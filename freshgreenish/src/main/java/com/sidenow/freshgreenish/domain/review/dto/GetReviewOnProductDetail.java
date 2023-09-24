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
public class GetReviewOnProductDetail {
    private Long reviewId;
    private Long productId;
    private Long userId;
    private String nickname;
    private String reviewTitle;
    private String reviewContent;
    private LocalDateTime lastModifiedAt;
    private Integer likeCount;
    private Boolean isReviewLikes;
    private List<ReviewImageVO> reviewImage;

    @Builder
    @QueryProjection
    public GetReviewOnProductDetail(Review review, Long productId, Long userId, String nickname, Boolean isReviewLikes) {
        this.reviewId = review.getReviewId();
        this.productId = productId;
        this.reviewTitle = review.getReviewTitle();
        this.reviewContent = review.getReviewContent();
        this.lastModifiedAt = review.getLastModifiedAt();
        this.likeCount = review.getLikeCount();
        this.userId = userId;
        this.nickname = nickname;
        this.isReviewLikes = isReviewLikes;
        this.reviewImage = review.getReviewImages().stream()
                .map(reviewImage -> ReviewImageVO.builder()
                        .reviewImageId(reviewImage.getReviewImageId())
                        .filePath(reviewImage.getFilePath())
                        .build())
                .collect(Collectors.toList());
    }
}
