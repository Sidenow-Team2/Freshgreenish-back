package com.sidenow.freshgreenish.domain.product.repository;

import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sidenow.freshgreenish.domain.product.dto.*;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sidenow.freshgreenish.domain.likes.entity.QLikes.likes;
import static com.sidenow.freshgreenish.domain.product.entity.QProduct.product;

@Repository
public class ProductRepositoryImpl implements CustomProductRepository {
    private final JPAQueryFactory queryFactory;

    public ProductRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public GetProductDetail getProductDetail(Long productId) {
        return queryFactory
                .select(new QGetProductDetail(
                        product,
                        Expressions.constant(false)
                )).from(product)
                .distinct()
                .where(product.productId.eq(productId)
                        .and(product.deleted.eq(false)))
                .fetchOne();
    }

    @Override
    public GetProductDetail getProductDetailUponLogin(Long productId, Long userId) {
        return queryFactory
                .select(new QGetProductDetail(
                        product,
                        isLikes(product.productId, userId)
                )).from(product)
                .where(product.productId.eq(productId)
                        .and(product.deleted.eq(false)))
                .fetchOne();
    }

    @Override
    public Page<GetProductCategory> getProductCategoryOrderByProductId(String category, Pageable pageable) {
        List<GetProductCategory> results = queryFactory
                .select(new QGetProductCategory(
                        product.productId,
                        product.title,
                        product.price,
                        product.discountRate,
                        product.discountPrice,
                        product.productDetailImage
                )).from(product)
                .distinct()
                .where(product.deleted.eq(false)
                        .or(product.origin.eq(category))
                        .or(product.storageMethod.eq(category)))
                .orderBy(product.productId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = results.size();
        return new PageImpl<>(results, pageable, total);
    }

    @Override
    public Page<GetProductCategory> getProductCategoryOrderByPurchaseCount(String category, Pageable pageable) {
        List<GetProductCategory> results = queryFactory
                .select(new QGetProductCategory(
                        product.productId,
                        product.title,
                        product.price,
                        product.discountRate,
                        product.discountPrice,
                        product.productDetailImage
                )).from(product)
                .distinct()
                .where(product.deleted.eq(false)
                        .or(product.origin.eq(category))
                        .or(product.storageMethod.eq(category)))
                .orderBy(product.purchaseCount.desc(), product.productId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = results.size();
        return new PageImpl<>(results, pageable, total);
    }

    @Override
    public Page<GetProductCategory> getProductCategoryOrderByLikeCount(String category, Pageable pageable) {
        List<GetProductCategory> results = queryFactory
                .select(new QGetProductCategory(
                        product.productId,
                        product.title,
                        product.price,
                        product.discountRate,
                        product.discountPrice,
                        product.productDetailImage
                )).from(product)
                .distinct()
                .where(product.deleted.eq(false)
                        .or(product.origin.eq(category))
                        .or(product.storageMethod.eq(category)))
                .orderBy(product.likeCount.desc(), product.productId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = results.size();
        return new PageImpl<>(results, pageable, total);
    }

    @Override
    public Boolean isDeleted(Long productId) {
        return queryFactory
                .select(product.deleted)
                .from(product)
                .where(product.productId.eq(productId))
                .fetchOne();
    }

    @Override
    public Integer getPrice(Long productId) {
        return queryFactory
                .select(product.price)
                .from(product)
                .where(product.productId.eq(productId))
                .fetchOne();
    }

    @Override
    public Integer getDiscountPrice(Long productId) {
        return queryFactory
                .select(product.discountPrice)
                .from(product)
                .where(product.productId.eq(productId))
                .fetchOne();
    }

    private JPQLQuery<Boolean> isLikes(NumberPath<Long> productId, Long userId) {
        return JPAExpressions.select(
                        new CaseBuilder()
                                .when(likes.isNotNull()).then(true)
                                .otherwise(false)
                ).from(likes)
                .where(likes.productId.eq(productId).and(likes.userId.eq(userId)));
    }
}
