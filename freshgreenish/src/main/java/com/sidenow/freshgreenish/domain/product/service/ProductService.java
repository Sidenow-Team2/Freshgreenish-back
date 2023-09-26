package com.sidenow.freshgreenish.domain.product.service;

import com.sidenow.freshgreenish.domain.product.dto.EditProduct;
import com.sidenow.freshgreenish.domain.product.dto.PostProduct;
import com.sidenow.freshgreenish.domain.product.entity.Product;
import com.sidenow.freshgreenish.domain.product.entity.ProductImage;
import com.sidenow.freshgreenish.domain.user.entity.User;
import com.sidenow.freshgreenish.global.exception.BusinessLogicException;
import com.sidenow.freshgreenish.global.exception.ExceptionCode;
import com.sidenow.freshgreenish.global.file.FileHandler;
import com.sidenow.freshgreenish.global.file.UploadFile;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductDbService productDbService;
    private final FileHandler fileHandler;

    @SneakyThrows
    @Transactional
    public void postProduct(PostProduct post, List<MultipartFile> productImage, MultipartFile productDetailImage, OAuth2User oauth){
        User findUser = productDbService.findUser(oauth);

        Product findProduct = returnProductFromPost(post);
        Integer discountPrice =
                calculateDiscountPrice(post.getPrice(), findProduct.getPrice(), post.getDiscountRate(), findProduct.getDiscountRate());
        findProduct.setDiscountPrice(discountPrice);
        findProduct.setProductNumber(createProductNumber(findProduct.getCreatedAt()));

        List<UploadFile> productImages = fileHandler.uploadFileList(productImage);
        UploadFile uploadFile = fileHandler.uploadFile(productDetailImage);
        findProduct.setProductDetailImage(uploadFile.getFilePath());

        productDbService.saveProductImage(productImages, findProduct);

        Product newProduct = productDbService.saveAndReturnProduct(findProduct);
        newProduct.setProductFirstImage(findProduct.getProductImages());

        productDbService.saveProduct(newProduct);
    }

    @SneakyThrows
    @Transactional
    public void editProduct(Long productId, EditProduct edit, List<MultipartFile> productImage, MultipartFile productDetailImage, OAuth2User oauth) {
        User findUser = productDbService.findUser(oauth);

        Product findProduct = productDbService.ifExistsReturnProduct(productId);
        findProduct.editProduct(edit);
        Integer discountPrice =
                calculateDiscountPrice(edit.getPrice(), findProduct.getPrice(), edit.getDiscountRate(), findProduct.getDiscountRate());
        findProduct.setDiscountPrice(discountPrice);

        List<Long> deleteImageId = edit.getDeleteImageId();
        List<ProductImage> originalProductImage = new ArrayList<>(findProduct.getProductImages());
        if (originalProductImage.size() != 0) {
            for (Long imageId : deleteImageId) {
                for (int j = 0; j < originalProductImage.size(); j++) {
                    if (Objects.equals(originalProductImage.get(j).getProductImageId(), imageId)) {
                        originalProductImage.remove(j);
                        break;
                    }
                }
            }
        }

        List<UploadFile> productImages = fileHandler.uploadFileList(productImage);
        findProduct.editProductImage(originalProductImage);

        productDbService.saveProductImage(productImages, findProduct);

        if (!productDetailImage.isEmpty()) {
            UploadFile uploadFile = fileHandler.uploadFile(productDetailImage);
            findProduct.setProductDetailImage(uploadFile.getFilePath());
        }

        Product newProduct = productDbService.saveAndReturnProduct(findProduct);
        newProduct.setProductFirstImage(findProduct.getProductImages());

        productDbService.saveProduct(newProduct);
    }

    @Transactional
    public void deleteProduct(Long productId, OAuth2User oauth) {
        User findUser = productDbService.findUser(oauth);

        Product findProduct = productDbService.ifExistsReturnProduct(productId);

        if (findProduct.getDeleted().equals(true)) {
            throw new BusinessLogicException(ExceptionCode.PRODUCT_ALREADY_DELETE);
        }

        findProduct.setDeleted(true);

        productDbService.saveProduct(findProduct);
    }

    public void restoreProduct(Long productId, OAuth2User oauth) {
        User findUser = productDbService.findUser(oauth);

        Product findProduct = productDbService.ifExistsReturnProduct(productId);

        if (findProduct.getDeleted().equals(true)) {
            throw new BusinessLogicException(ExceptionCode.PRODUCT_ALREADY_EXIST);
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
