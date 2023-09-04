package com.sidenow.freshgreenish.domain.product.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductImageVO {
    private Long productImageId;
    private String filePath;

    @Builder
    public ProductImageVO(Long productImageId, String filePath) {
        this.productImageId = productImageId;
        this.filePath = filePath;
    }
}
