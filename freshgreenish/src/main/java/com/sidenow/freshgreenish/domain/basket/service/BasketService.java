package com.sidenow.freshgreenish.domain.basket.service;

import com.sidenow.freshgreenish.domain.basket.dto.DeleteBasket;
import com.sidenow.freshgreenish.domain.basket.dto.GetBasket;
import com.sidenow.freshgreenish.domain.basket.dto.PostBasket;
import com.sidenow.freshgreenish.domain.basket.dto.TotalPriceInfo;
import com.sidenow.freshgreenish.domain.basket.entity.Basket;
import com.sidenow.freshgreenish.domain.basket.entity.ProductBasket;
import com.sidenow.freshgreenish.domain.product.entity.Product;
import com.sidenow.freshgreenish.domain.product.service.ProductDbService;
import com.sidenow.freshgreenish.domain.user.service.UserDbService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BasketService {
    private final BasketDbService basketDbService;
    private final ProductDbService productDbService;
    private final UserDbService userDbService;

    public void addProductInBasket(Long productId, OAuth2User oauth, PostBasket post, String type) {
        Basket findBasket = basketDbService.ifExistsReturnBasket(userDbService.findUserIdByOauth(oauth));
        Product findProduct = productDbService.ifExistsReturnProduct(productId);

        switch (type) {
            case "regular":
                addProductRegularBasket(productId, findBasket, post, findProduct);
                break;
            default:
                addProductBasket(productId, findBasket, post, findProduct);
        }

        productDbService.saveProduct(findProduct);
    }

    private void addProductBasket(Long productId, Basket basket, PostBasket post, Product product) {
        Optional<ProductBasket> findProductBasket =
                basketDbService.ifExistFindByProductIdAndBasketIdAndIdRegular(productId, basket.getBasketId(), false);

        if (findProductBasket.isPresent()) {
            Integer originalCount = findProductBasket.get().getCount();
            Integer totalCount = originalCount + post.getCount();

            findProductBasket.get().setIsRegular(false);
            findProductBasket.get().setCount(totalCount);
            findProductBasket.get().setTotalPrice(totalCount * product.getPrice());
            findProductBasket.get().setDiscountedTotalPrice(totalCount * product.getDiscountPrice());

            basket.addProductBasket(findProductBasket.get());

            findProductBasket.get().addBasket(basket);
            findProductBasket.get().addProduct(product);
        } else {
            ProductBasket productBasket = ProductBasket.builder()
                    .isRegular(false)
                    .count(post.getCount())
                    .totalPrice(product.getPrice() * post.getCount())
                    .discountedTotalPrice(product.getDiscountPrice() * post.getCount())
                    .build();

            basket.addProductBasket(productBasket);

            productBasket.addBasket(basket);
            productBasket.addProduct(product);
        }

        Basket newBasket = basketDbService.saveAndReturnBasket(basket);
        calculateTotalPrice(newBasket);

        basketDbService.saveBasket(newBasket);
    }

    private void addProductRegularBasket(Long productId, Basket basket, PostBasket post, Product product) {
        Optional<ProductBasket> findProductBasket =
                basketDbService.ifExistFindByProductIdAndBasketIdAndIdRegular(productId, basket.getBasketId(), true);

        if (findProductBasket.isPresent()) {
            Integer originalCount = findProductBasket.get().getCount();
            Integer totalCount = originalCount + post.getCount();

            findProductBasket.get().setIsRegular(true);
            findProductBasket.get().setCount(totalCount);
            findProductBasket.get().setTotalPrice(totalCount * product.getPrice());
            findProductBasket.get().setDiscountedTotalPrice(totalCount * product.getDiscountPrice());

            basket.addProductBasket(findProductBasket.get());

            findProductBasket.get().addBasket(basket);
            findProductBasket.get().addProduct(product);
        } else {
            ProductBasket productBasket = ProductBasket.builder()
                    .isRegular(true)
                    .count(post.getCount())
                    .totalPrice(product.getPrice() * post.getCount())
                    .discountedTotalPrice(product.getDiscountPrice() * post.getCount())
                    .build();

            basket.addProductBasket(productBasket);

            productBasket.addBasket(basket);
            productBasket.addProduct(product);
        }

        Basket newBasket = basketDbService.saveAndReturnBasket(basket);
        calculateRegularTotalPrice(newBasket);

        basketDbService.saveBasket(newBasket);
    }

    public void changeProductCountInBasket(OAuth2User oauth, Long productId, PostBasket post, String type) {
        Basket findBasket = basketDbService.ifExistsReturnBasketByUserId(userDbService.findUserIdByOauth(oauth));

        ProductBasket findProductBasket;

        switch (type) {
            case "regular":
                findProductBasket =
                        basketDbService.ifExistsReturnProductBasketByIsRegular(productId, findBasket.getBasketId(), true);
                break;
            default:
                findProductBasket =
                        basketDbService.ifExistsReturnProductBasketByIsRegular(productId, findBasket.getBasketId(), false);
        }

        Integer count = post.getCount();

        findProductBasket.setCount(count);
        findProductBasket.setTotalPrice(count * productDbService.getPrice(findProductBasket.getProduct().getProductId()));
        findProductBasket.setDiscountedTotalPrice(count * productDbService.getDiscountPrice(findProductBasket.getProduct().getProductId()));

        findBasket.addProductBasket(findProductBasket);

        Basket newBasket = basketDbService.saveAndReturnBasket(findBasket);

        calculateTotalPrice(newBasket);

        basketDbService.saveBasket(newBasket);
    }

    private void changeProductCount() {

    }

    public void deleteBasket(OAuth2User oauth, DeleteBasket delete, String type) {
        Long userId = userDbService.findUserIdByOauth(oauth);

        switch (type) {
            case "regular":
                deleteProductInRegular(userId, delete);
                break;
            default:
                deleteProductInBasket(userId, delete);
        }
    }

    public void deleteProductInBasket(Long userId, DeleteBasket delete) {
        Basket findBasket = basketDbService.ifExistsReturnBasket(userId);
        List<Long> deleteId = delete.getDeleteProductId();

        basketDbService.deleteAllByProductBasketId(deleteId);

        calculateTotalPrice(findBasket);

        basketDbService.saveBasket(findBasket);
    }

    public void deleteProductInRegular(Long userId, DeleteBasket delete) {
        Basket findBasket = basketDbService.ifExistsReturnBasket(userId);
        List<Long> deleteId = delete.getDeleteProductId();

        basketDbService.deleteAllByProductBasketId(deleteId);

        calculateRegularTotalPrice(findBasket);

        basketDbService.saveBasket(findBasket);
    }

    public TotalPriceInfo getTotalPriceInfo(OAuth2User oauth, String type) {
        Basket findBasket = basketDbService.ifExistsReturnBasket(userDbService.findUserIdByOauth(oauth));

        switch (type) {
            case "regular":
                return TotalPriceInfo.builder()
                        .totalBasketPrice(findBasket.getTotalRegularPrice())
                        .discountedBasketTotalPrice(findBasket.getDiscountedRegularTotalPrice())
                        .discountedBasketPrice(findBasket.getDiscountedRegularPrice())
                        .build();
            default:
                return TotalPriceInfo.builder()
                        .totalBasketPrice(findBasket.getTotalBasketPrice())
                        .discountedBasketTotalPrice(findBasket.getDiscountedBasketTotalPrice())
                        .discountedBasketPrice(findBasket.getDiscountedBasketPrice())
                        .build();
        }
    }

    private void calculateTotalPrice(Basket basket) {
        Integer totalBasketPrice = basketDbService.getTotalBasketPrice(basket.getBasketId());
        Integer discountedTotalPrice = basketDbService.getDiscountedTotalBasketPrice(basket.getBasketId());
        Integer discountedBasketPrice = totalBasketPrice - discountedTotalPrice;

        basket.setBasketPrice(totalBasketPrice, discountedTotalPrice, discountedBasketPrice);
    }

    private void calculateRegularTotalPrice(Basket basket) {
        Integer totalRegularPrice = basketDbService.getTotalRegularPrice(basket.getBasketId());
        Integer discountedRegularTotalPrice = basketDbService.getDiscountedTotalRegularPrice(basket.getBasketId());
        Integer discountedRegularPrice = totalRegularPrice - discountedRegularTotalPrice;

        basket.setRegularPrice(totalRegularPrice, discountedRegularTotalPrice, discountedRegularPrice);
    }

    public Page<GetBasket> getBasketList(OAuth2User oauth, Pageable pageable, String type) {
        Long userId = userDbService.findUserIdByOauth(oauth);

        switch (type) {
            case "regular":
                return basketDbService.getRegularList(userId, pageable);
            default:
                return basketDbService.getBasketList(userId, pageable);
        }
    }
}
