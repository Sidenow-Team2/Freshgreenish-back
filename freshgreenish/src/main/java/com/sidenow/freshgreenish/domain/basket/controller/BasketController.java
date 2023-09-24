package com.sidenow.freshgreenish.domain.basket.controller;

import com.sidenow.freshgreenish.domain.basket.dto.*;
import com.sidenow.freshgreenish.domain.basket.service.BasketDbService;
import com.sidenow.freshgreenish.domain.basket.service.BasketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/basket")
public class BasketController {
    private final BasketService basketService;
    private final BasketDbService basketDbService;

    @PostMapping("/product/{productId}")
    public ResponseEntity addProductInBasket(@PathVariable("productId") Long productId,
                                    @RequestBody @Valid PostBasket post) {
        Long userId = 1L;
        basketService.addProductInBasket(productId, userId, post);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/product/{productId}/option/change")
    public ResponseEntity changeProductCountInBasket(@PathVariable("productId") Long productId,
                                                     @RequestBody @Valid PostBasket post) {
        Long userId = 1L;
        basketService.changeProductCountInBasket(userId, productId, post);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public ResponseEntity getBasketList(Pageable pageable) {
        Long userId = 1L;
        Page<GetBasket> basket = basketDbService.getBasketList(userId, pageable);
        TotalPriceInfo info = basketService.getTotalPriceInfo(userId);
        return ResponseEntity.ok().body(new WrapBasket<>(basket, info));
    }

    @DeleteMapping()
    public ResponseEntity deleteProductInBasket(@RequestBody @Valid DeleteBasket delete) {
        Long userId = 1L;
        basketService.deleteProductInBasket(userId, delete);
        return ResponseEntity.ok().build();
    }
}
