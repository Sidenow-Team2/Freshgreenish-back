package com.sidenow.freshgreenish.domain.product.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
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

import java.util.ArrayList;
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
                .where(product.productId.eq(productId))
                .fetchOne();
    }

    @Override
    public GetProductDetail getProductDetailUponLogin(Long productId, Long userId) {
        return queryFactory
                .select(new QGetProductDetail(
                        product,
                        isLikes(product.productId, userId)
                )).from(product)
                .where(product.productId.eq(productId))
                .fetchOne();
    }

    @Override
    public Page<GetProductCategory> getProductCategory(String category, Integer sortId, Pageable pageable) {
        OrderSpecifier[] orderSpecifiers = createOrderSpecifier(sortId);

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
                .where(product.origin.eq(category)
                        .or(product.storageMethod.eq(category)))
                .orderBy(orderSpecifiers)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = results.size();
        return new PageImpl<>(results, pageable, total);
    }

    private JPQLQuery<Boolean> isLikes(NumberPath<Long> productId, Long userId) {
        return JPAExpressions.select(
                        new CaseBuilder()
                                .when(likes.isNotNull()).then(true)
                                .otherwise(false)
                ).from(likes)
                .where(likes.productId.eq(productId).and(likes.userId.eq(userId)));
    }

    private OrderSpecifier[] createOrderSpecifier(Integer sortId) {
        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();

        if (sortId == 2) orderSpecifiers.add(new OrderSpecifier(Order.DESC, product.purchaseCount));
        else if (sortId == 3) orderSpecifiers.add(new OrderSpecifier(Order.DESC, product.likeCount));
        else orderSpecifiers.add(new OrderSpecifier(Order.DESC, product.productId));

        return orderSpecifiers.toArray(new OrderSpecifier[orderSpecifiers.size()]);
    }
}
