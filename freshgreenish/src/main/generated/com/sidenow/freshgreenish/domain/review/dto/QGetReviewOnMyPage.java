package com.sidenow.freshgreenish.domain.review.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.sidenow.freshgreenish.domain.review.dto.QGetReviewOnMyPage is a Querydsl Projection type for GetReviewOnMyPage
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QGetReviewOnMyPage extends ConstructorExpression<GetReviewOnMyPage> {

    private static final long serialVersionUID = 352229388L;

    public QGetReviewOnMyPage(com.querydsl.core.types.Expression<? extends com.sidenow.freshgreenish.domain.review.entity.Review> review, com.querydsl.core.types.Expression<Long> productId, com.querydsl.core.types.Expression<String> title, com.querydsl.core.types.Expression<String> productFirstImage) {
        super(GetReviewOnMyPage.class, new Class<?>[]{com.sidenow.freshgreenish.domain.review.entity.Review.class, long.class, String.class, String.class}, review, productId, title, productFirstImage);
    }

}

