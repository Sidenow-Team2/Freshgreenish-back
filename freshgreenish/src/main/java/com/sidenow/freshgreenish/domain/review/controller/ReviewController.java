package com.sidenow.freshgreenish.domain.review.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {
    @PostMapping("/product/{product-id}")
    public ResponseEntity postQuestion(@PathVariable("product-id") Long productId
                                      /* @RequestBody @Valid PostReview post */) {
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{review-id}")
    public ResponseEntity editQuestion(@PathVariable("review-id") Long reviewId
                                      /* @RequestBody @Valid editReview edit */) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/product/{product-id}")
    public ResponseEntity getReviewOfProduct(@PathVariable("product-id") Long productId,
                                             Pageable pageable) {
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity getReviewOnMyPage(Pageable pageable) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{review-id}")
    public ResponseEntity deleteQuestion(@PathVariable("review-id") Long reviewId) {
        return ResponseEntity.noContent().build();
    }
}
