package com.sidenow.freshgreenish.domain.likes.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.sidenow.freshgreenish.domain.likes.dto.QGetLikesList is a Querydsl Projection type for GetLikesList
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QGetLikesList extends ConstructorExpression<GetLikesList> {

    private static final long serialVersionUID = 1183426894L;

    public QGetLikesList(com.querydsl.core.types.Expression<Long> likesId, com.querydsl.core.types.Expression<Long> productId, com.querydsl.core.types.Expression<String> title, com.querydsl.core.types.Expression<Integer> price, com.querydsl.core.types.Expression<Integer> discountRate, com.querydsl.core.types.Expression<Integer> discountedPrice, com.querydsl.core.types.Expression<String> productFirstImage) {
        super(GetLikesList.class, new Class<?>[]{long.class, long.class, String.class, int.class, int.class, int.class, String.class}, likesId, productId, title, price, discountRate, discountedPrice, productFirstImage);
    }

}

