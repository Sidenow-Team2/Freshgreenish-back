package com.sidenow.freshgreenish.domain.basket.repository;

import com.sidenow.freshgreenish.domain.basket.entity.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BasketRepository extends JpaRepository<Basket, Long>, CustomBasketRepository {
    Optional<Basket> findByUserId(Long userId);
}
