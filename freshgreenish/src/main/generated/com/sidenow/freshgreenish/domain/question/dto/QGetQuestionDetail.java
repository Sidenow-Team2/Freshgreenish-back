package com.sidenow.freshgreenish.domain.question.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.sidenow.freshgreenish.domain.question.dto.QGetQuestionDetail is a Querydsl Projection type for GetQuestionDetail
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QGetQuestionDetail extends ConstructorExpression<GetQuestionDetail> {

    private static final long serialVersionUID = 898233119L;

    public QGetQuestionDetail(com.querydsl.core.types.Expression<? extends com.sidenow.freshgreenish.domain.question.entity.Question> question, com.querydsl.core.types.Expression<String> title, com.querydsl.core.types.Expression<String> productFirstImage) {
        super(GetQuestionDetail.class, new Class<?>[]{com.sidenow.freshgreenish.domain.question.entity.Question.class, String.class, String.class}, question, title, productFirstImage);
    }

}

