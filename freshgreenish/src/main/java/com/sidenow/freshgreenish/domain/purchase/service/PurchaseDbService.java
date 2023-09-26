package com.sidenow.freshgreenish.domain.purchase.service;

import com.sidenow.freshgreenish.domain.product.entity.Product;
import com.sidenow.freshgreenish.domain.purchase.dto.AddressInfo;
import com.sidenow.freshgreenish.domain.purchase.dto.GetPurchaseOnMyPage;
import com.sidenow.freshgreenish.domain.purchase.dto.OrderList;
import com.sidenow.freshgreenish.domain.purchase.dto.PriceInfo;
import com.sidenow.freshgreenish.domain.purchase.entity.Purchase;
import com.sidenow.freshgreenish.domain.purchase.repository.PurchaseRepository;
import com.sidenow.freshgreenish.global.exception.BusinessLogicException;
import com.sidenow.freshgreenish.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseDbService {
    private final PurchaseRepository purchaseRepository;

    public Purchase savePurchase(Purchase purchase) {
        return purchaseRepository.save(purchase);
    }

    public Purchase saveAndReturnPurchase(Purchase purchase) {
        return purchaseRepository.save(purchase);
    }

    public Purchase ifExistsReturnPurchase(Long purchaseId) {
        return purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.PURCHASE_NOT_FOUND));
    }

    public List<OrderList> getOrderListByPurchaseIdAndUserId(Long purchaseId, Long userId) {
        return purchaseRepository.getOrderListByPurchaseIdAndUserId(purchaseId, userId);
    }

    public List<OrderList> getBasketOrderList(Long basketId, Long purchaseId, Long userId) {
        return purchaseRepository.getBasketOrderList(basketId, purchaseId, userId);
    }

    public Page<GetPurchaseOnMyPage> getPurchaseOnMyPage(Long useId, Pageable pageable) {
        return purchaseRepository.getPurchaseOnMyPage(useId, pageable);
    }

    public List<Product> getProductIdList(Long purchaseId, Long userId) {
        return purchaseRepository.getProductIdList(purchaseId, userId);
    }

    public AddressInfo getAddressInfo(Long addressId) {
        return purchaseRepository.getAddressInfo(addressId);
    }

    public PriceInfo getPriceInfo(Long purchaseId, Long userId) {
        return purchaseRepository.getPriceInfo(purchaseId, userId);
    }
}
