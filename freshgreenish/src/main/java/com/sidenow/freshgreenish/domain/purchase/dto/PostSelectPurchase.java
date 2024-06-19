package com.sidenow.freshgreenish.domain.purchase.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PostSelectPurchase {
    private List<Long> productIdList;
    private Integer totalPrice;
}
