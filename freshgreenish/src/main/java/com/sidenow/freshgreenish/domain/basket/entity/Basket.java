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

    @JsonManagedReference
    @OneToMany(mappedBy = "basket", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ProductBasket> productBasket = new ArrayList<>();

    @Builder
    public Basket(Long basketId, Long userId, Integer totalBasketPrice, Integer totalRegularPrice) {
        this.basketId = basketId;
        this.userId = userId;
    }
    public void addProductBasket(ProductBasket productBaskets) {
        if(productBaskets.getBasket() != this) productBaskets.addBasket(this);
        productBasket.add(productBaskets);
    }
}
