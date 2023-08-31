package com.sidenow.freshgreenish.domain.payment.service;

import com.sidenow.freshgreenish.domain.payment.dto.TossPaymentSuccessDto;
import com.sidenow.freshgreenish.domain.payment.entity.PaymentInfo;
import com.sidenow.freshgreenish.domain.purchase.service.PurchaseDbService;
import com.sidenow.freshgreenish.domain.user.entity.User;
import com.sidenow.freshgreenish.domain.user.service.UserDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentInfoService {

    private final PaymentInfoDbService paymentInfoDbService;
    private final PurchaseDbService purchaseDbService;
    private final UserDbService userDbService;

    public PaymentInfo requestTossPayment(PaymentInfo paymentInfo, Long userId) {

        User user = userDbService.findById(userId); // TODO Authentication으로 변경되면 userId -> userEmail로 변경
        // 구매 테이블 있는지 확인후 생성 또는 return

        return paymentInfoDbService.save(paymentInfo);
    }


}
