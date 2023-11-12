package com.sidenow.freshgreenish.domain.product.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.sidenow.freshgreenish.domain.product.dto.QGetProductCategory is a Querydsl Projection type for GetProductCategory
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QGetProductCategory extends ConstructorExpression<GetProductCategory> {

    private static final long serialVersionUID = 1242353166L;

    public QGetProductCategory(com.querydsl.core.types.Expression<Long> productId, com.querydsl.core.types.Expression<String> title, com.querydsl.core.types.Expression<Integer> price, com.querydsl.core.types.Expression<Integer> discountRate, com.querydsl.core.types.Expression<Integer> discountedPrice, com.querydsl.core.types.Expression<String> productFirstImage) {
        super(GetProductCategory.class, new Class<?>[]{long.class, String.class, int.class, int.class, int.class, String.class}, productId, title, price, discountRate, discountedPrice, productFirstImage);
    }

}

