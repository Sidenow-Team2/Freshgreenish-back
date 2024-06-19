package com.sidenow.freshgreenish.domain.product.repository;

import com.sidenow.freshgreenish.domain.product.dto.GetProductCategory;
import com.sidenow.freshgreenish.domain.product.dto.GetProductDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomProductRepository {
    GetProductDetail getProductDetail(Long productId);
    GetProductDetail getProductDetailUponLogin(Long productId, Long userId);

    Page<GetProductCategory> getMainPage(Pageable pageable);
    Page<GetProductCategory> getProductCategoryOrderByProductId(String category, Pageable pageable);
    Page<GetProductCategory> getProductCategoryOrderByPurchaseCount(String category, Pageable pageable);
    Page<GetProductCategory> getProductCategoryOrderByLikeCount(String category, Pageable pageable);

    Page<GetProductCategory> searchProductCategoryForTitleOrderByProductId(List<String> titles, String category, Pageable pageable);
    Page<GetProductCategory> searchProductCategoryForTitleOrderByPurchaseCount(List<String> titles, String category, Pageable pageable);
    Page<GetProductCategory> searchProductCategoryForTitleOrderByLikeCount(List<String> titles, String category, Pageable pageable);

    Boolean isDeleted(Long productId);

    Integer getPrice(Long productId);
    Integer getDiscountPrice(Long productId);

    String getProductTitle(Long purchaseId);

}
