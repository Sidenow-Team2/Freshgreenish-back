package com.sidenow.freshgreenish.domain.purchase.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.sidenow.freshgreenish.domain.purchase.dto.QGetSubscriptionOnMyPage is a Querydsl Projection type for GetSubscriptionOnMyPage
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QGetSubscriptionOnMyPage extends ConstructorExpression<GetSubscriptionOnMyPage> {

    private static final long serialVersionUID = -1701062342L;

    public QGetSubscriptionOnMyPage(com.querydsl.core.types.Expression<? extends com.sidenow.freshgreenish.domain.purchase.entity.Purchase> purchase, com.querydsl.core.types.Expression<String> orderName, com.querydsl.core.types.Expression<? extends com.sidenow.freshgreenish.domain.address.entity.Address> address, com.querydsl.core.types.Expression<String> title, com.querydsl.core.types.Expression<String> productFirstImage, com.querydsl.core.types.Expression<Integer> discountedPrice, com.querydsl.core.types.Expression<Long> productId, com.querydsl.core.types.Expression<Long> deliveryId, com.querydsl.core.types.Expression<java.time.LocalDateTime> deliveryDate, com.querydsl.core.types.Expression<java.time.LocalDateTime> firstPaymentDate, com.querydsl.core.types.Expression<java.time.LocalDateTime> thisMonthPaymentDate, com.querydsl.core.types.Expression<java.time.LocalDateTime> nextPaymentDate) {
        super(GetSubscriptionOnMyPage.class, new Class<?>[]{com.sidenow.freshgreenish.domain.purchase.entity.Purchase.class, String.class, com.sidenow.freshgreenish.domain.address.entity.Address.class, String.class, String.class, int.class, long.class, long.class, java.time.LocalDateTime.class, java.time.LocalDateTime.class, java.time.LocalDateTime.class, java.time.LocalDateTime.class}, purchase, orderName, address, title, productFirstImage, discountedPrice, productId, deliveryId, deliveryDate, firstPaymentDate, thisMonthPaymentDate, nextPaymentDate);
    }

}

