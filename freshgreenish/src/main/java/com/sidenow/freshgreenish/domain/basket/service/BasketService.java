package com.sidenow.freshgreenish.domain.basket.service;

import com.sidenow.freshgreenish.domain.basket.dto.DeleteBasket;
import com.sidenow.freshgreenish.domain.basket.dto.PostBasket;
import com.sidenow.freshgreenish.domain.basket.dto.TotalPriceInfo;
import com.sidenow.freshgreenish.domain.basket.entity.Basket;
import com.sidenow.freshgreenish.domain.basket.entity.ProductBasket;
import com.sidenow.freshgreenish.domain.product.entity.Product;
import com.sidenow.freshgreenish.domain.product.service.ProductDbService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BasketService {
    private final BasketDbService basketDbService;
    private final ProductDbService productDbService;

    public void addProductInBasket(Long productId, Long userId, PostBasket post) {
        Basket findBasket = basketDbService.ifExistsReturnBasket(userId);
        Product findProduct = productDbService.ifExistsReturnProduct(productId);
        Optional<ProductBasket> findProductBasket =
                basketDbService.ifExistFindByProductIdAndBasketId(productId, findBasket.getBasketId());

        if (findProductBasket.isPresent()) {
            Integer originalCount = findProductBasket.get().getCount();
            Integer totalCount = originalCount + post.getCount();

            findProductBasket.get().setCount(totalCount);
            findProductBasket.get().setTotalPrice(totalCount * findProduct.getPrice());
            findProductBasket.get().setDiscountedTotalPrice(totalCount * findProduct.getDiscountPrice());

            findBasket.addProductBasket(findProductBasket.get());

            findProductBasket.get().addBasket(findBasket);
            findProductBasket.get().addProduct(findProduct);
        } else {
            ProductBasket productBasket = ProductBasket.builder()
                .count(post.getCount())
                .totalPrice(findProduct.getPrice() * post.getCount())
                .discountedTotalPrice(findProduct.getDiscountPrice() * post.getCount())
                .build();

            findBasket.addProductBasket(productBasket);

            productBasket.addBasket(findBasket);
            productBasket.addProduct(findProduct);
        }
        productDbService.saveProduct(findProduct);

        Basket newBasket = basketDbService.saveAndReturnBasket(findBasket);

        calculateTotalPrice(newBasket);

        basketDbService.saveBasket(newBasket);
    }

    public void changeProductCountInBasket(Long userId, Long productId, PostBasket post) {
        Basket findBasket = basketDbService.ifExistsReturnBasketByUserId(userId);
        ProductBasket findProductBasket =
                basketDbService.ifExistsReturnProductBasket(productId, findBasket.getBasketId());

        Integer count = post.getCount();

        findProductBasket.setCount(count);
        findProductBasket.setTotalPrice(count * productDbService.getPrice(findProductBasket.getProduct().getProductId()));
        findProductBasket.setDiscountedTotalPrice(count * productDbService.getDiscountPrice(findProductBasket.getProduct().getProductId()));

        findBasket.addProductBasket(findProductBasket);

        Basket newBasket = basketDbService.saveAndReturnBasket(findBasket);

        calculateTotalPrice(newBasket);

        basketDbService.saveBasket(newBasket);
    }

    public void deleteProductInBasket(Long userId, DeleteBasket delete) {
        Basket findBasket = basketDbService.ifExistsReturnBasket(userId);
        List<Long> deleteId = delete.getDeleteProductId();

        basketDbService.deleteAllByProductBasketId(deleteId);

        calculateTotalPrice(findBasket);

        basketDbService.saveBasket(findBasket);
    }

    public TotalPriceInfo getTotalPriceInfo(Long userId) {
        Basket findBasket = basketDbService.ifExistsReturnBasket(userId);

        return TotalPriceInfo.builder()
                .totalBasketPrice(findBasket.getTotalBasketPrice())
                .discountedBasketTotalPrice(findBasket.getDiscountedBasketTotalPrice())
                .discountedBasketPrice(findBasket.getDiscountedBasketPrice())
                .build();
    }

    private void calculateTotalPrice(Basket basket) {
        Integer totalBasketPrice = basketDbService.getTotalBasketPrice(basket.getBasketId());
        Integer discountedTotalPrice = basketDbService.getDiscountedTotalBasketPrice(basket.getBasketId());
        Integer discountedBasketPrice = totalBasketPrice - discountedTotalPrice;

        basket.setBasketPrice(totalBasketPrice, discountedTotalPrice, discountedBasketPrice);
    }
}
