package com.sidenow.freshgreenish.domain.purchase.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PriceInfo {
    private Integer availablePoints;
    private Integer totalPrice;
    private String purchaseStatus;

    @Builder
    @QueryProjection
    public PriceInfo(Integer availablePoints, Integer totalPrice, String purchaseStatus) {
        this.availablePoints = availablePoints;
        this.totalPrice = totalPrice;
        this.purchaseStatus = purchaseStatus;
    }
}
