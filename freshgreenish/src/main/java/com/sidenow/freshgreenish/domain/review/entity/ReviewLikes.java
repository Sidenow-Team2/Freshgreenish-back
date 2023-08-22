package com.sidenow.freshgreenish.domain.review.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewLikes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REVIEW_LIKES_ID")
    private Long reviewLikesId;
    private Long userId;
    private Long reviewId;

    @Builder
    public ReviewLikes(Long reviewLikesId, Long userId, Long reviewId) {
        this.reviewLikesId = reviewLikesId;
        this.userId = userId;
        this.reviewId = reviewId;
    }
}
