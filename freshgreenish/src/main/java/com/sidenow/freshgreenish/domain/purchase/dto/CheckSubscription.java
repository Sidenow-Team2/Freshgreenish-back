package com.sidenow.freshgreenish.domain.purchase.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CheckSubscription {
    private Integer regularPurchaseCount;
    private Boolean isExists;

    @Builder
    public CheckSubscription(Integer regularPurchaseCount, Boolean isExists) {
        this.regularPurchaseCount = regularPurchaseCount;
        this.isExists = isExists;
    }
}
