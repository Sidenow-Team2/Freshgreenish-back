package com.sidenow.freshgreenish.domain.review.repository;

import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sidenow.freshgreenish.domain.review.dto.GetReviewOnMyPage;
import com.sidenow.freshgreenish.domain.review.dto.GetReviewOnProductDetail;
import com.sidenow.freshgreenish.domain.review.dto.QGetReviewOnMyPage;
import com.sidenow.freshgreenish.domain.review.dto.QGetReviewOnProductDetail;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sidenow.freshgreenish.domain.likes.entity.QLikes.likes;
import static com.sidenow.freshgreenish.domain.product.entity.QProduct.product;
import static com.sidenow.freshgreenish.domain.review.entity.QReview.review;
import static com.sidenow.freshgreenish.domain.user.entity.QUser.user;

@Repository
public class ReviewRepositoryImpl implements CustomReviewRepository {
    private final JPAQueryFactory queryFactory;

    public ReviewRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<GetReviewOnProductDetail> getReviewOnProductDetailOrderByReviewId(Long productId, Pageable pageable) {
        List<GetReviewOnProductDetail> results = queryFactory
                .select(new QGetReviewOnProductDetail(
                        review,
                        product.productId,
                        user.userId,
                        user.nickname,
                        Expressions.constant(false)
                )).from(review)
                .leftJoin(product).on(review.productId.eq(product.productId))
                .leftJoin(user).on(review.userId.eq(user.userId))
                .where(review.productId.eq(productId))
                .orderBy(review.reviewId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = results.size();
        return new PageImpl<>(results, pageable, total);
    }

    @Override
    public Page<GetReviewOnProductDetail> getReviewOnProductDetailOrderByReviewIdUponLogin(Long userId, Long productId, Pageable pageable) {
        List<GetReviewOnProductDetail> results = queryFactory
                .select(new QGetReviewOnProductDetail(
                        review,
                        product.productId,
                        user.userId,
                        user.nickname,
                        isReviewLikes(review.reviewId, userId)
                )).from(review)
                .leftJoin(product).on(review.productId.eq(product.productId))
                .leftJoin(user).on(review.userId.eq(user.userId))
                .where(review.productId.eq(productId))
                .orderBy(review.reviewId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = results.size();
        return new PageImpl<>(results, pageable, total);
    }

    @Override
    public Page<GetReviewOnProductDetail> getReviewOnProductDetailOrderByLikeCount(Long productId, Pageable pageable) {
        List<GetReviewOnProductDetail> results = queryFactory
                .select(new QGetReviewOnProductDetail(
                        review,
                        product.productId,
                        user.userId,
                        user.nickname,
                        Expressions.constant(false)
                )).from(review)
                .leftJoin(product).on(review.productId.eq(product.productId))
                .leftJoin(user).on(review.userId.eq(user.userId))
                .where(review.productId.eq(productId))
                .orderBy(review.likeCount.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = results.size();
        return new PageImpl<>(results, pageable, total);
    }

    @Override
    public Page<GetReviewOnProductDetail> getReviewOnProductDetailOrderByLikeCountUponLogin(Long userId, Long productId, Pageable pageable) {
        List<GetReviewOnProductDetail> results = queryFactory
                .select(new QGetReviewOnProductDetail(
                        review,
                        product.productId,
                        user.userId,
                        user.nickname,
                        isReviewLikes(review.reviewId, userId)
                )).from(review)
                .leftJoin(product).on(review.productId.eq(product.productId))
                .leftJoin(user).on(review.userId.eq(user.userId))
                .where(review.productId.eq(productId))
                .orderBy(review.likeCount.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = results.size();
        return new PageImpl<>(results, pageable, total);
    }

    @Override
    public Page<GetReviewOnMyPage> getReviewOnMyPage(Long userId, Pageable pageable) {
        List<GetReviewOnMyPage> results = queryFactory
                .select(new QGetReviewOnMyPage(
                        review,
                        product.productId,
                        product.title
                )).from(review)
                .leftJoin(product).on(review.productId.eq(product.productId))
                .where(review.userId.eq(userId))
                .orderBy(review.reviewId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = results.size();
        return new PageImpl<>(results, pageable, total);
    }

    private JPQLQuery<Boolean> isReviewLikes(NumberPath<Long> productId, Long userId) {
        return JPAExpressions.select(
                        new CaseBuilder()
                                .when(likes.isNotNull()).then(true)
                                .otherwise(false)
                ).from(likes)
                .where(likes.productId.eq(productId).and(likes.userId.eq(userId)));
    }

}
