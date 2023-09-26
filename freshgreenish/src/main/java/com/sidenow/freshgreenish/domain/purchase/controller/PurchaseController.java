package com.sidenow.freshgreenish.domain.purchase.controller;

import com.sidenow.freshgreenish.domain.dto.MultiResponseDto;
import com.sidenow.freshgreenish.domain.purchase.dto.*;
import com.sidenow.freshgreenish.domain.purchase.service.PurchaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PurchaseController {
    private final PurchaseService purchaseService;

    // 상품 페이지 - 단건 구매
    @PostMapping("/purchase/product/{productId}")
    public ResponseEntity createSinglePurchase(@PathVariable("productId") Long productId,
                                               @RequestBody @Valid PostPurchase post,
                                               @AuthenticationPrincipal OAuth2User oauth) {
        purchaseService.createSinglePurchase(productId, oauth, post);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 상세페이지 - 결제 요청 페이지
    @GetMapping("/purchase/{purchaseId}/payment")
    public ResponseEntity getSinglePurchase(@PathVariable("purchaseId") Long purchaseId,
                                            @AuthenticationPrincipal OAuth2User oauth) {
        GetPurchaseInfo purchase = purchaseService.getPurchaseInfo(purchaseId, oauth);
        return ResponseEntity.ok()
                .body(new WrapPurchaseInfo<>(purchase.getOrderLists(), purchase.getAddressInfo(), purchase.getPriceInfo()));
    }

    // 장바구니 - 선택 구매
    @PostMapping("/purchase/basket/select")
    public ResponseEntity createSelectPurchase(@RequestBody @Valid PostSelectPurchase post,
                                               @AuthenticationPrincipal OAuth2User oauth) {
        purchaseService.createSelectPurchase(oauth, post);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 장바구니 - 전체 구매
    @PostMapping("/purchase/basket/all")
    public ResponseEntity createAllPurchase(@AuthenticationPrincipal OAuth2User oauth) {
        purchaseService.createAllPurchase(oauth);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 장바구니 - 결제 요청 페이지
    @GetMapping("/purchase/{purchaseId}/basket/payment")
    public ResponseEntity getBasketPurchase(@PathVariable("purchaseId") Long purchaseId,
                                            @AuthenticationPrincipal OAuth2User oauth) {
        GetPurchaseInfo purchase = purchaseService.getBasketPurchaseInfo(purchaseId, oauth);
        return ResponseEntity.ok()
                .body(new WrapPurchaseInfo<>(purchase.getOrderLists(), purchase.getAddressInfo(), purchase.getPriceInfo()));
    }

    @PatchMapping("/purchase/{purchaseId}")
    public ResponseEntity usedPointInPurchase(@PathVariable("purchaseId") Long purchaseId,
                                              @RequestBody @Valid PostUsePoint post,
                                              @AuthenticationPrincipal OAuth2User oauth) {
        purchaseService.usedPointInPurchase(purchaseId, oauth, post);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/master/purchase/{purchaseId}")
    public ResponseEntity changePurchaseStatus(@PathVariable("purchaseId") Long purchaseId,
                                               @AuthenticationPrincipal OAuth2User oauth) {
        purchaseService.changePurchaseStatus(purchaseId, oauth);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/purchase")
    public ResponseEntity getPurchaseOnMyPage(@AuthenticationPrincipal OAuth2User oauth,
                                              Pageable pageable) {
        Page<GetPurchaseOnMyPage> purchase = purchaseService.getPurchaseOnMyPage(oauth, pageable);
        return ResponseEntity.ok().body(new MultiResponseDto<>(purchase));
    }

    @GetMapping("/purchase/{purchaseId}")
    public ResponseEntity getPurchaseDetail(@PathVariable("purchaseId") Long purchaseId,
                                              @AuthenticationPrincipal OAuth2User oauth) {
        GetPurchaseInfo purchase = purchaseService.getPurchaseInfo(purchaseId, oauth);
        return ResponseEntity.ok()
                .body(new WrapPurchaseInfo<>(purchase.getOrderLists(), purchase.getAddressInfo(), purchase.getPriceInfo()));
    }
}
