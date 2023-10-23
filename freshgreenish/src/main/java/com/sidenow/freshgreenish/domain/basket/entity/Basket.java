package com.sidenow.freshgreenish.domain.basket.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Basket{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BASKET_ID")
    private Long basketId;

    private Long userId;

    private Integer totalBasketPrice;
    private Integer discountedBasketTotalPrice;
    private Integer discountedBasketPrice;

    private Integer totalRegularPrice;
    private Integer discountedRegularTotalPrice;
    private Integer discountedRegularPrice;

    @JsonManagedReference
    @OneToMany(mappedBy = "basket", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ProductBasket> productBasket = new ArrayList<>();

    @Builder
    public Basket(Long basketId, Long userId, Integer totalBasketPrice, Integer discountedBasketTotalPrice,
                  Integer discountedBasketPrice, Integer totalRegularPrice, Integer discountedRegularTotalPrice,
                  Integer discountedRegularPrice) {
        this.basketId = basketId;
        this.userId = userId;
        this.totalBasketPrice = totalBasketPrice;
        this.discountedBasketTotalPrice = discountedBasketTotalPrice;
        this.discountedBasketPrice = discountedBasketPrice;
        this.totalRegularPrice = totalRegularPrice;
        this.discountedRegularTotalPrice = discountedRegularTotalPrice;
        this.discountedRegularPrice = discountedRegularPrice;
    }

    public void setBasketPrice(Integer totalBasketPrice, Integer discountedBasketTotalPrice, Integer discountedBasketPrice) {
        this.totalBasketPrice = totalBasketPrice;
        this.discountedBasketPrice = discountedBasketPrice;
        this.discountedBasketTotalPrice = discountedBasketTotalPrice;
    }

    public void setRegularPrice(Integer totalRegularPrice, Integer discountedRegularTotalPrice,
                                Integer discountedRegularPrice) {
        this.totalRegularPrice = totalRegularPrice;
        this.discountedRegularTotalPrice = discountedRegularTotalPrice;
        this.discountedRegularPrice = discountedRegularPrice;
    }

    public void addProductBasket(ProductBasket productBaskets) {
        if(productBaskets.getBasket() != this) productBaskets.addBasket(this);
        productBasket.add(productBaskets);
    }
}
