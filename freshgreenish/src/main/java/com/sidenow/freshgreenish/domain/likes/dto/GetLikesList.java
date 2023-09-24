package com.sidenow.freshgreenish.domain.likes.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetLikesList {
    private Long likesId;
    private Long productId;
    private String title; //이름
    private Integer price; //가격
    private Integer discountRate; //할인율
    private Integer discountedPrice; //할인된가격
    private String productFirstImage; //상품대표이미지

    @Builder
    @QueryProjection
    public GetLikesList(Long likesId, Long productId, String title, Integer price,
                        Integer discountRate, Integer discountedPrice, String productFirstImage) {
        this.likesId = likesId;
        this.productId = productId;
        this.title = title;
        this.price = price;
        this.discountRate = discountRate;
        this.discountedPrice = discountedPrice;
        this.productFirstImage = productFirstImage;
    }
}
