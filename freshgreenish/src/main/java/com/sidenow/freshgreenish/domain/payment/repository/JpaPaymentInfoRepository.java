package com.sidenow.freshgreenish.domain.payment.repository;

import com.sidenow.freshgreenish.domain.payment.entity.PaymentInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaPaymentInfoRepository extends JpaRepository<PaymentInfo, Long> {
    Optional<PaymentInfo> findByOrderId(String orderId);
//    Optional<PaymentInfo> findByPaymentKeyAndPurchase_User_Email(String paymentKey, String email);
//    Slice<PaymentInfo> findAllByPurchase_User_Email(String email, Pageable pageable);
}
