package com.sidenow.freshgreenish.domain.product.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.sidenow.freshgreenish.domain.product.dto.QGetProductDetail is a Querydsl Projection type for GetProductDetail
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QGetProductDetail extends ConstructorExpression<GetProductDetail> {

    private static final long serialVersionUID = 1776627041L;

    public QGetProductDetail(com.querydsl.core.types.Expression<? extends com.sidenow.freshgreenish.domain.product.entity.Product> product, com.querydsl.core.types.Expression<Boolean> isLikes) {
        super(GetProductDetail.class, new Class<?>[]{com.sidenow.freshgreenish.domain.product.entity.Product.class, boolean.class}, product, isLikes);
    }

}

