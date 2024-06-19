package com.sidenow.freshgreenish.domain.purchase.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderList {
    private Long productId;
    private String title;
    private String productFirstImage;
    private Integer count;
    private Integer discountedPrice;

    @Builder
    @QueryProjection
    public OrderList(Long productId, String title, String productFirstImage, Integer count, Integer discountedPrice) {
        this.productId = productId;
        this.title = title;
        this.productFirstImage = productFirstImage;
        this.count = count;
        this.discountedPrice = discountedPrice;
    }
}
