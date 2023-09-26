package com.sidenow.freshgreenish.domain.payment.service;

import com.sidenow.freshgreenish.domain.payment.feign.TossPayFeignClient;
import com.sidenow.freshgreenish.domain.payment.toss.*;
import com.sidenow.freshgreenish.domain.payment.util.PayConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;

import java.util.Base64;

@Slf4j
@Service
@Transactional
public class FeignService {
    @Value("${kakao.admin.key}")
    private String adminKey;

    @Value("${kakao.uri.pay-process}")
    private String paymentProcessUri;

    @Value("${kakao.pay.cid}")
    private String cid;

    @Value("${kakao.pay.taxfree}")
    private Integer taxFreeAmount;

    @Value("${toss.secret-key}")
    private String SECRET_KEY;

    @Autowired
    TossPayFeignClient tossPayFeignClient;

    public TossPayHeader setTossHeaders() {
        return TossPayHeader.builder()
                .adminKey(PayConstants.TOSS_AK + Base64.getEncoder().encodeToString((SECRET_KEY + ":").getBytes()))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public ReadyToTossPayInfo setReadyTossParams(String requestUrl, Long purchasId, Integer totalCost,
                                                 String PurchaseNumber, String orderName, String method) {
        return ReadyToTossPayInfo.builder()
                .successUrl(requestUrl + paymentProcessUri + "/" + purchasId + "/toss/success")
                .failUrl(requestUrl + paymentProcessUri + "/" + purchasId + "/toss/failure")
                .amount(totalCost)
                .method(method)
                .orderId(PurchaseNumber)
                .orderName(orderName)
                .build();
    }

    public TossPayReadyInfo getTossPayReadyInfo(TossPayHeader headers, ReadyToTossPayInfo body) {
        try {
            return tossPayFeignClient.readyForTossPayment(
                    headers.getAdminKey(),
                    headers.getContentType(),
                    body
            );
        } catch (RestClientException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public TossPaySuccessInfo getSuccessTossResponse(TossPayHeader headers, RequestForTossPayInfo body) {
        try {
            return tossPayFeignClient
                    .successForPayment(
                            headers.getAdminKey(),
                            headers.getContentType(),
                            body
                    );
        } catch (RestClientException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public RequestForTossPayInfo setRequestBody(String paymentKey, Integer amount, String orderId) {
        return RequestForTossPayInfo.builder()
                .paymentKey(paymentKey)
                .amount(amount)
                .orderId(orderId)
                .build();
    }
}

