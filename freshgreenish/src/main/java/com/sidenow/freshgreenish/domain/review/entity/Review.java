package com.sidenow.freshgreenish.domain.review.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sidenow.freshgreenish.global.utils.Auditable;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REVIEW")
    private Long id;

    @Column(length = 1000)
    private String content;

    private Long productId;
    private Long userId;

    private Integer likeCount;

    @JsonManagedReference
    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewImage> reviewImages = new ArrayList<>();

    @Builder
    public Review(Long id, String content, Long productId, Long userId) {
        this.id = id;
        this.content = content;
        this.productId = productId;
        this.userId = userId;
    }

    public void addReviewImage(ReviewImage reviewImage) {
        if(reviewImage.getReview() != this) reviewImage.addReview(this);
        reviewImages.add(reviewImage);
    }

    public void editReviewImage(List<ReviewImage> reviewImages) {
        this.reviewImages.clear();
        this.reviewImages.addAll(reviewImages);
    }
}
