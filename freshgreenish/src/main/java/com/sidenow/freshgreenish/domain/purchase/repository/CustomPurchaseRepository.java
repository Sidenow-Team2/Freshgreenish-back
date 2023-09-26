package com.sidenow.freshgreenish.domain.purchase.repository;

import com.sidenow.freshgreenish.domain.product.entity.Product;
import com.sidenow.freshgreenish.domain.purchase.dto.AddressInfo;
import com.sidenow.freshgreenish.domain.purchase.dto.GetPurchaseOnMyPage;
import com.sidenow.freshgreenish.domain.purchase.dto.OrderList;
import com.sidenow.freshgreenish.domain.purchase.dto.PriceInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomPurchaseRepository {
    List<OrderList> getOrderListByPurchaseIdAndUserId(Long purchaseId, Long userId);
    List<OrderList> getBasketOrderList(Long basketId, Long purchaseId, Long userId);
    Page<GetPurchaseOnMyPage> getPurchaseOnMyPage(Long userId, Pageable pageable);

    List<Product> getProductIdList(Long purchaseId, Long userId);

    AddressInfo getAddressInfo(Long addressId);

    PriceInfo getPriceInfo(Long purchaseId, Long userId);
}
