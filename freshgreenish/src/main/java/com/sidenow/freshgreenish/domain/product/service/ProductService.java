package com.sidenow.freshgreenish.domain.product.service;

import com.sidenow.freshgreenish.domain.product.dto.PostProduct;
import com.sidenow.freshgreenish.domain.product.entity.Product;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductDbService productDbService;

    //이미지 등록 추가 예정
    @Transactional
    public void postProduct(PostProduct post) {
        Product product = returnProductFromPost(post);
        productDbService.saveProduct(product);
    }

    private Product returnProductFromPost(PostProduct post) {
        Integer price = post.getPrice();
        Integer discountRate = post.getDiscountRate();
        Integer discountPrice = price;
        if (discountRate != 0) {
            discountPrice = price - (price * discountRate / 100);
            if (discountPrice % 10 != 0) discountPrice = (discountPrice / 10) * 10 + 10;
        }

        return Product.builder()
                .title(post.getTitle())
                .subTitle(post.getSubTitle())
                .price(price)
                .discountRate(discountRate)
                .discountedPrice(discountPrice)
                .detail(post.getDetail())
                .deliveryType(post.getDeliveryType())
                .seller(post.getSeller())
                .packageType(post.getPackageType())
                .unit(post.getUnit())
                .capacity(post.getCapacity())
                .origin(post.getOrigin())
                .notification(post.getNotification())
                .productNumber(post.getProductNumber())
                .storageMethod(post.getStorageMethod())
                .brand(post.getBrand())
                .weight(post.getWeight())
                .variety(post.getVariety())
                .harvestSeason(post.getHarvestSeason())
                .recommendation(post.getRecommendation())
                .subscription(post.getSubscription())
                .build();
    }
}
