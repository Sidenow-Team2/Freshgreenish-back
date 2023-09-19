package com.sidenow.freshgreenish.domain.purchase.service;

import com.sidenow.freshgreenish.domain.purchase.entity.Purchase;
import com.sidenow.freshgreenish.domain.purchase.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PurchaseDbService {

    private final PurchaseRepository purchaseRepository;

    public Purchase save(Purchase purchase) {
        return purchaseRepository.save(purchase);
    }

    public Optional<Purchase> findById(Long purchaseId) {
        return purchaseRepository.findById(purchaseId);
    }
}
