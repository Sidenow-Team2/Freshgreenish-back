package com.sidenow.freshgreenish.domain.purchase.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.sidenow.freshgreenish.domain.purchase.dto.QPriceInfo is a Querydsl Projection type for PriceInfo
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QPriceInfo extends ConstructorExpression<PriceInfo> {

    private static final long serialVersionUID = 613308356L;

    public QPriceInfo(com.querydsl.core.types.Expression<Integer> availablePoints, com.querydsl.core.types.Expression<Integer> totalPrice, com.querydsl.core.types.Expression<String> purchaseStatus) {
        super(PriceInfo.class, new Class<?>[]{int.class, int.class, String.class}, availablePoints, totalPrice, purchaseStatus);
    }

}

