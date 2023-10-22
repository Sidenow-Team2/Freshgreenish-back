package com.sidenow.freshgreenish.domain.payment.service;

import com.sidenow.freshgreenish.domain.payment.entity.PaymentInfo;
import com.sidenow.freshgreenish.domain.payment.feign.KakaoPayFeignClient;
import com.sidenow.freshgreenish.domain.payment.feign.TossPayFeignClient;
import com.sidenow.freshgreenish.domain.payment.kakao.*;
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

    @Autowired
    KakaoPayFeignClient kakaoFeignClient;

    public TossPayHeader setTossHeaders() {
        return TossPayHeader.builder()
                .adminKey(PayConstants.TOSS_AK + Base64.getEncoder().encodeToString((SECRET_KEY + ":").getBytes()))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public KakaoPayHeader setKakaoHeaders() {
        return KakaoPayHeader.builder()
                .adminKey(PayConstants.KAKAO_AK + adminKey)
                .accept(MediaType.APPLICATION_JSON + PayConstants.UTF_8)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE + PayConstants.UTF_8)
                .build();
    }

    public ReadyToTossPayInfo setReadyTossParams(String requestUrl, Long purchaseId, Integer totalCost,
                                                 String PurchaseNumber, String orderName, String method) {
        return ReadyToTossPayInfo.builder()
                .successUrl(requestUrl + paymentProcessUri + "/" + purchaseId + "/toss/success")
                .failUrl(requestUrl + paymentProcessUri + "/" + purchaseId + "/toss/failure")
                .amount(totalCost)
                .method(method)
                .orderId(PurchaseNumber)
                .orderName(orderName)
                .build();
    }

    public ReadyToKakaoPayInfo setReadyParams(String requestUrl, Long purchaseId, Integer totalCost,
                                              Long userId, String orderName, Integer totalCount) {
        return ReadyToKakaoPayInfo.builder()
                .cid(cid)
                .approval_url(requestUrl + paymentProcessUri + "/" + purchaseId + "/kakao/success")
                .cancel_url(requestUrl + paymentProcessUri + "/" + purchaseId + "/kakao/cancellation")
                .fail_url(requestUrl + paymentProcessUri + "/" + purchaseId + "/kakao/failure")
                .partner_order_id(purchaseId + "/" + userId + "/" + orderName)
                .partner_user_id(userId.toString())
                .item_name(orderName)
                .quantity(totalCount)
                .total_amount(totalCost)
                .val_amount(totalCost)
                .tax_free_amount(taxFreeAmount)
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

    public KakaoPayReadyInfo getPayReadyInfo(KakaoPayHeader headers,
                                             ReadyToKakaoPayInfo params) {
        try {
            return kakaoFeignClient.readyForPayment(
                    headers.getAdminKey(),
                    headers.getAccept(),
                    headers.getContentType(),
                    params
            );
        } catch (RestClientException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public RequestForKakaoPayInfo setRequestParams(String pgToken,
                                                   PaymentInfo paymentInfo) {
        return RequestForKakaoPayInfo.builder()
                .cid(paymentInfo.getCid())
                .tid(paymentInfo.getTid())
                .partner_order_id(paymentInfo.getPartnerOrderId())
                .partner_user_id(paymentInfo.getPartnerUserId())
                .pg_token(pgToken)
                .total_amount(paymentInfo.getTotalAmount())
                .build();
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

    public KakaoPaySuccessInfo getSuccessKakaoResponse(KakaoPayHeader headers,
                                                       RequestForKakaoPayInfo params) {
        try {
            return kakaoFeignClient
                    .successForPayment(
                            headers.getAdminKey(),
                            headers.getAccept(),
                            headers.getContentType(),
                            params
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

