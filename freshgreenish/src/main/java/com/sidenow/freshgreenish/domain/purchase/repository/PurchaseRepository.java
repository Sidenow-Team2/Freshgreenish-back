package com.sidenow.freshgreenish.domain.purchase.repository;

import com.sidenow.freshgreenish.domain.purchase.entity.Purchase;
import com.sidenow.freshgreenish.domain.purchase.entity.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PurchaseRepository extends JpaRepository<Purchase, Long>, CustomPurchaseRepository {

    List<Purchase> findAllByUserIdAndSubscriptionStatus(Long userId, SubscriptionStatus subscriptionStatus);
}
