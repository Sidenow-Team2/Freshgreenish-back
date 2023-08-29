package com.sidenow.freshgreenish.domain.payment.controller;

import com.sidenow.freshgreenish.domain.payment.dto.TossPaymentDto;
import com.sidenow.freshgreenish.domain.payment.dto.TossPaymentResponseDto;
import com.sidenow.freshgreenish.domain.payment.dto.TossPaymentSuccessCardDto;
import com.sidenow.freshgreenish.domain.payment.service.PaymentInfoService;
import com.sidenow.freshgreenish.global.config.TossPaymentConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentInfoController {
    private final TossPaymentConfig tossPaymentConfig;
    private final PaymentInfoService paymentInfoService;

    @PostMapping("/toss")
    public TossPaymentResponseDto ResponseEntity(@RequestBody TossPaymentDto tossPaymentDto){
        Long userId = 1L; // TODO Authentication 구현전에 임시로 사용할 예정
        TossPaymentResponseDto paymentResDto = paymentInfoService.requestTossPayment(tossPaymentDto.toEntity(), userId).toTossPaymentResponseDto();
        paymentResDto.setSuccessUrl(tossPaymentDto.getYourSuccessUrl() == null ? tossPaymentConfig.getSuccessUrl() : tossPaymentDto.getYourSuccessUrl());
        paymentResDto.setFailUrl(tossPaymentDto.getYourFailUrl() == null ? tossPaymentConfig.getFailUrl() : tossPaymentDto.getYourFailUrl());
        return paymentResDto;
    }



}
