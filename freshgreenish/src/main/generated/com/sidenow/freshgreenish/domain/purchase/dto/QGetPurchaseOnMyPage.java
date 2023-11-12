package com.sidenow.freshgreenish.domain.purchase.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.sidenow.freshgreenish.domain.purchase.dto.QGetPurchaseOnMyPage is a Querydsl Projection type for GetPurchaseOnMyPage
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QGetPurchaseOnMyPage extends ConstructorExpression<GetPurchaseOnMyPage> {

    private static final long serialVersionUID = -1961768290L;

    public QGetPurchaseOnMyPage(com.querydsl.core.types.Expression<? extends com.sidenow.freshgreenish.domain.purchase.entity.Purchase> purchase, com.querydsl.core.types.Expression<String> orderName, com.querydsl.core.types.Expression<? extends com.sidenow.freshgreenish.domain.address.entity.Address> address, com.querydsl.core.types.Expression<String> title, com.querydsl.core.types.Expression<String> productFirstImage, com.querydsl.core.types.Expression<Integer> discountedPrice, com.querydsl.core.types.Expression<Long> productId) {
        super(GetPurchaseOnMyPage.class, new Class<?>[]{com.sidenow.freshgreenish.domain.purchase.entity.Purchase.class, String.class, com.sidenow.freshgreenish.domain.address.entity.Address.class, String.class, String.class, int.class, long.class}, purchase, orderName, address, title, productFirstImage, discountedPrice, productId);
    }

}

