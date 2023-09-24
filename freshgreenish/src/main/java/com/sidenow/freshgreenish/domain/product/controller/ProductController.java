package com.sidenow.freshgreenish.domain.product.controller;


//import jakarta.validation.Valid;

import com.sidenow.freshgreenish.domain.dto.MultiResponseDto;
import com.sidenow.freshgreenish.domain.dto.SingleResponseDto;
import com.sidenow.freshgreenish.domain.product.dto.EditProduct;
import com.sidenow.freshgreenish.domain.product.dto.GetProductDetail;
import com.sidenow.freshgreenish.domain.product.dto.GetProductCategory;
import com.sidenow.freshgreenish.domain.product.dto.PostProduct;
import com.sidenow.freshgreenish.domain.product.service.ProductDbService;
import com.sidenow.freshgreenish.domain.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductDbService productDbService;

    @PostMapping("/master/product")
    public ResponseEntity postProduct(@RequestPart(value = "data") @Valid PostProduct post,
                                      @RequestPart(value = "productImage") List<MultipartFile> productImage,
                                      @RequestPart(value = "productDetailImage") MultipartFile productDetailImage) {
        productService.postProduct(post, productImage, productDetailImage);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/master/product/{productId}")
    public ResponseEntity editProduct(@PathVariable("productId") Long productId,
                                      @RequestPart(value = "data") @Valid EditProduct edit,
                                      @RequestPart(required = false, value = "productImage") List<MultipartFile> productImage,
                                      @RequestPart(required = false, value = "productDetailImage") MultipartFile productDetailImage) {
        productService.editProduct(productId, edit, productImage, productDetailImage);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity getProductDetail(@PathVariable("productId") Long productId) {
        Long userId = 1L; // TODO : 추후 시큐리티 적용 후 수정 예정
        GetProductDetail product = productDbService.getProductDetail(productId, userId);
        return ResponseEntity.ok().body(new SingleResponseDto<>(product));
    }

    @GetMapping("/product/{category}/sort/{sortId}")
    public ResponseEntity getProductCategory(@PathVariable("category") String category,
                                             @PathVariable("sortId") Integer sortId,
                                             Pageable pageable) {
        Page<GetProductCategory> products = productDbService.getProductCategory(category, sortId, pageable);
        return ResponseEntity.ok().body(new MultiResponseDto<>(products));
    }

    @PatchMapping("/master/product/{productId}/delete")
    public ResponseEntity deleteProduct(@PathVariable("productId") Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/master/product/{productId}/restore")
    public ResponseEntity restoreProduct(@PathVariable("productId") Long productId) {
        productService.restoreProduct(productId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search/{title:.+}")
    public ResponseEntity searchProductTitle(@PathVariable("title") String title,
                                             Pageable pageable) {
        return ResponseEntity.ok().build();
    }
}
