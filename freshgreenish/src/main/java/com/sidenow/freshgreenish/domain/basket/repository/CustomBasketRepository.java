package com.sidenow.freshgreenish.domain.basket.repository;

import com.sidenow.freshgreenish.domain.basket.dto.GetBasket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomBasketRepository {
    Page<GetBasket> getBasketList(Long userId, Pageable pageable);
    Page<GetBasket> getRegularList(Long userId, Pageable pageable);

    List<Long> getProductIdInBasket(Long basketId);
    List<Long> getProductIdInRegular(Long basketId);

    Integer getTotalBasketPrice(Long basketId);
    Integer getTotalRegularPrice(Long basketId);
    Integer getDiscountedTotalBasketPrice(Long basketId);
    Integer getDiscountedTotalRegularPrice(Long basketId);

    Integer getProductPriceInBasket(Long productId, Long basketId);
    Integer getProductPriceInRegular(Long productId, Long basketId);
}
