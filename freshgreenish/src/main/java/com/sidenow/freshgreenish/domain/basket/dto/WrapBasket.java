package com.sidenow.freshgreenish.domain.basket.dto;

import com.sidenow.freshgreenish.domain.dto.PageInfo;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class WrapBasket<T> {
    private List<T> data;
    private TotalPriceInfo totalPriceInfo;
    private PageInfo pageInfo;

    @Builder
    public WrapBasket(Page<T> data, TotalPriceInfo totalPriceInfo) {
        this.data = data.getContent();
        this.totalPriceInfo = totalPriceInfo;
        this.pageInfo = PageInfo.builder()
                .page(data.getNumber() + 1)
                .size(data.getSize())
                .totalElements(data.getTotalElements())
                .totalPages(data.getTotalPages())
                .build();
    }
}
