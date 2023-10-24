package com.sidenow.freshgreenish.domain.basket.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sidenow.freshgreenish.domain.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductBasket{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_BASKET_ID")
    private Long productBasketId;
    private Integer count;
    private Integer totalPrice = 0;
    private Integer discountedTotalPrice = 0;
    private Boolean isRegular = false;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASKET_ID")
    private Basket basket;

    @Builder
    public ProductBasket(Long productBasketId, Integer count, Integer totalPrice,
                         Integer discountedTotalPrice, Boolean isRegular) {
        this.productBasketId = productBasketId;
        this.count = count;
        this.totalPrice = totalPrice;
        this.discountedTotalPrice = discountedTotalPrice;
        this.isRegular = isRegular;
    }

    public void addProduct(Product product) {
        this.product = product;
    }

    public void addBasket(Basket basket) {
        this.basket = basket;
    }

    public void addCount() {
        count++;
    }

    public void reduceCount() {
        count--;
    }
}
