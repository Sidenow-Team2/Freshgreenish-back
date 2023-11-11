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
                .totalBasketPrice(0)
                .totalRegularPrice(0)
                .build();
    }

    public Basket ifExistsReturnBasketByUserId(Long userId) {
        return basketRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.PRODUCT_BASKET_NOT_FOUND));
    }

    public Basket ifExistsReturnBasketById(Long basketId) {
        return basketRepository.findById(basketId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.PRODUCT_BASKET_NOT_FOUND));
    }

    public ProductBasket ifExistsReturnProductBasket(Long productId, Long basketId) {
        return productBasketRepository.findByProductProductIdAndBasketBasketId(productId, basketId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.PRODUCT_BASKET_NOT_FOUND));
    }

    public ProductBasket ifExistsReturnProductBasketByIsRegular(Long productId, Long basketId, Boolean isRegular) {
        return productBasketRepository.findByProductProductIdAndBasketBasketIdAndIsRegular(productId, basketId, isRegular)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.PRODUCT_BASKET_NOT_FOUND));
    }

    public Optional<ProductBasket> ifExistFindByProductIdAndBasketId(Long productId, Long basketId) {
        return productBasketRepository.findByProductProductIdAndBasketBasketId(productId, basketId);
    }

    public Optional<ProductBasket> ifExistFindByProductIdAndBasketIdAndIdRegular(Long productId, Long basketId, Boolean isRegular) {
        return productBasketRepository.findByProductProductIdAndBasketBasketIdAndIsRegular(productId, basketId, isRegular);
    }

    public Page<GetBasket> getBasketList(Long userId, Pageable pageable) {
        return basketRepository.getBasketList(userId, pageable);
    }

    public Page<GetBasket> getRegularList(Long userId, Pageable pageable) {
        return basketRepository.getRegularList(userId, pageable);
    }

    public List<Long> getProductIdInBasket(Long basketId) {
        return basketRepository.getProductIdInBasket(basketId);
    }

    public List<Long> getProductIdInRegular(Long basketId) {
        return basketRepository.getProductIdInRegular(basketId);
    }

    public Integer getDiscountedTotalBasketPrice(Long basketId) {
        return basketRepository.getDiscountedTotalBasketPrice(basketId);
    }

    public Integer getDiscountedTotalRegularPrice(Long basketId) {
        return basketRepository.getDiscountedTotalRegularPrice(basketId);
    }

    public Integer getProductPriceInBasket(Long productId, Long basketId) {
        return basketRepository.getProductPriceInBasket(productId, basketId);
    }

    public Integer getProductPriceInRegular(Long productId, Long basketId) {
        return basketRepository.getProductPriceInRegular(productId, basketId);
    }

    public void deleteAllByProductBasketId(List<Long> productBasketId) {
        productBasketRepository.deleteAllById(productBasketId);
    }
}
