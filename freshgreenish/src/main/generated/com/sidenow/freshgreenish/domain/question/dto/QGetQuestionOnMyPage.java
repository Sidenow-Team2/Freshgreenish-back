package com.sidenow.freshgreenish.domain.question.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.sidenow.freshgreenish.domain.question.dto.QGetQuestionOnMyPage is a Querydsl Projection type for GetQuestionOnMyPage
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QGetQuestionOnMyPage extends ConstructorExpression<GetQuestionOnMyPage> {

    private static final long serialVersionUID = 207119464L;

    public QGetQuestionOnMyPage(com.querydsl.core.types.Expression<? extends com.sidenow.freshgreenish.domain.question.entity.Question> question, com.querydsl.core.types.Expression<String> title, com.querydsl.core.types.Expression<String> productFirstImage, com.querydsl.core.types.Expression<String> answerComment) {
        super(GetQuestionOnMyPage.class, new Class<?>[]{com.sidenow.freshgreenish.domain.question.entity.Question.class, String.class, String.class, String.class}, question, title, productFirstImage, answerComment);
    }

}

