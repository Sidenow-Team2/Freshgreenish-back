package com.sidenow.freshgreenish.domain.payment.service;

import com.sidenow.freshgreenish.domain.payment.dto.TossPaymentSuccessDto;
import com.sidenow.freshgreenish.domain.payment.entity.PaymentInfo;
import com.sidenow.freshgreenish.domain.payment.repository.JpaPaymentInfoRepository;
import com.sidenow.freshgreenish.domain.user.entity.User;
import com.sidenow.freshgreenish.domain.user.repository.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentInfoService {

    private final JpaPaymentInfoRepository jpaPaymentInfoRepository;
    private final JpaUserRepository jpaUserRepository;

    public PaymentInfo requestTossPayment(PaymentInfo paymentInfo, Long userId) {

        User user = jpaUserRepository.findById(userId).orElseThrow( () -> new RuntimeException("존재하지 않는 유저 입니다.")); // TODO Authentication으로 변경되면 userId -> userEmail로 변경
        return jpaPaymentInfoRepository.save(paymentInfo);
    }


}
