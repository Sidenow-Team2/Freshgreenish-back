package com.sidenow.freshgreenish.domain.payment.service;

import com.sidenow.freshgreenish.domain.payment.entity.PaymentInfo;
import com.sidenow.freshgreenish.domain.payment.repository.PaymentInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentInfoDbService {

    private final PaymentInfoRepository paymentInfoRepository;

    public PaymentInfo save(PaymentInfo paymentInfo) {
        return paymentInfoRepository.save(paymentInfo);
    }
}
