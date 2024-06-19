package com.sidenow.freshgreenish.domain.basket.controller;

import com.sidenow.freshgreenish.domain.basket.dto.*;
import com.sidenow.freshgreenish.domain.basket.service.BasketService;
import com.sidenow.freshgreenish.domain.dto.MultiResponseDto;
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

    @PostMapping("/product/{productId}/irregular")
    public ResponseEntity addProductInBasket(@PathVariable("productId") Long productId,
                                             @RequestBody @Valid PostBasket post,
                                             @AuthenticationPrincipal OAuth2User oauth) {
        basketService.addProductInBasket(productId, oauth, post);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/product/{productId}/regular")
    public ResponseEntity addProductInRegularBasket(@PathVariable("productId") Long productId,
                                                    @RequestBody @Valid PostBasket post,
                                                    @AuthenticationPrincipal OAuth2User oauth) {
        basketService.addProductInRegularBasket(productId, oauth, post);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/product/{productId}/add")
    public ResponseEntity addOneProductInBasket(@PathVariable("productId") Long productId,
                                                @AuthenticationPrincipal OAuth2User oauth) {
        basketService.addOneProductInBasket(productId, oauth);
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

    @GetMapping("/irregular")
    public ResponseEntity getBasketList(Pageable pageable,
                                        @AuthenticationPrincipal OAuth2User oauth) {
        Page<GetBasket> basket = basketService.getBasketList(oauth, pageable);
        return ResponseEntity.ok().body(new MultiResponseDto<>(basket));
    }

    @GetMapping("/regular")
    public ResponseEntity getRegularList(Pageable pageable,
                                        @AuthenticationPrincipal OAuth2User oauth) {
        Page<GetBasket> basket = basketService.getRegularList(oauth, pageable);
        return ResponseEntity.ok().body(new MultiResponseDto<>(basket));
    }


    @DeleteMapping("/{type}")
    public ResponseEntity deleteProductInBasket(@RequestBody @Valid DeleteBasket delete,
                                                @PathVariable("type") String type,
                                                @AuthenticationPrincipal OAuth2User oauth) {
        basketService.deleteBasket(oauth, delete, type);
        return ResponseEntity.ok().build();
    }
}
