package com.sidenow.freshgreenish.domain.question.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QQuestion is a Querydsl query type for Question
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQuestion extends EntityPathBase<Question> {

    private static final long serialVersionUID = 1474944818L;

    public static final QQuestion question = new QQuestion("question");

    public final com.sidenow.freshgreenish.global.utils.QAuditable _super = new com.sidenow.freshgreenish.global.utils.QAuditable(this);

    public final NumberPath<Long> answerId = createNumber("answerId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    public final BooleanPath isSecretMessage = createBoolean("isSecretMessage");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt = _super.lastModifiedAt;

    public final NumberPath<Long> productId = createNumber("productId", Long.class);

    public final EnumPath<QuestionAnswerStatus> questionAnswerStatus = createEnum("questionAnswerStatus", QuestionAnswerStatus.class);

    public final StringPath questionContent = createString("questionContent");

    public final NumberPath<Long> questionId = createNumber("questionId", Long.class);

    public final StringPath questionTitle = createString("questionTitle");

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QQuestion(String variable) {
        super(Question.class, forVariable(variable));
    }

    public QQuestion(Path<? extends Question> path) {
        super(path.getType(), path.getMetadata());
    }

    public QQuestion(PathMetadata metadata) {
        super(Question.class, metadata);
    }

}

