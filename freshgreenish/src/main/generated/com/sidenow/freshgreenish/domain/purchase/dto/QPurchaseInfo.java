package com.sidenow.freshgreenish.domain.purchase.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.sidenow.freshgreenish.domain.purchase.dto.QPurchaseInfo is a Querydsl Projection type for PurchaseInfo
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QPurchaseInfo extends ConstructorExpression<PurchaseInfo> {

    private static final long serialVersionUID = -1686237182L;

    public QPurchaseInfo(com.querydsl.core.types.Expression<Long> purchaseId, com.querydsl.core.types.Expression<String> purchaseNumber, com.querydsl.core.types.Expression<String> orderName, com.querydsl.core.types.Expression<Integer> availablePoints, com.querydsl.core.types.Expression<Integer> totalPrice) {
        super(PurchaseInfo.class, new Class<?>[]{long.class, String.class, String.class, int.class, int.class}, purchaseId, purchaseNumber, orderName, availablePoints, totalPrice);
    }

}

