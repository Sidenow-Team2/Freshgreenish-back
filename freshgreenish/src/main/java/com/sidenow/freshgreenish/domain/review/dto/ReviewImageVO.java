package com.sidenow.freshgreenish.domain.review.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewImageVO {
    private Long reviewImageId;
    private String filePath;

    @Builder
    public ReviewImageVO(Long reviewImageId, String filePath) {
        this.reviewImageId = reviewImageId;
        this.filePath = filePath;
    }
}
