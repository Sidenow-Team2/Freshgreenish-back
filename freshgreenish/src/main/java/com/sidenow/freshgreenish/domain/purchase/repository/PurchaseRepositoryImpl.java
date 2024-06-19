package com.sidenow.freshgreenish.domain.purchase.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sidenow.freshgreenish.domain.product.entity.Product;
import com.sidenow.freshgreenish.domain.purchase.dto.*;
import com.sidenow.freshgreenish.domain.purchase.entity.PurchaseStatus;
import com.sidenow.freshgreenish.domain.purchase.entity.SubscriptionStatus;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sidenow.freshgreenish.domain.address.entity.QAddress.address;
import static com.sidenow.freshgreenish.domain.basket.entity.QProductBasket.productBasket;
import static com.sidenow.freshgreenish.domain.delivery.entity.QDelivery.delivery;
import static com.sidenow.freshgreenish.domain.payment.entity.QPaymentInfo.paymentInfo;
import static com.sidenow.freshgreenish.domain.product.entity.QProduct.product;
import static com.sidenow.freshgreenish.domain.purchase.entity.QProductPurchase.productPurchase;
import static com.sidenow.freshgreenish.domain.purchase.entity.QPurchase.purchase;
import static com.sidenow.freshgreenish.domain.user.entity.QUser.user;

@Repository
public class PurchaseRepositoryImpl implements CustomPurchaseRepository{
    private final JPAQueryFactory queryFactory;

    public PurchaseRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<OrderList> getOrderListByPurchaseIdAndUserId(Long purchaseId, Long userId) {
        return queryFactory.select(
                        new QOrderList(
                                product.productId,
                                product.title,
                                product.productFirstImage,
                                purchase.count,
                                product.discountPrice
                        ))
                .from(purchase)
                .leftJoin(product).on(product.productId.eq(purchase.productId))
                .where(purchase.purchaseId.eq(purchaseId)
                        .and(purchase.userId.eq(userId)))
                .fetch();
    }

    @Override
    public List<OrderList> getBasketOrderList(Long basketId, Long purchaseId, Long userId) {
        return queryFactory.select(
                        new QOrderList(
                                product.productId,
                                product.title,
                                product.productFirstImage,
                                productBasket.count,
                                product.discountPrice
                        ))
                .from(productPurchase)
                .leftJoin(product).on(product.productId.eq(productPurchase.product.productId))
                .leftJoin(productBasket).on(productBasket.product.productId.eq(productPurchase.product.productId))
                .where(productPurchase.purchase.purchaseId.eq(purchaseId)
                        .and(productBasket.basket.basketId.eq(basketId))
                        .and(purchase.userId.eq(userId))
                        .and(productBasket.isRegular.eq(false)))
                .fetch();
    }

    @Override
    public List<OrderList> getRegularOrderList(Long basketId, Long purchaseId, Long userId) {
        return queryFactory.select(
                        new QOrderList(
                                product.productId,
                                product.title,
                                product.productFirstImage,
                                productBasket.count,
                                product.discountPrice
                        ))
                .from(productPurchase)
                .leftJoin(product).on(product.productId.eq(productPurchase.product.productId))
                .leftJoin(productBasket).on(productBasket.product.productId.eq(productPurchase.product.productId))
                .where(productPurchase.purchase.purchaseId.eq(purchaseId)
                        .and(productBasket.basket.basketId.eq(basketId))
                        .and(purchase.userId.eq(userId))
                        .and(productBasket.isRegular.eq(true)))
                .fetch();
    }

    @Override
    public Page<GetPurchaseOnMyPage> getPurchaseOnMyPage(Long userId, Pageable pageable) {
        List<GetPurchaseOnMyPage> result = queryFactory.select(
                        new QGetPurchaseOnMyPage(
                                purchase,
                                paymentInfo.orderName,
                                address,
                                product.title,
                                product.productFirstImage,
                                product.discountPrice,
                                product.productId
                                )
                ).from(purchase)
                .leftJoin(paymentInfo).on(paymentInfo.paymentId.eq(purchase.purchaseId))
                .leftJoin(product).on(product.productId.eq(purchase.productId))
                .leftJoin(address).on(address.user.userId.eq(purchase.userId))
                .distinct()
                .where(purchase.userId.eq(userId)
                        .and(purchase.isRegularDelivery.eq(false))
                        .and(address.addressId.eq(purchase.addressId))
                        .and(purchase.purchaseStatus.ne(PurchaseStatus.PAY_IN_PROGRESS)))
                .orderBy(purchase.purchaseId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = result.size();
        return new PageImpl<>(result, pageable, total);
    }

    @Override
    public Page<GetSubscriptionOnMyPage> getSubscriptionOnMyPage(Long userId, Pageable pageable) {
        List<GetSubscriptionOnMyPage> result = queryFactory.select(
                        new QGetSubscriptionOnMyPage(
                                purchase,
                                paymentInfo.orderName,
                                address,
                                product.title,
                                product.productFirstImage,
                                product.discountPrice,
                                product.productId,
                                delivery.deliveryId,
                                delivery.deliveryDate,
                                delivery.firstPaymentDate,
                                delivery.thisMonthPaymentDate,
                                delivery.nextPaymentDate
                        )
                ).from(purchase)
                .leftJoin(paymentInfo).on(paymentInfo.paymentId.eq(purchase.purchaseId))
                .leftJoin(product).on(product.productId.eq(purchase.productId))
                .leftJoin(address).on(address.user.userId.eq(purchase.userId))
                .leftJoin(delivery).on(delivery.purchaseId.eq(purchase.purchaseId))
                .distinct()
                .where(purchase.userId.eq(userId)
                        .and(purchase.isRegularDelivery.eq(true))
                        .and(purchase.subscriptionStatus.ne(SubscriptionStatus.NOT_USE_SUBSCRIPTION)))
                .orderBy(purchase.purchaseId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = result.size();
        return new PageImpl<>(result, pageable, total);
    }

    @Override
    public List<Product> getProductIdList(Long purchaseId, Long userId) {
        return queryFactory
                .select(productPurchase.product)
                .from(productPurchase)
                .leftJoin(purchase).on(purchase.eq(productPurchase.purchase))
                .where(purchase.purchaseId.eq(purchaseId)
                        .and(purchase.userId.eq(userId)))
                .fetch();
    }

    @Override
    public AddressInfo getAddressInfo(Long addressId) {
        return queryFactory
                .select(
                        new QAddressInfo(
                                address
                        )
                )
                .from(address)
                .where(address.addressId.eq(addressId))
                .fetchOne();
    }

    @Override
    public PriceInfo getPriceInfo(Long purchaseId, Long userId) {
        return queryFactory
                .select(
                        new QPriceInfo(
                                user.saved_money,
                                purchase.totalPrice,
                                purchase.purchaseStatus.stringValue())
                )
                .from(purchase)
                .leftJoin(user).on(purchase.userId.eq(user.userId))
                .where(purchase.purchaseId.eq(purchaseId)
                        .and(purchase.userId.eq(userId)))
                .fetchOne();
    }

    @Override
    public Integer getSubscriptCount(Long userId) {
        return Math.toIntExact(queryFactory
                .select(purchase.count())
                .from(purchase)
                .where(purchase.userId.eq(userId)
                        .and(purchase.isRegularDelivery.eq(true))
                        .and(purchase.subscriptionStatus.eq(SubscriptionStatus.DURING_SUBSCRIPTION)))
                .fetchOne());
    }
}
