package com.sidenow.freshgreenish.domain.payment.service;

import com.sidenow.freshgreenish.domain.payment.entity.PaymentInfo;
import com.sidenow.freshgreenish.domain.payment.repository.PaymentInfoRepository;
import com.sidenow.freshgreenish.global.exception.BusinessLogicException;
import com.sidenow.freshgreenish.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentDbService {

    private final PaymentInfoRepository paymentInfoRepository;

    public PaymentInfo savePayment(PaymentInfo paymentInfo) {
        return paymentInfoRepository.save(paymentInfo);
    }

    public PaymentInfo ifExistsReturnPaymentInfo(Long purchaseId) {
        return paymentInfoRepository.findByPurchasePurchaseId(purchaseId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.PAYMENT_NOT_FOUND));
    }
}
