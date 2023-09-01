package com.sidenow.freshgreenish.domain.product.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sidenow.freshgreenish.global.utils.Auditable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE product SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Product extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID")
    private Long productId;
    private String title; //이름

    @Column(length = 2000)
    private String subTitle; //한줄소개

    private Integer price; //가격
    private Integer discountRate = 0; //할인율
    private Integer discountedPrice; //할인된가격

    @Column(length = 2000)
    private String detail; //상품 설명

    private String deliveryType; //배송방법
    private String seller; //판매자
    private String packageType; //포장타입
    private String unit; //판매단위
    private String capacity; //중량/용량
    private String origin; //원산지

    @Column(length = 2000)
    private String notification; //안내사항

    private Long productNumber; //상품번호
    private String storageMethod; //상품상태(냉장,실온,냉동)
    private String brand; //제조사
    private String weight; //상품무게
    private String variety; //품종
    private String harvestSeason; //수확시기

    private Boolean recommendation; //추천상품
    private Boolean subscription; //구독가능여부

    @Column(length = 2000)
    private String productFirstImage; //대표이미지주소
    @Column(length = 2000)
    private String productDetailImage; //상세정보이미지주소

    @Setter
    private Integer likeCount = 0; //좋아요수

    @Setter
    private Integer purchaseCount = 0; //판매수

    @Builder
    public Product(Long productId, String title, String subTitle, Integer price, Integer discountedPrice, String detail,
                   String deliveryType, String seller, String packageType, String unit, String capacity, String origin,
                   String notification, Long productNumber, String storageMethod, String brand, String weight, Integer discountRate,
                   String variety, String harvestSeason, Boolean recommendation, Boolean subscription, String productDetailImage) {
        this.productId = productId;
        this.title = title;
        this.subTitle = subTitle;
        this.price = price;
        this.discountRate = discountRate;
        this.discountedPrice = discountedPrice;
        this.detail = detail;

        this.deliveryType = deliveryType;
        this.seller = seller;
        this.packageType = packageType;
        this.unit = unit;
        this.capacity = capacity;
        this.origin = origin;
        this.notification = notification;

        this.productNumber = productNumber;
        this.storageMethod = storageMethod;
        this.brand = brand;
        this.weight = weight;
        this.variety = variety;
        this.harvestSeason = harvestSeason;

        this.recommendation = recommendation;
        this.subscription = subscription;

        this.productDetailImage = productDetailImage;
    }

    @JsonManagedReference
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> productImages = new ArrayList<>();

    public void addProductImage(ProductImage productImage) {
        if(productImage.getProduct() != this) productImage.addProduct(this);
        productImages.add(productImage);
    }

    public void editProductImage(List<ProductImage> productImages) {
        this.productImages.clear();
        this.productImages.addAll(productImages);
    }

    public void setProductFirstImage(List<ProductImage> productImages) {
        productFirstImage = productImages.get(0).getFilePath();
    }
}
