package com.sidenow.freshgreenish.domain.basket.service;

import com.sidenow.freshgreenish.domain.basket.dto.GetBasket;
import com.sidenow.freshgreenish.domain.basket.entity.Basket;
import com.sidenow.freshgreenish.domain.basket.entity.ProductBasket;
import com.sidenow.freshgreenish.domain.basket.repository.BasketRepository;
import com.sidenow.freshgreenish.domain.basket.repository.ProductBasketRepository;
import com.sidenow.freshgreenish.global.exception.BusinessLogicException;
import com.sidenow.freshgreenish.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BasketDbService {
    private final BasketRepository basketRepository;
    private final ProductBasketRepository productBasketRepository;

    public void saveBasket(Basket basket) {
        basketRepository.save(basket);
    }

    public Basket saveAndReturnBasket(Basket basket) {
        return basketRepository.save(basket);
    }

    public Basket ifExistsReturnBasket(Long userId) {
        if (basketRepository.findByUserId(userId).isPresent()) {
            return basketRepository.findByUserId(userId).get();
        }

        return Basket.builder()
                .userId(userId)
                .build();
    }

    public Basket ifExistsReturnBasketByUserId(Long userId) {
        return basketRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.PRODUCT_BASKET_NOT_FOUND));
    }

    public ProductBasket ifExistsReturnProductBasket(Long productId, Long basketId) {
        return productBasketRepository.findByProductProductIdAndBasketBasketId(productId, basketId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.PRODUCT_BASKET_NOT_FOUND));
    }

    public Optional<ProductBasket> ifExistFindByProductIdAndBasketId(Long productId, Long basketId) {
        return productBasketRepository.findByProductProductIdAndBasketBasketId(productId, basketId);
    }

    public Page<GetBasket> getBasketList(Long userId, Pageable pageable) {
        return basketRepository.getBasketList(userId, pageable);
    }

    public Integer getTotalBasketPrice(Long basketId) {
        return basketRepository.getTotalBasketPrice(basketId);
    }

    public Integer getDiscountedTotalBasketPrice(Long basketId) {
        return basketRepository.getDiscountedTotalBasketPrice(basketId);
    }

    public void deleteAllByProductBasketId(List<Long> productBasketId) {
        productBasketRepository.deleteAllById(productBasketId);
    }
}
