package com.sidenow.freshgreenish.domain.review.controller;

import com.sidenow.freshgreenish.domain.dto.MultiResponseDto;
import com.sidenow.freshgreenish.domain.review.dto.*;
import com.sidenow.freshgreenish.domain.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/purchase/{purchaseId}")
    public ResponseEntity postQuestion(@PathVariable("purchaseId") Long purchaseId,
                                       @RequestPart(value = "data") @Valid PostReview post,
                                       @RequestPart(required = false, value = "reviewImage") List<MultipartFile> reviewImage,
                                       @AuthenticationPrincipal OAuth2User oauth) {
        reviewService.postReview(oauth, purchaseId, post, reviewImage);
//        reviewService.postReviewS3(oauth, purchaseId, post, reviewImage);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{reviewId}")
    public ResponseEntity editQuestion(@PathVariable("reviewId") Long reviewId,
                                       @RequestPart(value = "data") @Valid EditReview edit,
                                       @RequestPart(required = false, value = "reviewImage") List<MultipartFile> reviewImage,
                                       @AuthenticationPrincipal OAuth2User oauth) {
        reviewService.editReview(oauth, reviewId, edit, reviewImage);
//        reviewService.editReviewS3(oauth, reviewId, edit, reviewImage);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/product/{productId}/sort/{sortId}")
    public ResponseEntity getReviewOnProductDetail(@PathVariable("productId") Long productId,
                                                   @PathVariable("sortId") Integer sortId,
                                                   @AuthenticationPrincipal OAuth2User oauth,
                                                   Pageable pageable) {
        Page<GetReviewOnProductDetail> review = reviewService.getReviewOnProductDetail(oauth, productId, pageable, sortId);
        return ResponseEntity.ok().body(new MultiResponseDto<>(review));

    }

    @GetMapping
    public ResponseEntity getReviewOnMyPage(@AuthenticationPrincipal OAuth2User oauth,
                                            Pageable pageable) {
        Page<GetReviewOnMyPage> review = reviewService.getReviewOnMyPage(oauth, pageable);
        return ResponseEntity.ok().body(new MultiResponseDto<>(review));
    }

    @PatchMapping("/{reviewId}")
    public ResponseEntity deleteQuestion(@PathVariable("reviewId") Long reviewId,
                                         @AuthenticationPrincipal OAuth2User oauth) {
        reviewService.deleteReview(oauth, reviewId);
//        reviewService.deleteReviewS3(oauth, reviewId);
        return ResponseEntity.ok().build();
    }

    /** --- reviewLike --- **/

    @GetMapping("/{reviewId}/likes/check")
    public ResponseEntity checkReviewLikes(@PathVariable("reviewId") Long reviewId,
                                           @AuthenticationPrincipal OAuth2User oauth) {
        boolean isLikes = reviewService.isUserReviewLikes(oauth, reviewId);
        return ResponseEntity.ok().body(new WrapReviewLikes(isLikes));
    }

    @GetMapping("/{reviewId}/likes")
    public ResponseEntity addOrDeleteReviewLikes(@PathVariable("reviewId") Long reviewId,
                                                 @AuthenticationPrincipal OAuth2User oauth) {
        boolean isLikes = reviewService.changeReviewLikesStatus(oauth, reviewId);
        return ResponseEntity.ok().body(new WrapReviewLikes(isLikes));
    }
}
