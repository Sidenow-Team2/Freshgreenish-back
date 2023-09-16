package com.sidenow.freshgreenish.domain.product.controller;

<<<<<<< HEAD
//import jakarta.validation.Valid;
=======
import com.sidenow.freshgreenish.domain.dto.MultiResponseDto;
import com.sidenow.freshgreenish.domain.dto.SingleResponseDto;
import com.sidenow.freshgreenish.domain.product.dto.GetProductDetail;
import com.sidenow.freshgreenish.domain.product.dto.GetProductCategory;
import com.sidenow.freshgreenish.domain.product.dto.PostProduct;
import com.sidenow.freshgreenish.domain.product.service.ProductDbService;
import com.sidenow.freshgreenish.domain.product.service.ProductService;
import jakarta.validation.Valid;
>>>>>>> d992d1ff4ca4215a6a1b5317068c84026c74c2fa
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(originPatterns = "http://localhost:8080")
@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductDbService productDbService;

    // 이미지 등록 기능 추가 예정
    @PostMapping("/master/product")
    public ResponseEntity postProduct(@RequestBody @Valid PostProduct post) {
                                    /* @RequestPart(required = false, value = "productImage") List<MultipartFile> productImage */
                                    /* @RequestPart(required = false, value = "productDetailImage") MultipartFile productDetailImage */
        productService.postProduct(post);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/master/product/{product-id}")
    public ResponseEntity editProduct(@PathVariable("product-id") Long productId
                                      /* @RequestBody @Valid editProduct edit */) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/product/{product-id}")
    public ResponseEntity getProductDetail(@PathVariable("product-id") Long productId) {
        Long userId = 1L; //추후 시큐리티 적용 후 수정 예정
        GetProductDetail product = productDbService.getProductDetail(productId, userId);
        return ResponseEntity.ok().body(new SingleResponseDto<>(product));
    }

    @GetMapping("/product/{category}/sort/{sort-id}")
    public ResponseEntity getProductCategory(@PathVariable("category") String category,
                                             @PathVariable("sort-id") Integer sortId,
                                             Pageable pageable) {
        Page<GetProductCategory> products = productDbService.getProductCategory(category, sortId, pageable);
        return ResponseEntity.ok().body(new MultiResponseDto<>(products));
    }

    @DeleteMapping("/master/product/{product-id}")
    public ResponseEntity deleteProduct(@PathVariable("product-id") Long productId) {
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/{title:.+}")
    public ResponseEntity searchProductTitle(@PathVariable("title") String title,
                                             Pageable pageable) {
        return ResponseEntity.ok().build();
    }
}
