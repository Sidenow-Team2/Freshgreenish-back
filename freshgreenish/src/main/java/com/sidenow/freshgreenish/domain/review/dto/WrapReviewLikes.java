package com.sidenow.freshgreenish.domain.review.dto;

import lombok.Getter;

@Getter
public class WrapReviewLikes {
    private boolean reviewLikes;

    public WrapReviewLikes(boolean reviewLikes) {
        this.reviewLikes = reviewLikes;
    }
}
