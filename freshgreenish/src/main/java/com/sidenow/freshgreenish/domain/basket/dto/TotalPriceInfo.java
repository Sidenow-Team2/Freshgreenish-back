package com.sidenow.freshgreenish.domain.basket.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TotalPriceInfo {
    private Integer totalBasketPrice;
    private Integer discountedBasketTotalPrice;
    private Integer discountedBasketPrice;

    @Builder
    public TotalPriceInfo(Integer totalBasketPrice, Integer discountedBasketTotalPrice, Integer discountedBasketPrice) {
        this.totalBasketPrice = totalBasketPrice;
        this.discountedBasketTotalPrice = discountedBasketTotalPrice;
        this.discountedBasketPrice = discountedBasketPrice;
    }
}
