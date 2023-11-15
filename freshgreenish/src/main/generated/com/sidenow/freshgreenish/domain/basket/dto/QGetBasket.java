package com.sidenow.freshgreenish.domain.basket.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.sidenow.freshgreenish.domain.basket.dto.QGetBasket is a Querydsl Projection type for GetBasket
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QGetBasket extends ConstructorExpression<GetBasket> {

    private static final long serialVersionUID = -1172648658L;

    public QGetBasket(com.querydsl.core.types.Expression<Long> productBasketId, com.querydsl.core.types.Expression<Long> productId, com.querydsl.core.types.Expression<String> title, com.querydsl.core.types.Expression<String> productFirstImage, com.querydsl.core.types.Expression<Integer> count, com.querydsl.core.types.Expression<Integer> price, com.querydsl.core.types.Expression<Integer> discountRate, com.querydsl.core.types.Expression<Integer> discountedPrice) {
        super(GetBasket.class, new Class<?>[]{long.class, long.class, String.class, String.class, int.class, int.class, int.class, int.class}, productBasketId, productId, title, productFirstImage, count, price, discountRate, discountedPrice);
    }

}

