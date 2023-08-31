package com.sidenow.freshgreenish.domain.payment.controller;

import com.sidenow.freshgreenish.domain.payment.service.PaymentInfoService;
import com.sidenow.freshgreenish.global.config.TossPaymentConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentInfoController {
    private final TossPaymentConfig tossPaymentConfig;
    private final PaymentInfoService paymentInfoService;

}
