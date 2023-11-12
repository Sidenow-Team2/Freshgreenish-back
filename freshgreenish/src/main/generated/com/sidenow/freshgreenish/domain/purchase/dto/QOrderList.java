package com.sidenow.freshgreenish.domain.purchase.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.sidenow.freshgreenish.domain.purchase.dto.QOrderList is a Querydsl Projection type for OrderList
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QOrderList extends ConstructorExpression<OrderList> {

    private static final long serialVersionUID = -1947406535L;

    public QOrderList(com.querydsl.core.types.Expression<Long> productId, com.querydsl.core.types.Expression<String> title, com.querydsl.core.types.Expression<String> productFirstImage, com.querydsl.core.types.Expression<Integer> count, com.querydsl.core.types.Expression<Integer> discountedPrice) {
        super(OrderList.class, new Class<?>[]{long.class, String.class, String.class, int.class, int.class}, productId, title, productFirstImage, count, discountedPrice);
    }

}

