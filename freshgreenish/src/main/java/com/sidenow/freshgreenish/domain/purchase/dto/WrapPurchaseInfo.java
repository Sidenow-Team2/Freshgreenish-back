package com.sidenow.freshgreenish.domain.purchase.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class WrapPurchaseInfo<T> {
    private List<T> orderList;
    private AddressInfo addressInfo;
    private PriceInfo priceInfo;

    @Builder
    public WrapPurchaseInfo(List<T> orderList, AddressInfo addressInfo, PriceInfo priceInfo) {
        this.orderList = orderList;
        this.addressInfo = addressInfo;
        this.priceInfo = priceInfo;
    }
}
