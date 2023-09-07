package com.sidenow.freshgreenish.domain.product.controller;

//import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {
    @PostMapping("/master/product")
    public ResponseEntity postProduct(/* @RequestBody @Valid PostProduct post */) {
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/master/product/{product-id}")
    public ResponseEntity editProduct(@PathVariable("product-id") Long productId
                                      /* @RequestBody @Valid editProduct edit */) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/product/{product-id}")
    public ResponseEntity getProductDetail(@PathVariable("product-id") Long productId) {
        return ResponseEntity.ok().build();
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
