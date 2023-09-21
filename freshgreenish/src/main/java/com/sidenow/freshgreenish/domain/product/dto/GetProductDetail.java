package com.sidenow.freshgreenish.domain.product.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.sidenow.freshgreenish.domain.product.entity.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class GetProductDetail {
    private Long productId;
    private String title; //이름
    private String subTitle; //한줄소개
    private Integer price; //가격
    private Integer discountRate; //할인율
    private Integer discountedPrice; //할인된가격
    private String detail; //상품 설명

    private String deliveryType; //배송방법
    private String seller; //판매자
    private String packageType; //포장타입
    private String unit; //판매단위
    private String capacity; //중량/용량
    private String origin; //원산지
    private String notification; //안내사항

    private String productNumber; //상품번호
    private String storageMethod; //상품상태(냉장,실온,냉동)
    private String brand; //제조사
    private String weight; //상품무게
    private String variety; //품종
    private String harvestSeason; //수확시기

    private Boolean recommendation; //추천상품
    private Boolean subscription; //구독가능여부

    private Boolean isLikes; //좋아요여부
    private Integer likeCount; //좋아요수

    private List<ProductImageVO> productImages; //상품이미지(최대3장)
    private String productDetailImage; //상품상세이미지

    @Builder
    @QueryProjection
    public GetProductDetail(Product product, Boolean isLikes) {
        this.productId = product.getProductId();
        this.title = product.getTitle();
        this.subTitle = product.getSubTitle();
        this.price = product.getPrice();
        this.discountRate = product.getDiscountRate();
        this.discountedPrice = product.getDiscountPrice();
        this.detail = product.getDetail();
        this.deliveryType = product.getDeliveryType();
        this.seller = product.getSeller();
        this.packageType = product.getPackageType();
        this.unit = product.getUnit();
        this.capacity = product.getCapacity();
        this.notification = product.getNotification();
        this.origin = product.getOrigin();
        this.productNumber = product.getProductNumber();
        this.storageMethod = product.getStorageMethod();
        this.brand = product.getBrand();
        this.weight = product.getWeight();
        this.variety = product.getVariety();
        this.harvestSeason = product.getHarvestSeason();
        this.recommendation = product.getRecommendation();
        this.subscription = product.getSubscription();
        this.isLikes = isLikes;
        this.likeCount = product.getLikeCount();
        this.productImages = product.getProductImages().stream()
                .map(productImage -> ProductImageVO.builder()
                        .productImageId(productImage.getProductImageId())
                        .filePath(productImage.getFilePath())
                        .build())
                .collect(Collectors.toList());
        this.productDetailImage = product.getProductDetailImage();
    }
}
