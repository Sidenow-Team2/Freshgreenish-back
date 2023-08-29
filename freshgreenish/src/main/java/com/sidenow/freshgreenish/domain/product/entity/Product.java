package com.sidenow.freshgreenish.domain.product.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE product SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID")
    private Long productId;
    private String name;
    private String type;
    private Integer price;

    @Column(length = 2000)
    private String detail;

    private Boolean season;
    private Integer likeCount;
    private Boolean isRecommendation; //추천상품
    private Boolean isSubscription; //구독가능여부
    private Long number; //상품번호
    private String company; //제조사
    private Integer weight; //상품무게
    private String area; //생산지
    private String productStatus; //상품상태
    private String origin; //원산지
    private String variety; //품종
    private String packages; //포장타입
    private String harvestSeason; //수확시기
    private Integer capacity; //중량/용량
    private Integer notification; //안내사항

    @Builder
    public Product(Long productId, String name, String type, Integer price, String detail,
                   Boolean season, Integer likeCount, Boolean isRecommendation, Boolean isSubscription, Long number,
                   String company, Integer weight, String area, String productStatus, String origin,
                   String variety, String packages, String harvestSeason, Integer capacity, Integer notification) {
        this.productId = productId;
        this.name = name;
        this.type = type;
        this.price = price;
        this.detail = detail;
        this.season = season;
        this.likeCount = likeCount;
        this.isRecommendation = isRecommendation;
        this.isSubscription = isSubscription;
        this.number = number;
        this.company = company;
        this.weight = weight;
        this.area = area;
        this.productStatus = productStatus;
        this.origin = origin;
        this.variety = variety;
        this.packages = packages;
        this.harvestSeason = harvestSeason;
        this.capacity = capacity;
        this.notification = notification;
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
}
