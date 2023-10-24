package com.sidenow.freshgreenish.domain.delivery.repository;

import com.sidenow.freshgreenish.domain.delivery.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    Optional<Delivery> findByPurchaseId(Long purchaseId);

}
