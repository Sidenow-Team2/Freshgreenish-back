package com.sidenow.freshgreenish.domain.user.repository;

import com.sidenow.freshgreenish.domain.delivery.entity.Delivery;
import com.sidenow.freshgreenish.domain.purchase.entity.Purchase;
import com.sidenow.freshgreenish.domain.purchase.entity.SubscriptionStatus;
import com.sidenow.freshgreenish.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByNickname(String nickname);
//    List<Purchase> findAllByUserIdAndSubscriptionStatus(Long userId, SubscriptionStatus subscriptionStatus);
//    Delivery findByPurchaseId(Long purchaseId);
}
