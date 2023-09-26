package com.sidenow.freshgreenish.domain.purchase.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class GetPurchaseInfo {
    List<OrderList> orderLists;
    AddressInfo addressInfo;
    PriceInfo priceInfo;

    @Builder
    public GetPurchaseInfo(List<OrderList> orderLists, AddressInfo addressInfo, PriceInfo priceInfo) {
        this.orderLists = orderLists;
        this.addressInfo = addressInfo;
        this.priceInfo = priceInfo;
    }
}
