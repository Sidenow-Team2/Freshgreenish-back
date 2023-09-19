package com.sidenow.freshgreenish.domain.purchase.service;

import com.sidenow.freshgreenish.domain.purchase.entity.ProductPurchase;
import com.sidenow.freshgreenish.domain.purchase.repository.ProductPurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductPurchaseDbService {

    private final ProductPurchaseRepository productPurchaseRepository;

    public ProductPurchase save(ProductPurchase productPurchase) {
        return productPurchaseRepository.save(productPurchase);
    }
}
