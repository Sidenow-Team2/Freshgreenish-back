package com.sidenow.freshgreenish.domain.basket.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class DeleteBasket {
    private List<Long> deleteProductId;
}
