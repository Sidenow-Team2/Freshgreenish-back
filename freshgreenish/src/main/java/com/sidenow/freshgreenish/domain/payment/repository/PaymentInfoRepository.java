package com.sidenow.freshgreenish.domain.payment.repository;

import com.sidenow.freshgreenish.domain.payment.entity.PaymentInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentInfoRepository extends JpaRepository<PaymentInfo, Long> {
    Optional<PaymentInfo> findByPurchasePurchaseId(Long purchaseId);
}
