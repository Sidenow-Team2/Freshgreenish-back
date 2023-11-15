package com.sidenow.freshgreenish.domain.review.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QReviewLikes is a Querydsl query type for ReviewLikes
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReviewLikes extends EntityPathBase<ReviewLikes> {

    private static final long serialVersionUID = -1321721466L;

    public static final QReviewLikes reviewLikes = new QReviewLikes("reviewLikes");

    public final NumberPath<Long> reviewId = createNumber("reviewId", Long.class);

    public final NumberPath<Long> reviewLikesId = createNumber("reviewLikesId", Long.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QReviewLikes(String variable) {
        super(ReviewLikes.class, forVariable(variable));
    }

    public QReviewLikes(Path<? extends ReviewLikes> path) {
        super(path.getType(), path.getMetadata());
    }

    public QReviewLikes(PathMetadata metadata) {
        super(ReviewLikes.class, metadata);
    }

}

