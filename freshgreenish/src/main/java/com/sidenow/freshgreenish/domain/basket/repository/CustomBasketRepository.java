package com.sidenow.freshgreenish.domain.basket.repository;

import com.sidenow.freshgreenish.domain.basket.dto.GetBasket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomBasketRepository {
    Page<GetBasket> getBasketList(Long userId, Pageable pageable);

    Integer getTotalBasketPrice(Long basketId);
    Integer getDiscountedTotalBasketPrice(Long basketId);
}
