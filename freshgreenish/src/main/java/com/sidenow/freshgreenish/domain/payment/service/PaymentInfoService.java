package com.sidenow.freshgreenish.domain.payment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sidenow.freshgreenish.domain.payment.dto.TossPaymentSuccessDto;
import com.sidenow.freshgreenish.domain.payment.entity.PaymentInfo;
import com.sidenow.freshgreenish.domain.purchase.entity.Purchase;
import com.sidenow.freshgreenish.domain.purchase.service.PurchaseDbService;
import com.sidenow.freshgreenish.global.config.TossPaymentConfig;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentInfoService {

    private final PurchaseDbService purchaseDbService;
    private final TossPaymentConfig tossPaymentConfig;
    private final ObjectMapper objectMapper;
    private final EntityManager entityManager;
    private final PaymentInfoDbService paymentInfoDbService;

    public String paymentSuccess(String type, String paymentKey, Long purchaseId, String orderId, Long totalAmount) {
        Purchase purchase = purchaseDbService.findById(purchaseId).orElseThrow(() -> new RuntimeException("존재 하지 않는 구매 번호 입니다."));
        String message = "결제 데이터 저장 실패";

        if (totalAmount.equals(purchase.getPaymentInfo().getTotalAmount())) {
            if (type.equals("toss")) {

                try{
                    message = validateTossPayment(paymentKey, purchase, orderId, totalAmount);
                }catch (Exception e){
                    return message;
                }

            } else if (type.equals("kakao")) {
                message = "구현 중입니다.";
            }
        }
        return message;
    }

    private String validateTossPayment(String paymentKey, Purchase purchase, String orderId, Long totalAmount) throws IOException, InterruptedException {
        String testSecretApiKey = tossPaymentConfig.getTestSecretKey() + ":";
        String authKey = new String(Base64.getEncoder().encode(testSecretApiKey.getBytes(StandardCharsets.UTF_8)));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.tosspayments.com/v1/payments/confirm"))
                .header("Authorization", "Basic " + authKey)
                .header("Content-Type", "application/json")
                .method("POST"
                        , HttpRequest
                                .BodyPublishers
                                .ofString("{\"paymentKey\":\"" + paymentKey + "\",\"amount\":\"" + totalAmount + "\",\"orderId\":\"" + orderId + "\"}")
                ).build();

        HttpResponse<String> response = HttpClient
                .newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        TossPaymentSuccessDto tossResponse = objectMapper.readValue(response.body(), TossPaymentSuccessDto.class);
        PaymentInfo paymentInfo = purchase.getPaymentInfo();

        paymentInfo.setCid(tossResponse.getMid());
        paymentInfo.setTid(tossResponse.getPaymentKey());
        paymentInfo.setPartnerOrderId(tossResponse.getOrderId());
        paymentInfo.setSuccessStatus(tossResponse.getStatus().equals("DONE"));

        entityManager.merge(paymentInfo);

        return "결제 성공";
    }


    public void paymentFail(String code, String message, String orderId) {
        PaymentInfo paymentInfo = paymentInfoDbService.findByPartnerOrderId(orderId).orElseThrow(() -> {
            throw  new RuntimeException("존재하지 않는 거래 입니다.");
        });
        paymentInfo.setSuccessStatus(false);
        paymentInfo.setFailReasons(message);
        entityManager.merge(paymentInfo);
    }
}
