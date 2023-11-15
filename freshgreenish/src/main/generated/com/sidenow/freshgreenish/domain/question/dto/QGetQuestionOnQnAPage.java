package com.sidenow.freshgreenish.domain.question.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.sidenow.freshgreenish.domain.question.dto.QGetQuestionOnQnAPage is a Querydsl Projection type for GetQuestionOnQnAPage
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QGetQuestionOnQnAPage extends ConstructorExpression<GetQuestionOnQnAPage> {

    private static final long serialVersionUID = 1051497990L;

    public QGetQuestionOnQnAPage(com.querydsl.core.types.Expression<? extends com.sidenow.freshgreenish.domain.question.entity.Question> question, com.querydsl.core.types.Expression<String> nickname, com.querydsl.core.types.Expression<Long> userId, com.querydsl.core.types.Expression<String> answerComment) {
        super(GetQuestionOnQnAPage.class, new Class<?>[]{com.sidenow.freshgreenish.domain.question.entity.Question.class, String.class, long.class, String.class}, question, nickname, userId, answerComment);
    }

}

