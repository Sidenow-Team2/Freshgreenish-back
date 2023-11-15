package com.sidenow.freshgreenish.domain.review.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.sidenow.freshgreenish.domain.review.dto.QGetReviewOnProductDetail is a Querydsl Projection type for GetReviewOnProductDetail
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QGetReviewOnProductDetail extends ConstructorExpression<GetReviewOnProductDetail> {

    private static final long serialVersionUID = -1160929457L;

    public QGetReviewOnProductDetail(com.querydsl.core.types.Expression<? extends com.sidenow.freshgreenish.domain.review.entity.Review> review, com.querydsl.core.types.Expression<Long> productId, com.querydsl.core.types.Expression<Long> userId, com.querydsl.core.types.Expression<String> nickname, com.querydsl.core.types.Expression<Boolean> isReviewLikes) {
        super(GetReviewOnProductDetail.class, new Class<?>[]{com.sidenow.freshgreenish.domain.review.entity.Review.class, long.class, long.class, String.class, boolean.class}, review, productId, userId, nickname, isReviewLikes);
    }

}

