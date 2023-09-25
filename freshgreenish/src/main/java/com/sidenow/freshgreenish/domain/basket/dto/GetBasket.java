package com.sidenow.freshgreenish.domain.basket.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetBasket {
    private Long productBasketId;
    private Long productId;
    private String title;
    private String productFirstImage;
    private Integer count;
    private Integer price;
    private Integer discountRate;
    private Integer discountedPrice;

    @Builder
    @QueryProjection
    public GetBasket(Long productBasketId, Long productId, String title, String productFirstImage,
                     Integer count, Integer price, Integer discountRate, Integer discountedPrice) {
        this.productBasketId = productBasketId;
        this.productId = productId;
        this.title = title;
        this.productFirstImage = productFirstImage;
        this.count = count;
        this.price = price;
        this.discountRate = discountRate;
        this.discountedPrice = discountedPrice;
    }
}
