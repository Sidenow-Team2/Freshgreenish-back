package com.sidenow.freshgreenish.domain.product.service;

import com.sidenow.freshgreenish.domain.product.dto.GetProductCategory;
import com.sidenow.freshgreenish.domain.product.dto.GetProductDetail;
import com.sidenow.freshgreenish.domain.product.entity.Product;
import com.sidenow.freshgreenish.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductDbService {
    private final ProductRepository productRepository;

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public Product findById(Long productId) {
        return productRepository.findById(productId).orElseThrow(() -> new RuntimeException("존재 하지 않는 상품입니다."));
    }

    public GetProductDetail getProductDetail(Long productId, Long userId) {
        if (userId != null) return productRepository.getProductDetailUponLogin(productId, userId);
        return productRepository.getProductDetail(productId);
    }

    public Page<GetProductCategory> getProductCategory(String category, Integer sortId, Pageable pageable) {
        return productRepository.getProductCategory(category, sortId, pageable);
    }
}
