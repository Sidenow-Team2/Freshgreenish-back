package com.sidenow.freshgreenish.domain.purchase.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PurchaseInfo {
    private Long purchaseId;
    private String purchaseNumber;
    private String orderName;
    private Integer availablePoints;
    private Integer totalPrice;

    @Builder
    @QueryProjection
    public PurchaseInfo(Long purchaseId, String purchaseNumber, String orderName,
                        Integer availablePoints, Integer totalPrice) {
        this.purchaseId = purchaseId;
        this.purchaseNumber = purchaseNumber;
        this.orderName = orderName;
        this.availablePoints = availablePoints;
        this.totalPrice = totalPrice;
    }
}
