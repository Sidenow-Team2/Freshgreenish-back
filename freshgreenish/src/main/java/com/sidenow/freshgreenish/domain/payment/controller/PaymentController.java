package com.sidenow.freshgreenish.domain.payment.controller;

import com.sidenow.freshgreenish.domain.payment.dto.Message;
import com.sidenow.freshgreenish.domain.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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

    // Toss 결제 성공
    @GetMapping("/api/purchase/{purchaseId}/toss/success")
    public ResponseEntity<Message<?>> successTossPayment(@PathVariable("purchaseId") Long purchaseId) {
        Message<?> message = paymentService.getSuccessTossPaymentInfo(purchaseId);
        if (message.getData() == null) paymentService.getFailedPayMessage();
        return ResponseEntity.ok().build();
    }

    // Toss 결제 실패
    @GetMapping("/api/purchase/{purchaseId}/toss/failure")
    public ResponseEntity<String> failedTossPayment(@PathVariable("purchaseId") Long purchaseId) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(FAILED_PAY_MESSAGE);
    }


}
