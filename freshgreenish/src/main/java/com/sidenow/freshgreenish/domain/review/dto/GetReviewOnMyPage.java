package com.sidenow.freshgreenish.domain.review.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.sidenow.freshgreenish.domain.review.entity.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class GetReviewOnMyPage {
    private Long reviewId;
    private Long productId;
    private String reviewTitle;
    private String reviewContent;
    private LocalDateTime lastModifiedAt;
    private String title;
    private String productFirstImage;
    private List<ReviewImageVO> reviewImage;
    private Integer likeCount;

    @Builder
    @QueryProjection
    public GetReviewOnMyPage(Review review, Long productId, String title, String productFirstImage) {
        this.reviewId = review.getReviewId();
        this.productId = productId;
        this.reviewTitle = review.getReviewTitle();
        this.reviewContent = review.getReviewContent();
        this.lastModifiedAt = review.getLastModifiedAt();
        this.title = title;
        this.likeCount = review.getLikeCount();
        this.reviewImage = review.getReviewImages().stream()
                .map(reviewImage -> ReviewImageVO.builder()
                        .reviewImageId(reviewImage.getReviewImageId())
                        .filePath(reviewImage.getFilePath())
                        .build())
                .collect(Collectors.toList());
        this.productFirstImage = productFirstImage;
    }
}
