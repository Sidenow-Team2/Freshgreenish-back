package com.sidenow.freshgreenish.domain.basket.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sidenow.freshgreenish.domain.product.entity.Product;
import com.sidenow.freshgreenish.global.utils.Auditable;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductBasket extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_BASKET_ID")
    private Long productBasketId;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASKET_ID")
    private Basket basket;

    @Builder
    public ProductBasket(Product product, Basket basket) {
        this.product = product;
        this.basket = basket;
    }
}
