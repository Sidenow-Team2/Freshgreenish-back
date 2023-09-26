package com.sidenow.freshgreenish.domain.purchase.service;

import com.sidenow.freshgreenish.domain.basket.entity.Basket;
import com.sidenow.freshgreenish.domain.basket.service.BasketDbService;
import com.sidenow.freshgreenish.domain.product.entity.Product;
import com.sidenow.freshgreenish.domain.product.service.ProductDbService;
import com.sidenow.freshgreenish.domain.purchase.dto.*;
import com.sidenow.freshgreenish.domain.purchase.entity.ProductPurchase;
import com.sidenow.freshgreenish.domain.purchase.entity.Purchase;
import com.sidenow.freshgreenish.domain.purchase.entity.PurchaseStatus;
import com.sidenow.freshgreenish.domain.user.entity.User;
import com.sidenow.freshgreenish.domain.user.service.UserDbService;
import com.sidenow.freshgreenish.global.exception.BusinessLogicException;
import com.sidenow.freshgreenish.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PurchaseService {
    private final PurchaseDbService purchaseDbService;
    private final ProductDbService productDbService;
    private final BasketDbService basketDbService;
    private final UserDbService userDbService;

    public void createSinglePurchase(Long productId, OAuth2User oauth, PostPurchase post) {
        User findUser = userDbService.findUserByEmail(oauth);
        Basket findBasket = basketDbService.ifExistsReturnBasket(findUser.getUserId());
        basketDbService.saveBasket(findBasket);

        Purchase purchase = Purchase.builder()
                .productId(productId)
                .userId(findUser.getUserId())
                .addressId(1L) // 수정 예정
                .usedPoints(0)
                .count(post.getCount())
                .isRegularDelivery(false)
                .totalPrice(productDbService.getPrice(productId) * post.getCount())
                .totalPriceBeforeUsePoint(productDbService.getPrice(productId) * post.getCount())
                .build();

        purchase.setStatus(PurchaseStatus.PAY_IN_PROGRESS);

        Purchase findPurchase = purchaseDbService.saveAndReturnPurchase(purchase);
        findPurchase.setPurchaseNumber(createPurchaseNumber(findPurchase.getCreatedAt()));

        Product findProduct = productDbService.ifExistsReturnProduct(productId);

        ProductPurchase productPurchase = ProductPurchase.builder()
                .product(findProduct)
                .purchase(findPurchase)
                .build();

        findPurchase.addProductPurchase(productPurchase);

        productPurchase.addPurchase(findPurchase);
        productPurchase.addProduct(findProduct);

        purchaseDbService.savePurchase(findPurchase);
    }

    public void createSelectPurchase(OAuth2User oauth, PostSelectPurchase post) {
        User findUser = userDbService.findUserByEmail(oauth);
        Basket findBasket = basketDbService.ifExistsReturnBasketByUserId(findUser.getUserId());

        Purchase purchase = Purchase.builder()
                .productId(post.getProductIdList().get(0))
                .basketId(findBasket.getBasketId())
                .userId(findUser.getUserId())
                .addressId(1L) // 수정 예정
                .usedPoints(0)
                .count(1)
                .isRegularDelivery(false)
                .build();

        purchase.setStatus(PurchaseStatus.PAY_IN_PROGRESS);

        Purchase findPurchase = purchaseDbService.saveAndReturnPurchase(purchase);

        findPurchase.setPurchaseNumber(createPurchaseNumber(findPurchase.getCreatedAt()));
        findPurchase.setTotalPrice(calculateTotalPrice(post.getProductIdList(), findBasket.getBasketId()));
        findPurchase.setTotalPriceBeforeUsePoint(calculateTotalPrice(post.getProductIdList(), findBasket.getBasketId()));

        createAndSaveProductPurchase(post.getProductIdList(), findPurchase);

        purchaseDbService.savePurchase(findPurchase);
    }

    public void createAllPurchase(OAuth2User oauth) {
        User findUser = userDbService.findUserByEmail(oauth);
        Basket findBasket = basketDbService.ifExistsReturnBasketByUserId(findUser.getUserId());

        Purchase purchase = Purchase.builder()
                .productId(findBasket.getProductBasket().get(0).getProduct().getProductId())
                .basketId(findBasket.getBasketId())
                .userId(findUser.getUserId())
                .addressId(1L) // 수정 예정
                .usedPoints(0)
                .count(1)
                .isRegularDelivery(false)
                .totalPrice(findBasket.getDiscountedBasketTotalPrice())
                .totalPriceBeforeUsePoint(findBasket.getDiscountedBasketTotalPrice())
                .build();

        purchase.setStatus(PurchaseStatus.PAY_IN_PROGRESS);

        Purchase findPurchase = purchaseDbService.saveAndReturnPurchase(purchase);
        findPurchase.setPurchaseNumber(createPurchaseNumber(findPurchase.getCreatedAt()));

        List<Long> productIdList = findBasket.getProductBasket().stream()
                .map(m -> m.getProduct().getProductId())
                .collect(Collectors.toList());

        createAndSaveProductPurchase(productIdList, findPurchase);

        purchaseDbService.savePurchase(findPurchase);
    }

    public GetPurchaseInfo getPurchaseInfo(Long purchaseId, OAuth2User oauth) {
        User findUser = userDbService.findUserByEmail(oauth);
        Purchase findPurchase = purchaseDbService.ifExistsReturnPurchase(purchaseId);

        return GetPurchaseInfo.builder()
                .orderLists(purchaseDbService
                        .getOrderListByPurchaseIdAndUserId(purchaseId, findUser.getUserId()))
                .addressInfo(purchaseDbService.getAddressInfo(findPurchase.getAddressId()))
                .priceInfo(purchaseDbService.getPriceInfo(purchaseId, findUser.getUserId()))
                .build();
    }

    public GetPurchaseInfo getBasketPurchaseInfo(Long purchaseId, OAuth2User oauth) {
        User findUser = userDbService.findUserByEmail(oauth);
        Basket findBasket = basketDbService.ifExistsReturnBasketByUserId(findUser.getUserId());
        Purchase findPurchase = purchaseDbService.ifExistsReturnPurchase(purchaseId);

        return GetPurchaseInfo.builder()
                .orderLists(purchaseDbService
                        .getBasketOrderList(findBasket.getBasketId(), purchaseId, findUser.getUserId()))
                .addressInfo(purchaseDbService.getAddressInfo(findPurchase.getAddressId()))
                .priceInfo(purchaseDbService.getPriceInfo(purchaseId, findUser.getUserId()))
                .build();
    }

    public void usedPointInPurchase(Long purchaseId, OAuth2User oauth, PostUsePoint post) {
        Purchase findPurchase = purchaseDbService.ifExistsReturnPurchase(purchaseId);
        User user = userDbService.findUserByEmail(oauth);

        // TODO : test 용 코드, 추후 배포 시 코드 삭제
        user.setSaved_money(5000);
        userDbService.saveUser(user);

        if (user.getSaved_money() < post.getPoint()) {
            new BusinessLogicException(ExceptionCode.POINTS_CANNOT_EXCEEDED);
        }

        findPurchase.setTotalPrice(findPurchase.getTotalPriceBeforeUsePoint());
        Integer originalTotalPrice = findPurchase.getTotalPrice();
        Integer totalPrice = originalTotalPrice - post.getPoint();

        findPurchase.setTotalPrice(totalPrice);
        findPurchase.setUsedPoints(post.getPoint());

        purchaseDbService.savePurchase(findPurchase);
    }

    public void changePurchaseStatus(Long purchaseId, OAuth2User oauth) {
        Purchase findPurchase = purchaseDbService.ifExistsReturnPurchase(purchaseId);

        // 관리자 여부 확인 코드 필요
        User findUser = userDbService.findUserByEmail(oauth);

        if (findPurchase.getPurchaseStatus().equals(PurchaseStatus.PAY_SUCCESS)) {
            findPurchase.setStatus(PurchaseStatus.DELIVERY_IN_PROGRESS);
        } else if (findPurchase.getPurchaseStatus().equals(PurchaseStatus.DELIVERY_IN_PROGRESS)) {
            findPurchase.setStatus(PurchaseStatus.DURING_DELIVERY);
        } else if (findPurchase.getPurchaseStatus().equals(PurchaseStatus.DURING_DELIVERY)) {
            findPurchase.setStatus(PurchaseStatus.DELIVERY_SUCCESS);
        } else throw new BusinessLogicException(ExceptionCode.INVALID_ACCESS);

        purchaseDbService.savePurchase(findPurchase);
    }

    public Page<GetPurchaseOnMyPage> getPurchaseOnMyPage(OAuth2User oauth, Pageable pageable) {
        User findUser = userDbService.findUserByEmail(oauth);
        return purchaseDbService.getPurchaseOnMyPage(findUser.getUserId(), pageable);
    }

    private String createPurchaseNumber(LocalDateTime createdAt) {
        String createDay = createdAt.toString();
        String createDayStr = createDay.replaceAll("[^0-9]", "");

        return createDayStr.substring(0, 20);
    }

    private void createAndSaveProductPurchase(List<Long> idList, Purchase purchase) {
        ProductPurchase productPurchase;
        Product findProduct;

        // 수정 필요
        int i = 0;
        while (i < idList.size()) {
            findProduct =
                    productDbService.ifExistsReturnProduct(idList.get(i));

            productPurchase = ProductPurchase.builder()
                    .product(findProduct)
                    .purchase(purchase)
                    .build();

            purchase.addProductPurchase(productPurchase);

            productPurchase.addPurchase(purchase);
            productPurchase.addProduct(findProduct);

            i++;
        }
    }

    private Integer calculateTotalPrice(List<Long> productIdList, Long basketId) {
        Integer totalPrice = 0;

        for (int i = 0; i < productIdList.size(); i++) {
            totalPrice = totalPrice + basketDbService.getProductPriceInBasket(productIdList.get(i), basketId);
        }

        return totalPrice;
    }
}
