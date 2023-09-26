package com.sidenow.freshgreenish.domain.basket.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sidenow.freshgreenish.domain.basket.dto.GetBasket;
import com.sidenow.freshgreenish.domain.basket.dto.QGetBasket;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sidenow.freshgreenish.domain.basket.entity.QProductBasket.productBasket;
import static com.sidenow.freshgreenish.domain.product.entity.QProduct.product;

@Repository
public class BasketRepositoryImpl implements CustomBasketRepository {
    private final JPAQueryFactory queryFactory;

    public BasketRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<GetBasket> getBasketList(Long userId, Pageable pageable) {
        List<GetBasket> results = queryFactory.select(
                new QGetBasket(
                        productBasket.productBasketId,
                        product.productId,
                        product.title,
                        product.productFirstImage,
                        productBasket.count,
                        product.price,
                        product.discountRate,
                        product.discountPrice
                )).from(productBasket)
                .leftJoin(product).on(product.productId.eq(productBasket.product.productId))
                .where(productBasket.basket.userId.eq(userId))
                .orderBy(productBasket.productBasketId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = results.size();
        return new PageImpl<>(results, pageable, total);
    }

    @Override
    public Integer getTotalBasketPrice(Long basketId) {
        return queryFactory
                .select(productBasket.totalPrice.sum())
                .from(productBasket)
                .where(productBasket.basket.basketId.eq(basketId))
                .fetchOne();
    }

    @Override
    public Integer getDiscountedTotalBasketPrice(Long basketId) {
        return queryFactory
                .select(productBasket.discountedTotalPrice.sum())
                .from(productBasket)
                .where(productBasket.basket.basketId.eq(basketId))
                .fetchOne();
    }

    @Override
    public Integer getProductPriceInBasket(Long productId, Long basketId) {
        return queryFactory
                .select(productBasket.discountedTotalPrice)
                .from(productBasket)
                .where(productBasket.basket.basketId.eq(basketId)
                        .and(productBasket.product.productId.eq(productId)))
                .fetchOne();
    }
}
