package com.sidenow.freshgreenish.domain.purchase.service;

import com.sidenow.freshgreenish.domain.basket.entity.Basket;
import com.sidenow.freshgreenish.domain.basket.service.BasketDbService;
import com.sidenow.freshgreenish.domain.payment.entity.PaymentInfo;
import com.sidenow.freshgreenish.domain.payment.service.PaymentDbService;
import com.sidenow.freshgreenish.domain.product.entity.Product;
import com.sidenow.freshgreenish.domain.product.service.ProductDbService;
import com.sidenow.freshgreenish.domain.purchase.dto.*;
import com.sidenow.freshgreenish.domain.purchase.entity.ProductPurchase;
import com.sidenow.freshgreenish.domain.purchase.entity.Purchase;
import com.sidenow.freshgreenish.domain.purchase.entity.PurchaseStatus;
import com.sidenow.freshgreenish.domain.purchase.entity.SubscriptionStatus;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class PurchaseService {
    private final PurchaseDbService purchaseDbService;
    private final PaymentDbService paymentDbService;
    private final ProductDbService productDbService;
    private final BasketDbService basketDbService;
    private final UserDbService userDbService;

    public void createSinglePurchase(Long productId, OAuth2User oauth, PostPurchase post) {
        Long userId = userDbService.findUserIdByOauth(oauth);
        Basket findBasket = basketDbService.ifExistsReturnBasket(userId);
        basketDbService.saveBasket(findBasket);

        Purchase purchase = Purchase.builder()
                .productId(productId)
                .userId(userId)
                .addressId(0L)
                .usedPoints(0)
                .count(post.getCount())
                .totalCount(post.getCount())
                .isRegularDelivery(false)
                .totalPrice(post.getTotalPrice())
                .totalPriceBeforeUsePoint(post.getTotalPrice())
                .build();

        purchase.setStatus(PurchaseStatus.PAY_IN_PROGRESS);
        purchase.setSubStatus(SubscriptionStatus.NOT_USE_SUBSCRIPTION);

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

        Purchase newPurchase = purchaseDbService.saveAndReturnPurchase(findPurchase);

        PaymentInfo paymentInfo = PaymentInfo.builder()
                .purchase(newPurchase)
                .orderName(createOrderName(purchase.getCreatedAt()))
                .build();

        paymentDbService.savePayment(paymentInfo);
    }

    public void createSelectPurchase(OAuth2User oauth, PostSelectPurchase post) {
        Long userId = userDbService.findUserIdByOauth(oauth);
        Basket findBasket = basketDbService.ifExistsReturnBasketByUserId(userId);

        Purchase purchase = Purchase.builder()
                .productId(post.getProductIdList().get(0))
                .basketId(findBasket.getBasketId())
                .userId(userId)
                .addressId(0L)
                .usedPoints(0)
                .count(1)
                .totalCount(1)
                .isRegularDelivery(false)
                .build();

        purchase.setStatus(PurchaseStatus.PAY_IN_PROGRESS);
        purchase.setSubStatus(SubscriptionStatus.NOT_USE_SUBSCRIPTION);

        Purchase findPurchase = purchaseDbService.saveAndReturnPurchase(purchase);

        findPurchase.setPurchaseNumber(createPurchaseNumber(findPurchase.getCreatedAt()));
        findPurchase.setTotalPrice(post.getTotalPrice());
        findPurchase.setTotalPriceBeforeUsePoint(post.getTotalPrice());

        createAndSaveProductPurchase(post.getProductIdList(), findPurchase);

        Purchase newPurchase = purchaseDbService.saveAndReturnPurchase(findPurchase);

        PaymentInfo paymentInfo = PaymentInfo.builder()
                .purchase(newPurchase)
                .orderName(createOrderName(purchase.getCreatedAt()))
                .build();

        paymentDbService.savePayment(paymentInfo);
    }

    public void createRegularPurchaseSelect(OAuth2User oauth, PostSelectPurchase post) {
        Long userId = userDbService.findUserIdByOauth(oauth);
        Basket findBasket = basketDbService.ifExistsReturnBasketByUserId(userId);

        Purchase purchase = Purchase.builder()
                .productId(post.getProductIdList().get(0))
                .basketId(findBasket.getBasketId())
                .userId(userId)
                .addressId(0L)
                .usedPoints(0)
                .count(1)
                .totalCount(1)
                .isRegularDelivery(true)
                .build();

        purchase.setStatus(PurchaseStatus.PAY_IN_PROGRESS);
        purchase.setSubStatus(SubscriptionStatus.DURING_SUBSCRIPTION);

        Purchase findPurchase = purchaseDbService.saveAndReturnPurchase(purchase);

        findPurchase.setPurchaseNumber(createPurchaseNumber(findPurchase.getCreatedAt()));
        findPurchase.setTotalPrice(post.getTotalPrice());
        findPurchase.setTotalPriceBeforeUsePoint(post.getTotalPrice());

        createAndSaveProductPurchase(post.getProductIdList(), findPurchase);

        Purchase newPurchase = purchaseDbService.saveAndReturnPurchase(findPurchase);

        PaymentInfo paymentInfo = PaymentInfo.builder()
                .purchase(newPurchase)
                .orderName(createOrderName(purchase.getCreatedAt()))
                .build();

        paymentDbService.savePayment(paymentInfo);
    }

    public void createAllPurchase(OAuth2User oauth, PostAllPurchase post) {
        Long userId = userDbService.findUserIdByOauth(oauth);
        Basket findBasket = basketDbService.ifExistsReturnBasketByUserId(userId);

        Purchase purchase = Purchase.builder()
                .productId(findBasket.getProductBasket().get(0).getProduct().getProductId())
                .basketId(findBasket.getBasketId())
                .userId(userId)
                .addressId(0L)
                .usedPoints(0)
                .count(1)
                .isRegularDelivery(false)
                .totalPrice(post.getTotalPrice())
                .totalPriceBeforeUsePoint(post.getTotalPrice())
                .build();

        purchase.setStatus(PurchaseStatus.PAY_IN_PROGRESS);
        purchase.setSubStatus(SubscriptionStatus.NOT_USE_SUBSCRIPTION);

        Purchase findPurchase = purchaseDbService.saveAndReturnPurchase(purchase);
        findPurchase.setPurchaseNumber(createPurchaseNumber(findPurchase.getCreatedAt()));

        List<Long> productIdList = basketDbService.getProductIdInBasket(findBasket.getBasketId());
        createAndSaveProductPurchase(productIdList, findPurchase);

        Purchase newPurchase = purchaseDbService.saveAndReturnPurchase(findPurchase);

        PaymentInfo paymentInfo = PaymentInfo.builder()
                .purchase(newPurchase)
                .orderName(createOrderName(purchase.getCreatedAt()))
                .build();

        paymentDbService.savePayment(paymentInfo);
    }

    public void createRegularPurchaseAll(OAuth2User oauth, PostAllPurchase post) {
        Long userId = userDbService.findUserIdByOauth(oauth);
        Basket findBasket = basketDbService.ifExistsReturnBasketByUserId(userId);

        Purchase purchase = Purchase.builder()
                .productId(findBasket.getProductBasket().get(0).getProduct().getProductId())
                .basketId(findBasket.getBasketId())
                .userId(userId)
                .addressId(0L)
                .usedPoints(0)
                .count(1)
                .isRegularDelivery(true)
                .totalPrice(post.getTotalPrice())
                .totalPriceBeforeUsePoint(post.getTotalPrice())
                .build();

        purchase.setStatus(PurchaseStatus.PAY_IN_PROGRESS);
        purchase.setSubStatus(SubscriptionStatus.DURING_SUBSCRIPTION);

        Purchase findPurchase = purchaseDbService.saveAndReturnPurchase(purchase);
        findPurchase.setPurchaseNumber(createPurchaseNumber(findPurchase.getCreatedAt()));

        List<Long> productIdList = basketDbService.getProductIdInRegular(findBasket.getBasketId());
        createAndSaveProductPurchase(productIdList, findPurchase);

        Purchase newPurchase = purchaseDbService.saveAndReturnPurchase(findPurchase);

        PaymentInfo paymentInfo = PaymentInfo.builder()
                .purchase(newPurchase)
                .orderName(createOrderName(purchase.getCreatedAt()))
                .build();

        paymentDbService.savePayment(paymentInfo);
    }

    public GetPurchaseInfo getPurchaseInfo(Long purchaseId, OAuth2User oauth) {
        Long userId = userDbService.findUserIdByOauth(oauth);
        Purchase findPurchase = purchaseDbService.ifExistsReturnPurchase(purchaseId);

        return GetPurchaseInfo.builder()
                .orderLists(purchaseDbService.getOrderListByPurchaseIdAndUserId(findPurchase.getPurchaseId(), userId))
                .addressInfo(purchaseDbService.getAddressInfo(findPurchase.getAddressId()))
                .priceInfo(purchaseDbService.getPriceInfo(purchaseId, userId))
                .build();
    }

    public GetPurchaseDetail getPurchaseDetail(Long purchaseId, OAuth2User oauth) {
        Long userId = userDbService.findUserIdByOauth(oauth);
        Purchase findPurchase = purchaseDbService.ifExistsReturnPurchase(purchaseId);

        return GetPurchaseDetail.builder()
                .orderLists(purchaseDbService.getOrderListByPurchaseIdAndUserId(purchaseId, userId))
                .addressInfo(purchaseDbService.getAddressInfo(findPurchase.getAddressId()))
                .priceInfo(purchaseDbService.getPriceInfo(purchaseId, userId))
                .build();
    }

    public GetPurchaseInfo getBasketPurchaseInfo(Long purchaseId, OAuth2User oauth) {
        Long userId = userDbService.findUserIdByOauth(oauth);
        Basket findBasket = basketDbService.ifExistsReturnBasketByUserId(userId);
        Purchase findPurchase = purchaseDbService.ifExistsReturnPurchase(purchaseId);

        return GetPurchaseInfo.builder()
                .orderLists(purchaseDbService.getBasketOrderList(findBasket.getBasketId(), findPurchase.getPurchaseId(), userId))
                .addressInfo(purchaseDbService.getAddressInfo(findPurchase.getAddressId()))
                .priceInfo(purchaseDbService.getPriceInfo(purchaseId, userId))
                .build();
    }

    public GetPurchaseInfo getRegularPurchaseInfo(Long purchaseId, OAuth2User oauth) {
        Long userId = userDbService.findUserIdByOauth(oauth);
        Basket findBasket = basketDbService.ifExistsReturnBasketByUserId(userId);
        Purchase findPurchase = purchaseDbService.ifExistsReturnPurchase(purchaseId);

        return GetPurchaseInfo.builder()
                .orderLists(purchaseDbService.getRegularOrderList(findBasket.getBasketId(), purchaseId, userId))
                .addressInfo(purchaseDbService.getAddressInfo(findPurchase.getAddressId()))
                .priceInfo(purchaseDbService.getPriceInfo(purchaseId, userId))
                .build();
    }

    public void usedPointInPurchase(Long purchaseId, OAuth2User oauth, PostUsePoint post) {
        Purchase findPurchase = purchaseDbService.ifExistsReturnPurchase(purchaseId);
        User findUser = userDbService.findUserByEmail(oauth);

        if (findUser.getSaved_money() < post.getPoint()) {
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
        User manager = userDbService.findUserByEmail(oauth);

        if (findPurchase.getPurchaseStatus().equals(PurchaseStatus.PAY_SUCCESS)) {
            findPurchase.setStatus(PurchaseStatus.DELIVERY_IN_PROGRESS);
        } else if (findPurchase.getPurchaseStatus().equals(PurchaseStatus.DELIVERY_IN_PROGRESS)) {
            findPurchase.setStatus(PurchaseStatus.DURING_DELIVERY);
        } else if (findPurchase.getPurchaseStatus().equals(PurchaseStatus.DURING_DELIVERY)) {
            findPurchase.setStatus(PurchaseStatus.DELIVERY_SUCCESS);

            User buyer = userDbService.ifExistsReturnUser(findPurchase.getUserId());
            Integer originalReserves = buyer.getSaved_money();
            Integer updateReserves = originalReserves + (findPurchase.getTotalPrice() * 1 / 100);

            buyer.setSaved_money(updateReserves);

        } else throw new BusinessLogicException(ExceptionCode.INVALID_ACCESS);

        purchaseDbService.savePurchase(findPurchase);
    }

    public void masterChangePurchaseStatus(Long purchaseId, OAuth2User oauth, Integer statusId) {
        Purchase findPurchase = purchaseDbService.ifExistsReturnPurchase(purchaseId);

        // 관리자 여부 확인 코드 필요
        User findUser = userDbService.findUserByEmail(oauth);

        switch (statusId) {
            case 1:
                findPurchase.setStatus(PurchaseStatus.PAY_IN_PROGRESS);
                break;
            case 2:
                findPurchase.setStatus(PurchaseStatus.PAY_SUCCESS);
                break;
            case 3:
                findPurchase.setStatus(PurchaseStatus.DELIVERY_IN_PROGRESS);
                break;
            case 4:
                findPurchase.setStatus(PurchaseStatus.DURING_DELIVERY);
                break;
            case 5:
                findPurchase.setStatus(PurchaseStatus.DELIVERY_SUCCESS);
                break;
            case 6:
                findPurchase.setStatus(PurchaseStatus.REFUND_PAYMENT);
                break;
            default:
                throw new BusinessLogicException(ExceptionCode.INVALID_ACCESS);
        }

        purchaseDbService.savePurchase(findPurchase);
    }

    public Page<GetPurchaseOnMyPage> getPurchaseOnMyPage(OAuth2User oauth, Pageable pageable) {
        Long userId = userDbService.findUserIdByOauth(oauth);
        return purchaseDbService.getPurchaseOnMyPage(userId, pageable);
    }

    public Page<GetSubscriptionOnMyPage> getSubscriptionOnMyPage(OAuth2User oauth, Pageable pageable) {
        Long userId = userDbService.findUserIdByOauth(oauth);
        return purchaseDbService.getSubscriptionOnMyPage(userId, pageable);
    }

    public CheckSubscription getSubscriptionInfo(OAuth2User oauth) {
        Long userId = userDbService.findUserIdByOauth(oauth);
        if (purchaseDbService.getSubscriptionCount(userId) > 0) {
            return CheckSubscription.builder()
                    .regularPurchaseCount(purchaseDbService.getSubscriptionCount(userId))
                    .isExists(true)
                    .build();
        } else return CheckSubscription.builder()
                        .regularPurchaseCount(purchaseDbService.getSubscriptionCount(userId))
                        .isExists(false)
                        .build();
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

    private Integer calculateTotalRegularPrice(List<Long> productIdList, Long basketId) {
        Integer totalPrice = 0;

        for (int i = 0; i < productIdList.size(); i++) {
            totalPrice = totalPrice + basketDbService.getProductPriceInRegular(productIdList.get(i), basketId);
        }

        return totalPrice;
    }

    private String createOrderName(LocalDateTime createdAt) {
        String created = createdAt.toString();
        return created.substring(0, 10);
    }
}
