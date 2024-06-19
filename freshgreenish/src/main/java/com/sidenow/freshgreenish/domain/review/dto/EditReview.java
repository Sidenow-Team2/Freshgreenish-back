package com.sidenow.freshgreenish.domain.review.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class EditReview {
    private String reviewTitle;
    private String reviewContent;
    private List<Long> deleteReviewImageId;
}
