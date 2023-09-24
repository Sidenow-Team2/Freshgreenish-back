package com.sidenow.freshgreenish.domain.product.service;

import com.sidenow.freshgreenish.domain.product.dto.PostProduct;
import com.sidenow.freshgreenish.domain.product.entity.Product;
import com.sidenow.freshgreenish.global.exception.BusinessLogicException;
import com.sidenow.freshgreenish.global.exception.ExceptionCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductDbService productDbService;

    // TODO : 이미지 등록 추가 예정
    @Transactional
    public void postProduct(PostProduct post) {
        Product findProduct = returnProductFromPost(post);
        Integer discountPrice =
                calculateDiscountPrice(post.getPrice(), findProduct.getPrice(), post.getDiscountRate(), findProduct.getDiscountRate());
        findProduct.setDiscountPrice(discountPrice);
        findProduct.setProductNumber(createProductNumber(findProduct.getCreatedAt()));

        productDbService.saveProduct(findProduct);
    }

    @Transactional
    public void editProduct(Long productId, PostProduct edit) {
        Product findProduct = productDbService.ifExistsReturnProduct(productId);
        findProduct.editProduct(edit);
        Integer discountPrice =
                calculateDiscountPrice(edit.getPrice(), findProduct.getPrice(), edit.getDiscountRate(), findProduct.getDiscountRate());
        findProduct.setDiscountPrice(discountPrice);

        productDbService.saveProduct(findProduct);
    }

    @Transactional
    public void deleteProduct(Long productId) {
        Product findProduct = productDbService.ifExistsReturnProduct(productId);

        if (findProduct.getDeleted().equals(true)) {
            throw new BusinessLogicException(ExceptionCode.PRODUCT_ALREADY_EXIST);
        }

        findProduct.setDeleted(true);

        productDbService.saveProduct(findProduct);
    }

    public void restoreProduct(Long productId) {
        // TODO : Product 조회 안됨(수정 필요)
        Product findProduct = productDbService.ifExistsReturnProduct(productId);

        if (findProduct.getDeleted().equals(true)) {
            throw new BusinessLogicException(ExceptionCode.PRODUCT_ALREADY_RESTORE);
        }

        findProduct.setDeleted(false);

        productDbService.saveProduct(findProduct);
    }

    private Product returnProductFromPost(PostProduct post) {
        return Product.builder()
                .title(post.getTitle())
                .subTitle(post.getSubTitle())
                .price(post.getPrice())
                .discountRate(post.getDiscountRate())
                .detail(post.getDetail())
                .deliveryType(post.getDeliveryType())
                .seller(post.getSeller())
                .packageType(post.getPackageType())
                .unit(post.getUnit())
                .capacity(post.getCapacity())
                .origin(post.getOrigin())
                .notification(post.getNotification())
                .storageMethod(post.getStorageMethod())
                .brand(post.getBrand())
                .weight(post.getWeight())
                .variety(post.getVariety())
                .harvestSeason(post.getHarvestSeason())
                .recommendation(post.getRecommendation())
                .subscription(post.getSubscription())
                .build();
    }

    private Integer calculateDiscountPrice(Integer editPrice, Integer productPrice, Integer editDiscountRate, Integer productDiscountRate) {
        Integer price = 0;
        Integer discountRate = 0;
        Integer discountPrice = price;

        if (editPrice == null) price = productPrice;
        price = editPrice;

        if (editDiscountRate == null) discountRate = productDiscountRate;
        discountRate = editDiscountRate;

        if (discountRate != 0) {
            discountPrice = price - (price * discountRate / 100);
            if (discountPrice % 10 != 0) discountPrice = (discountPrice / 10) * 10 + 10;
        }
        return discountPrice;
    }

    private String createProductNumber(LocalDateTime createdAt) {
        String createDay = createdAt.toString();
        String createDayStr = createDay.replaceAll("[^0-9]", "");
        String productNumber = createDayStr.substring(0, 15);

        return productNumber;
    }
}
