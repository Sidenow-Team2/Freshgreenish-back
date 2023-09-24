package com.sidenow.freshgreenish.domain.review.controller;

import com.sidenow.freshgreenish.domain.dto.MultiResponseDto;
import com.sidenow.freshgreenish.domain.review.dto.GetReviewOnMyPage;
import com.sidenow.freshgreenish.domain.review.dto.GetReviewOnProductDetail;
import com.sidenow.freshgreenish.domain.review.dto.PostReview;
import com.sidenow.freshgreenish.domain.review.dto.WrapReviewLikes;
import com.sidenow.freshgreenish.domain.review.service.ReviewDbService;
import com.sidenow.freshgreenish.domain.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;
    private final ReviewDbService reviewDbService;

    // TODO : productId -> 구매Id로 변경해야 함
    @PostMapping("/product/{productId}")
    public ResponseEntity postQuestion(@PathVariable("productId") Long productId,
                                       @RequestBody @Valid PostReview post) {
        Long userId = 1L;
        reviewService.postReview(userId, productId, post);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{reviewId}")
    public ResponseEntity editQuestion(@PathVariable("reviewId") Long reviewId,
                                       @RequestBody @Valid PostReview edit) {
        Long userId = 1L;
        reviewService.editReview(userId, reviewId, edit);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/product/{productId}/sort/{sortId}")
    public ResponseEntity getReviewOnProductDetail(@PathVariable("productId") Long productId,
                                                   @PathVariable("sortId") Integer sortId,
                                                   Pageable pageable) {
        Long userId = 1L;
        Page<GetReviewOnProductDetail> review = reviewDbService.getReviewOnProductDetail(userId, productId, pageable, sortId);
        return ResponseEntity.ok().body(new MultiResponseDto<>(review));

    }

    @GetMapping
    public ResponseEntity getReviewOnMyPage(Pageable pageable) {
        Long userId = 1L;
        Page<GetReviewOnMyPage> review = reviewDbService.getReviewOnMyPage(userId, pageable);
        return ResponseEntity.ok().body(new MultiResponseDto<>(review));
    }

    @PatchMapping("/{reviewId}")
    public ResponseEntity deleteQuestion(@PathVariable("reviewId") Long reviewId) {
        Long userId = 1L;
        reviewService.deleteReview(userId, reviewId);
        return ResponseEntity.ok().build();
    }

    /** --- reviewLike --- **/

    @GetMapping("/{reviewId}/likes/check")
    public ResponseEntity checkReviewLikes(@PathVariable("reviewId") Long reviewId) {
        Long userId = 1L;
        boolean isLikes = reviewService.isUserReviewLikes(userId, reviewId);
        return ResponseEntity.ok().body(new WrapReviewLikes(isLikes));
    }

    @GetMapping("/{reviewId}/likes")
    public ResponseEntity addOrDeleteReviewLikes(@PathVariable("reviewId") Long reviewId) {
        Long userId = 1L;
        boolean isLikes = reviewService.changeReviewLikesStatus(userId, reviewId);
        return ResponseEntity.ok().body(new WrapReviewLikes(isLikes));
    }
}
