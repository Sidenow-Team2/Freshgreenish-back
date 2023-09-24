package com.sidenow.freshgreenish.domain.product.repository;

import com.sidenow.freshgreenish.domain.product.dto.GetProductCategory;
import com.sidenow.freshgreenish.domain.product.dto.GetProductDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomProductRepository {
    GetProductDetail getProductDetail(Long productId);
    GetProductDetail getProductDetailUponLogin(Long productId, Long userId);
    Page<GetProductCategory> getProductCategoryOrderByProductId(String category, Pageable pageable);
    Page<GetProductCategory> getProductCategoryOrderByPurchaseCount(String category, Pageable pageable);
    Page<GetProductCategory> getProductCategoryOrderByLikeCount(String category, Pageable pageable);
    Boolean isDeleted(Long productId);

}
