package com.sidenow.freshgreenish.domain.payment.service;

import com.sidenow.freshgreenish.domain.basket.dto.DeleteBasket;
import com.sidenow.freshgreenish.domain.basket.entity.Basket;
import com.sidenow.freshgreenish.domain.basket.service.BasketDbService;
import com.sidenow.freshgreenish.domain.basket.service.BasketService;
import com.sidenow.freshgreenish.domain.payment.dto.Message;
import com.sidenow.freshgreenish.domain.payment.entity.PaymentInfo;
import com.sidenow.freshgreenish.domain.payment.kakao.*;
import com.sidenow.freshgreenish.domain.payment.toss.*;
import com.sidenow.freshgreenish.domain.product.entity.Product;
import com.sidenow.freshgreenish.domain.product.service.ProductDbService;
import com.sidenow.freshgreenish.domain.purchase.entity.Purchase;
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
    private final BasketService basketService;
    private final UserDbService userDbService;
    private final FeignService feignService;

    private String requestUrl = "http://localhost:8080";

    @Transactional
    public Message getTossPayUrl(Long purchaseId, int methodId) {
        Purchase findPurchase = purchaseDbService.ifExistsReturnPurchase(purchaseId);

        if (!findPurchase.getPurchaseStatus().equals(PAY_IN_PROGRESS)) {
            throw new BusinessLogicException(INVALID_ACCESS);
        }

        PaymentInfo paymentInfo = PaymentInfo.builder()
                .purchase(findPurchase)
                .build();

        PaymentInfo findPaymentInfo = paymentDbService.savePayment(paymentInfo);

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
                        findPurchase.getPurchaseNumber(), createOrderName(findPurchase.getCreatedAt()), method);

        TossPayReadyInfo tossPayReadyInfo = feignService.getTossPayReadyInfo(headers, body);

        findPaymentInfo.setTossPaymentInfo(body);
        findPaymentInfo.setPaymentKey(tossPayReadyInfo.getPaymentKey());
        paymentDbService.savePayment(findPaymentInfo);

        return Message.builder()
                .data(tossPayReadyInfo.getCheckout().getUrl())
                .message(TOSS_PAY_URI_MSG)
                .build();
    }

    @Transactional
    public Message getKaKaoPayUrl(Long purchaseId) {
        Purchase findPurchase = purchaseDbService.ifExistsReturnPurchase(purchaseId);

        if (!findPurchase.getPurchaseStatus().equals(PAY_IN_PROGRESS)) {
            throw new BusinessLogicException(INVALID_ACCESS);
        }

        PaymentInfo paymentInfo = PaymentInfo.builder()
                .purchase(findPurchase)
                .build();

        PaymentInfo findPaymentInfo = paymentDbService.savePayment(paymentInfo);

        KakaoPayHeader headers = feignService.setKakaoHeaders();
        ReadyToKakaoPayInfo params = feignService.setReadyParams(
                requestUrl, purchaseId, findPurchase.getTotalPrice(), findPurchase.getUserId(),
                createOrderName(findPurchase.getCreatedAt()), findPurchase.getTotalCount());

        KakaoPayReadyInfo payReadyInfo = feignService.getPayReadyInfo(headers, params);

        findPaymentInfo.setKakaoPaymentInfo(params, payReadyInfo.getTid());
        paymentDbService.savePayment(findPaymentInfo);

        return Message.builder()
                .data(payReadyInfo.getNextRedirectPcUrl())
                .message(KAKAO_PAY_URI_MSG)
                .build();
    }

    @Transactional
    public Message getSuccessTossPaymentInfo(Long purchaseId) {
        Purchase findPurchase = purchaseDbService.ifExistsReturnPurchase(purchaseId);
        PaymentInfo findPaymentInfo = paymentDbService.ifExistsReturnPaymentInfo(purchaseId);

        TossPayHeader headers = feignService.setTossHeaders();
        RequestForTossPayInfo body = feignService.setRequestBody(
                findPaymentInfo.getPaymentKey(), findPaymentInfo.getAmount(), findPaymentInfo.getOrderId());

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
        purchaseDbService.savePurchase(findPurchase);

        List<Product> findProducts = purchaseDbService.getProductIdList(purchaseId, findPurchase.getUserId());
        changePurchaseCount(findProducts);

        Basket findBasket = basketDbService.ifExistsReturnBasketByUserId(findPurchase.getUserId());
        List<Long> deleteProductIdList = basketDbService.getProductIdInBasket(findBasket.getBasketId());

        if (deleteProductIdList.size() != 0) {
            DeleteBasket deleteBasket = DeleteBasket.builder()
                    .deleteProductId(deleteProductIdList)
                    .build();

            basketService.deleteProductInBasket(findPurchase.getUserId(), deleteBasket);
        }

        User findUser = userDbService.ifExistsReturnUser(findPurchase.getUserId());
        findUser.setSaved_money(findUser.getSaved_money() - findPurchase.getUsedPoints());

        userDbService.saveUser(findUser);

        return Message.builder()
                .data(tossPaySuccessInfo)
                .message(INFO_URI_MSG)
                .build();
    }

    @Transactional
    public Message getSuccessKakaoPaymentInfo(Long purchaseId, String pgToken) {
        Purchase findPurchase = purchaseDbService.ifExistsReturnPurchase(purchaseId);
        PaymentInfo findPaymentInfo = paymentDbService.ifExistsReturnPaymentInfo(purchaseId);

        KakaoPayHeader headers = feignService.setKakaoHeaders();
        RequestForKakaoPayInfo params = feignService.setRequestParams(pgToken, findPaymentInfo);

        KakaoPaySuccessInfo kakaoPaySuccessInfo = feignService.getSuccessKakaoResponse(headers, params);

        kakaoPaySuccessInfo.setOrderStatus(ORDER_APPROVED);
        findPurchase.setPaymentMethod("카카오페이");
        findPurchase.setStatus(PAY_SUCCESS);
        purchaseDbService.savePurchase(findPurchase);

        List<Product> findProducts = purchaseDbService.getProductIdList(purchaseId, findPurchase.getUserId());
        changePurchaseCount(findProducts);

        Basket findBasket = basketDbService.ifExistsReturnBasketByUserId(findPurchase.getUserId());
        List<Long> deleteProductIdList = basketDbService.getProductIdInBasket(findBasket.getBasketId());

        if (deleteProductIdList.size() != 0) {
            DeleteBasket deleteBasket = DeleteBasket.builder()
                    .deleteProductId(deleteProductIdList)
                    .build();

            basketService.deleteProductInBasket(findPurchase.getUserId(), deleteBasket);
        }

        return Message.builder()
                .data(kakaoPaySuccessInfo)
                .message(INFO_URI_MSG)
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

    private String createOrderName(LocalDateTime createdAt) {
        String created = createdAt.toString();
        return created.substring(0, 10);
    }
}
