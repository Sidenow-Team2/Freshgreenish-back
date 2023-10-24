package com.sidenow.freshgreenish.domain.payment.service;

import com.sidenow.freshgreenish.domain.address.entity.Address;
import com.sidenow.freshgreenish.domain.address.service.AddressDbService;
import com.sidenow.freshgreenish.domain.basket.dto.DeleteBasket;
import com.sidenow.freshgreenish.domain.basket.entity.Basket;
import com.sidenow.freshgreenish.domain.basket.service.BasketDbService;
import com.sidenow.freshgreenish.domain.basket.service.BasketService;
import com.sidenow.freshgreenish.domain.delivery.entity.Delivery;
import com.sidenow.freshgreenish.domain.delivery.service.DeliveryDbService;
import com.sidenow.freshgreenish.domain.payment.dto.Message;
import com.sidenow.freshgreenish.domain.payment.entity.PaymentInfo;
import com.sidenow.freshgreenish.domain.payment.kakao.*;
import com.sidenow.freshgreenish.domain.payment.toss.*;
import com.sidenow.freshgreenish.domain.product.entity.Product;
import com.sidenow.freshgreenish.domain.product.service.ProductDbService;
import com.sidenow.freshgreenish.domain.purchase.entity.Purchase;
import com.sidenow.freshgreenish.domain.purchase.entity.SubscriptionStatus;
import com.sidenow.freshgreenish.domain.purchase.service.PurchaseDbService;
import com.sidenow.freshgreenish.domain.user.entity.User;
import com.sidenow.freshgreenish.domain.user.service.UserDbService;
import com.sidenow.freshgreenish.global.exception.BusinessLogicException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.sidenow.freshgreenish.domain.payment.util.PayConstants.*;
import static com.sidenow.freshgreenish.domain.purchase.entity.PurchaseStatus.*;
import static com.sidenow.freshgreenish.domain.purchase.util.PurchaseConstants.*;
import static com.sidenow.freshgreenish.global.exception.ExceptionCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PurchaseDbService purchaseDbService;
    private final PaymentDbService paymentDbService;
    private final ProductDbService productDbService;
    private final BasketDbService basketDbService;
    private final DeliveryDbService deliveryDbService;
    private final UserDbService userDbService;
    private final AddressDbService addressDbService;
    private final BasketService basketService;
    private final FeignService feignService;

    private String requestUrl = "http://localhost:8080";

    @Transactional
    public Message getTossPayUrl(Long purchaseId, int methodId) {
        Purchase findPurchase = purchaseDbService.ifExistsReturnPurchase(purchaseId);

        if (!findPurchase.getPurchaseStatus().equals(PAY_IN_PROGRESS)) {
            throw new BusinessLogicException(INVALID_ACCESS);
        }

        PaymentInfo findPayment = paymentDbService.ifExistsReturnPaymentInfo(findPurchase.getPurchaseId());

        String method = "";
        switch (methodId) {
            case 2:
                method = "휴대폰";
                break;
            case 3:
                method = "계좌이체";
                break;
            default:
                method = "카드";
        }

        TossPayHeader headers = feignService.setTossHeaders();
        ReadyToTossPayInfo body =
                feignService.setReadyTossParams(requestUrl, purchaseId, findPurchase.getTotalPrice(),
                        findPurchase.getPurchaseNumber(), findPayment.getOrderName(), method);

        TossPayReadyInfo tossPayReadyInfo = feignService.getTossPayReadyInfo(headers, body);

        findPayment.setTossPaymentInfo(body);
        findPayment.setPaymentKey(tossPayReadyInfo.getPaymentKey());
        paymentDbService.savePayment(findPayment);

        return Message.builder()
                .data(tossPayReadyInfo.getCheckout().getUrl())
                .message(TOSS_PAY_URI_MSG)
                .build();
    }

    @Transactional
    public Message getKaKaoPayUrl(Long purchaseId) {
        Purchase findPurchase = purchaseDbService.ifExistsReturnPurchase(purchaseId);

        if (findPurchase.getIsRegularDelivery().equals(false)
                && !findPurchase.getPurchaseStatus().equals(PAY_IN_PROGRESS)) {
            throw new BusinessLogicException(INVALID_ACCESS);
        } else if (findPurchase.getIsRegularDelivery().equals(true)
                && !findPurchase.getSubscriptionStatus().equals(SubscriptionStatus.DURING_SUBSCRIPTION)) {
            throw new BusinessLogicException(INVALID_ACCESS);
        }

        PaymentInfo findPayment = paymentDbService.ifExistsReturnPaymentInfo(findPurchase.getPurchaseId());

        KakaoPayHeader headers = feignService.setKakaoHeaders();
        ReadyToKakaoPayInfo params = feignService.setReadyParams(
                requestUrl, purchaseId, findPurchase.getTotalPrice(), findPurchase.getUserId(),
                findPayment.getOrderName(), findPurchase.getTotalCount(),
                findPurchase.getIsRegularDelivery());

        KakaoPayReadyInfo payReadyInfo = feignService.getPayReadyInfo(headers, params);

        findPayment.setKakaoPaymentInfo(params, payReadyInfo.getTid());
        paymentDbService.savePayment(findPayment);

        return Message.builder()
                .data(payReadyInfo.getNextRedirectPcUrl())
                .message(KAKAO_PAY_URI_MSG)
                .build();
    }

    @Transactional
    public Message getKakaoSubUrl(Long purchaseId) {
        Purchase findPurchase = purchaseDbService.ifExistsReturnPurchase(purchaseId);
        PaymentInfo findPayment = paymentDbService.ifExistsReturnPaymentInfo(purchaseId);
        String productTitle = productDbService.getProductTitle(purchaseId);

        if (!findPurchase.getPurchaseStatus().equals(SubscriptionStatus.DURING_SUBSCRIPTION)) {
            throw new BusinessLogicException(INVALID_ACCESS);
        }

        KakaoPayHeader headers = feignService.setKakaoHeaders();
        ReadyToKakaoSubInfo params = feignService.setReadySubParams(findPayment.getSid(), purchaseId,
                findPurchase.getTotalPrice(), findPurchase.getUserId(), findPayment.getOrderName(),
                productTitle, findPurchase.getTotalCount());

        KakaoSubReadyInfo payReadyInfo = feignService.getSubReadyInfo(headers, params);

        return Message.builder()
                .data(payReadyInfo)
                .message(SUCCESS_KAKAO_REGULAR_PAY)
                .build();
    }

    @Transactional
    public Message getSuccessTossPaymentInfo(Long purchaseId) {
        Purchase findPurchase = purchaseDbService.ifExistsReturnPurchase(purchaseId);
        PaymentInfo findPayment = paymentDbService.ifExistsReturnPaymentInfo(purchaseId);

        TossPayHeader headers = feignService.setTossHeaders();
        RequestForTossPayInfo body = feignService.setRequestBody(
                findPayment.getPaymentKey(), findPayment.getAmount(), findPayment.getOrderId());

        TossPaySuccessInfo tossPaySuccessInfo = feignService.getSuccessTossResponse(headers, body);

        switch (tossPaySuccessInfo.getMethod()) {
            case "휴대폰":
                findPurchase.setPaymentMethod("토스페이 휴대폰");
                break;
            case "계좌이체":
                findPurchase.setPaymentMethod("토스페이 계좌이체");
                break;
            default:
                findPurchase.setPaymentMethod("토스페이 카드");
        }

        tossPaySuccessInfo.setOrderStatus(ORDER_APPROVED);
        findPurchase.setStatus(PAY_SUCCESS);
        findPayment.setPaymentDate(LocalDateTime.now());

        Purchase newPurchase = purchaseDbService.savePurchase(findPurchase);
        PaymentInfo newPayment = paymentDbService.savePayment(findPayment);

        newPayment.setDeliveryDate(newPayment.getPaymentDate().plusDays(1));
        paymentDbService.savePayment(newPayment);

        List<Product> findProducts = purchaseDbService.getProductIdList(purchaseId, newPurchase.getUserId());
        changePurchaseCount(findProducts);

        Basket findBasket = basketDbService.ifExistsReturnBasketByUserId(newPurchase.getUserId());
        List<Long> deleteProductIdList = basketDbService.getProductIdInBasket(findBasket.getBasketId());

        if (deleteProductIdList.size() != 0) {
            DeleteBasket deleteBasket = DeleteBasket.builder()
                    .deleteProductId(deleteProductIdList)
                    .build();

            basketService.deleteProductInBasket(newPurchase.getUserId(), deleteBasket);
        }

        User findUser = userDbService.ifExistsReturnUser(newPurchase.getUserId());
        findUser.setSaved_money(findUser.getSaved_money() - newPurchase.getUsedPoints());

        userDbService.saveUser(findUser);

        return Message.builder()
                .data(tossPaySuccessInfo)
                .message(INFO_URI_MSG)
                .build();
    }

    @Transactional
    public Message getSuccessKakaoPaymentInfo(Long purchaseId, String pgToken) {
        Purchase findPurchase = purchaseDbService.ifExistsReturnPurchase(purchaseId);
        PaymentInfo findPayment = paymentDbService.ifExistsReturnPaymentInfo(purchaseId);

        KakaoPayHeader headers = feignService.setKakaoHeaders();
        RequestForKakaoPayInfo params = feignService.setRequestParams(pgToken, findPayment);

        KakaoPaySuccessInfo kakaoPaySuccessInfo = feignService.getSuccessKakaoResponse(headers, params);

        kakaoPaySuccessInfo.setOrderStatus(ORDER_APPROVED);
        findPurchase.setPaymentMethod("카카오페이");
        findPurchase.setStatus(PAY_SUCCESS);
        findPayment.setSid(kakaoPaySuccessInfo.getSid());
        findPayment.setPaymentDate(LocalDateTime.now());

        Purchase newPurchase = purchaseDbService.savePurchase(findPurchase);
        PaymentInfo newPayment = paymentDbService.savePayment(findPayment);

        newPayment.setDeliveryDate(newPayment.getPaymentDate().plusDays(1));
        paymentDbService.savePayment(newPayment);

        List<Product> findProducts = purchaseDbService.getProductIdList(purchaseId, newPurchase.getUserId());
        changePurchaseCount(findProducts);

        Basket findBasket = basketDbService.ifExistsReturnBasketByUserId(newPurchase.getUserId());
        List<Long> deleteProductIdList = basketDbService.getProductIdInBasket(findBasket.getBasketId());

        if (deleteProductIdList.size() != 0) {
            DeleteBasket deleteBasket = DeleteBasket.builder()
                    .deleteProductId(deleteProductIdList)
                    .build();

            basketService.deleteProductInBasket(newPurchase.getUserId(), deleteBasket);
        }

        if (findPurchase.getIsRegularDelivery().equals(true)) {
            Address findAddress = addressDbService.ifExistsReturnAddress(newPurchase.getAddressId());
            Delivery findDelivery =
                    deliveryDbService.findOrCreateDelivery(newPurchase.getPurchaseId(), newPurchase.getUserId(),
                            newPayment.getPaymentDate(), newPurchase.getPaymentMethod(), findAddress);

            LocalDateTime date = newPayment.getPaymentDate().plusMonths(1);
            findDelivery.setThisMonthPaymentDate(date);
            findDelivery.setNextPaymentDate(date.plusMonths(1)); // 수정 필요
            findDelivery.setDeliveryDate(date.plusDays(1)); // 수정 필요

            deliveryDbService.saveDelivery(findDelivery);

            newPurchase.setSubStatus(SubscriptionStatus.DURING_SUBSCRIPTION);
            purchaseDbService.savePurchase(newPurchase);

            User findUser = userDbService.ifExistsReturnUser(findPurchase.getUserId());
            findUser.setIsJoinRegular(true);
            userDbService.saveUser(findUser);
        }

        return Message.builder()
                .data(kakaoPaySuccessInfo)
                .message(INFO_URI_MSG)
                .build();
    }

    @Transactional
    public Message getSuccessKakaoRegularPaymentInfo(Long purchaseId, String pgToken) {
        Purchase findPurchase = purchaseDbService.ifExistsReturnPurchase(purchaseId);
        PaymentInfo findPayment = paymentDbService.ifExistsReturnPaymentInfo(purchaseId);

        KakaoPayHeader headers = feignService.setKakaoHeaders();
        RequestForKakaoPayInfo params = feignService.setRequestParams(pgToken, findPayment);

        KakaoPaySuccessInfo kakaoPaySuccessInfo = feignService.getSuccessKakaoResponse(headers, params);

        kakaoPaySuccessInfo.setOrderStatus(ORDER_APPROVED);
        findPurchase.setPaymentMethod("카카오페이");
        findPurchase.setStatus(PAY_SUCCESS);
        findPayment.setSid(kakaoPaySuccessInfo.getSid());
        findPayment.setPaymentDate(LocalDateTime.now());

        Purchase newPurchase = purchaseDbService.savePurchase(findPurchase);
        PaymentInfo newPayment = paymentDbService.savePayment(findPayment);

        List<Product> findProducts = purchaseDbService.getProductIdList(purchaseId, newPurchase.getUserId());
        changePurchaseCount(findProducts);

        Basket findBasket = basketDbService.ifExistsReturnBasketByUserId(newPurchase.getUserId());

        List<Long> deleteProductIdList;

        if (newPurchase.getIsRegularDelivery().equals(true)) {
            deleteProductIdList = basketDbService.getProductIdInRegular(findBasket.getBasketId());
        } else deleteProductIdList = basketDbService.getProductIdInBasket(findBasket.getBasketId());


        if (deleteProductIdList.size() != 0) {
            DeleteBasket deleteBasket = DeleteBasket.builder()
                    .deleteProductId(deleteProductIdList)
                    .build();

            basketService.deleteProductInBasket(newPurchase.getUserId(), deleteBasket);
        }

        if (findPurchase.getIsRegularDelivery().equals(true)) {
            Address findAddress = addressDbService.ifExistsReturnAddress(newPurchase.getAddressId());
            Delivery findDelivery =
                    deliveryDbService.findOrCreateDelivery(newPurchase.getPurchaseId(), newPurchase.getUserId(),
                            newPayment.getPaymentDate(), newPurchase.getPaymentMethod(), findAddress);

            LocalDateTime date = newPayment.getPaymentDate().plusMonths(1);
            findDelivery.setThisMonthPaymentDate(date);
            findDelivery.setNextPaymentDate(date.plusMonths(1)); //수정 필요

            deliveryDbService.saveDelivery(findDelivery);
        }

        User findUser = userDbService.ifExistsReturnUser(findPurchase.getUserId());
        findUser.setIsJoinRegular(true);
        userDbService.saveUser(findUser);

        return Message.builder()
                .data(kakaoPaySuccessInfo)
                .message(INFO_URI_MSG)
                .build();
    }

    public Message cancleKakaoRegularPayment(Long purchaseId) {
        Purchase findPurchase = purchaseDbService.ifExistsReturnPurchase(purchaseId);
        PaymentInfo findPayment = paymentDbService.ifExistsReturnPaymentInfo(purchaseId);

        KakaoPayHeader headers = feignService.setKakaoHeaders();
        RequestForKakaoRegularPayCancel params =
                feignService.setRequestCancelParams(findPayment.getSid(), findPayment.getCid());

        KakaoPayRegularCancelInfo cancelInfo =
                feignService.getCancelKakaoPayRegularResponse(headers, params);

        cancelInfo.setOrderStatus(REFUND_APPROVED);

        findPurchase.setSubStatus(SubscriptionStatus.END_OF_SUBSCRIPTION);
        purchaseDbService.savePurchase(findPurchase);

        return Message.builder()
                .data(cancelInfo)
                .message(CANCELED_PAY_MESSAGE)
                .build();
    }

    private void changePurchaseCount(List<Product> productList) {
        int i = 0;
        while (i < productList.size()) {
            productList.get(i).addPurchaseCount();
            productDbService.saveProduct(productList.get(i));
            i++;
        }
    }

    public Message<?> getFailedPayMessage() {
        return Message.builder()
                .message(FAILED_INFO_MESSAGE + "<br>" + INVALID_PARAMS)
                .build();
    }
}
