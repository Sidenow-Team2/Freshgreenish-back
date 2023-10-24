package com.sidenow.freshgreenish.domain.basket.controller;

import com.sidenow.freshgreenish.domain.basket.dto.*;
import com.sidenow.freshgreenish.domain.basket.service.BasketService;
import com.sidenow.freshgreenish.domain.user.service.UserDbService;
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
@RequestMapping("/basket")
public class BasketController {
    private final BasketService basketService;
    private final UserDbService userDbService;

    @PostMapping("/product/{productId}/{type}")
    public ResponseEntity addProductInBasket(@PathVariable("productId") Long productId,
                                             @PathVariable("type") String type,
                                             @RequestBody @Valid PostBasket post,
                                             @AuthenticationPrincipal OAuth2User oauth) {
        basketService.addProductInBasket(productId, oauth, post, type);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/product/{productId}/{type}")
    public ResponseEntity changeProductCountInBasket(@PathVariable("productId") Long productId,
                                                     @PathVariable("type") String type,
                                                     @RequestBody @Valid PostBasket post,
                                                     @AuthenticationPrincipal OAuth2User oauth) {
        basketService.changeProductCountInBasket(oauth, productId, post, type);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{type}")
    public ResponseEntity getBasketList(Pageable pageable,
                                        @PathVariable("type") String type,
                                        @AuthenticationPrincipal OAuth2User oauth) {
        Page<GetBasket> basket = basketService.getBasketList(oauth, pageable, type);
        TotalPriceInfo info = basketService.getTotalPriceInfo(oauth, type);
        return ResponseEntity.ok().body(new WrapBasket<>(basket, info));
    }


    @DeleteMapping("/{type}")
    public ResponseEntity deleteProductInBasket(@RequestBody @Valid DeleteBasket delete,
                                                @PathVariable("type") String type,
                                                @AuthenticationPrincipal OAuth2User oauth) {
        basketService.deleteBasket(oauth, delete, type);
        return ResponseEntity.ok().build();
    }
}
