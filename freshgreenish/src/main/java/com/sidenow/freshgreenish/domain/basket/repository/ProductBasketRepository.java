package com.sidenow.freshgreenish.domain.basket.repository;

import com.sidenow.freshgreenish.domain.basket.entity.ProductBasket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductBasketRepository extends JpaRepository<ProductBasket, Long> {
    Optional<ProductBasket> findByProductProductIdAndBasketBasketId(Long productId, Long basketId);
}
