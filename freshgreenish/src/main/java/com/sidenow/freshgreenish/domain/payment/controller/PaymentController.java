package com.sidenow.freshgreenish.domain.payment.controller;

import com.sidenow.freshgreenish.domain.payment.dto.Message;
import com.sidenow.freshgreenish.domain.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import static com.sidenow.freshgreenish.domain.purchase.util.PurchaseConstants.CANCELED_PAY_MESSAGE;
import static com.sidenow.freshgreenish.domain.purchase.util.PurchaseConstants.FAILED_PAY_MESSAGE;

@RestController
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    // Toss 결제 요청
    @GetMapping("/purchase/{purchaseId}/toss/payment/{methodId}")
    public ResponseEntity<Message<?>> orderTossPayment(@PathVariable("purchaseId") Long purchaseId,
                                                       @PathVariable("methodId") int methodId) {
        Message<?> message = paymentService.getTossPayUrl(purchaseId, methodId);
        if (message.getData() == null) paymentService.getFailedPayMessage();
        return ResponseEntity.ok().body(message);
    }

    // kakao 결제 요청
    @GetMapping("/purchase/{purchaseId}/kakao/payment")
    public ResponseEntity<Message<?>> orderKakaoPayment(@PathVariable("purchaseId") Long purchaseId) {
        Message<?> message = paymentService.getKaKaoPayUrl(purchaseId);
        if (message.getData() == null) paymentService.getFailedPayMessage();
        return ResponseEntity.ok().body(message);
    }

    // kakao 정기 결제 요청
    @GetMapping("/purchase/{purchaseId}/subscription/kakao/payment")
    public ResponseEntity<Message<?>> orderKakaoRegularPayment(@PathVariable("purchaseId") Long purchaseId) {
        Message<?> message = paymentService.getKakaoSubUrl(purchaseId);
        if (message.getData() == null) paymentService.getFailedPayMessage();
        return ResponseEntity.ok().body(message);
    }

    // Toss 결제 성공
    @GetMapping("/api/purchase/{purchaseId}/toss/success")
    public ResponseEntity<Message<?>> successTossPayment(@PathVariable("purchaseId") Long purchaseId) {
        Message<?> message = paymentService.getSuccessTossPaymentInfo(purchaseId);
        if (message.getData() == null) paymentService.getFailedPayMessage();
        return ResponseEntity.ok().build();
    }

    // kakao 결제 성공
    @GetMapping("/api/purchase/{purchaseId}/kakao/success")
    public ResponseEntity<Message<?>> successKakaoPayment(@PathVariable("purchaseId") Long purchaseId,
                                                          @RequestParam("pg_token") String pgToken) {
        Message<?> message = paymentService.getSuccessKakaoPaymentInfo(purchaseId, pgToken);
        if (message.getData() == null) paymentService.getFailedPayMessage();
        return ResponseEntity.ok().build();
    }

    // kakao 정기 결제 성공
    @GetMapping("/api/purchase/{purchaseId}/subscription/kakao/success")
    public ResponseEntity<Message<?>> successKakaoRegularPayment(@PathVariable("purchaseId") Long purchaseId,
                                                                 @RequestParam("pg_token") String pgToken) {
        Message<?> message = paymentService.getSuccessKakaoRegularPaymentInfo(purchaseId, pgToken);
        if (message.getData() == null) paymentService.getFailedPayMessage();
        return ResponseEntity.ok().build();
    }

    // Toss 결제 실패
    @GetMapping("/api/purchase/{purchaseId}/toss/failure")
    public ResponseEntity<String> failedTossPayment(@PathVariable("purchaseId") Long purchaseId) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(FAILED_PAY_MESSAGE);
    }

    // kakao 결제 취소
    @GetMapping("/api/purchase/{purchaseId}/kakao/cancellation")
    public ResponseEntity<String> cancelKakaoPayment(@PathVariable("purchaseId") Long purchaseId) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(CANCELED_PAY_MESSAGE);
    }

    // kakao 정기 결제 취소(비활성화)
    @GetMapping("/purchase/{purchaseId}/subscription/kakao/cancellation")
    public ResponseEntity<String> cancelKakaoRegularPayment(@PathVariable("purchaseId") Long purchaseId) {
        Message<?> message = paymentService.cancleKakaoRegularPayment(purchaseId);
        if (message.getData() == null) paymentService.getFailedPayMessage();
        return ResponseEntity.ok().build();
    }

    // kakao 결제 실패
    @GetMapping("/api/purchase/{purchaseId}/kakao/failure")
    public ResponseEntity<String> failedKakaoPayment(@PathVariable("purchaseId") Long purchaseId) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(FAILED_PAY_MESSAGE);
    }


}
