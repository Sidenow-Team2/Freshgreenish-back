package com.sidenow.freshgreenish.domain.review.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.sidenow.freshgreenish.domain.review.entity.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class GetReviewOnMyPage {
    private Long reviewId;
    private Long productId;
    private String reviewTitle;
    private String reviewContent;
    private LocalDateTime lastModifiedAt;
    private String title;
    private Integer likeCount;

    @Builder
    @QueryProjection
    public GetReviewOnMyPage(Review review, Long productId, String title) {
        this.reviewId = review.getReviewId();
        this.productId = productId;
        this.reviewTitle = review.getReviewTitle();
        this.reviewContent = review.getReviewContent();
        this.lastModifiedAt = review.getLastModifiedAt();
        this.title = title;
        this.likeCount = review.getLikeCount();
    }
}
