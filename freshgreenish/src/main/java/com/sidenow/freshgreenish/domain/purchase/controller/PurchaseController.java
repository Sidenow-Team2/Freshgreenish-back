package com.sidenow.freshgreenish.domain.purchase.controller;

import com.sidenow.freshgreenish.domain.payment.dto.PaymentResponseDto;
import com.sidenow.freshgreenish.domain.purchase.dto.ProductOrderDTO;
import com.sidenow.freshgreenish.domain.purchase.service.PurchaseService;
import com.sidenow.freshgreenish.global.config.TossPaymentConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/purchase")
public class PurchaseController {
    private final TossPaymentConfig tossPaymentConfig;
    private final PurchaseService  purchaseService;

    @PostMapping("/{type}")
    public PaymentResponseDto savePurchase(@PathVariable("type") String type, @RequestBody ProductOrderDTO productOrderDTO){
        Long userId = 1L; // TODO Authentication 구현전에 임시로 사용할 예정
        PaymentResponseDto paymentResponseDto = purchaseService.checkPurchaseValue(productOrderDTO, userId);
        if(type.equals("toss")){
            paymentResponseDto.setSuccessUrl(tossPaymentConfig.getSuccessUrl());
            paymentResponseDto.setFailUrl(tossPaymentConfig.getFailUrl());
        }
        return paymentResponseDto;
    }
}
