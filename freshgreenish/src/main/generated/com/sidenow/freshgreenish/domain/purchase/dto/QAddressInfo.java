package com.sidenow.freshgreenish.domain.purchase.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.sidenow.freshgreenish.domain.purchase.dto.QAddressInfo is a Querydsl Projection type for AddressInfo
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QAddressInfo extends ConstructorExpression<AddressInfo> {

    private static final long serialVersionUID = -1649599057L;

    public QAddressInfo(com.querydsl.core.types.Expression<? extends com.sidenow.freshgreenish.domain.address.entity.Address> address) {
        super(AddressInfo.class, new Class<?>[]{com.sidenow.freshgreenish.domain.address.entity.Address.class}, address);
    }

}

