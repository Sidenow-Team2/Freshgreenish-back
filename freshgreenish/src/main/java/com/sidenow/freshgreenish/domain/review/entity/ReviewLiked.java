package com.sidenow.freshgreenish.domain.review.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewLiked {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REVIEW_LIKED_ID")
    private Long reviewLikedId;
    private Long userId;
    private Long reviewId;

    @Builder
    public ReviewLiked(Long reviewLikedId, Long userId, Long reviewId) {
        this.reviewLikedId = reviewLikedId;
        this.userId = userId;
        this.reviewId = reviewId;
    }
}
