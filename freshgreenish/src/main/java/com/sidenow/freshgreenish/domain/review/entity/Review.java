package com.sidenow.freshgreenish.domain.review.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sidenow.freshgreenish.domain.review.dto.EditReview;
import com.sidenow.freshgreenish.global.utils.Auditable;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE review SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Review extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REVIEW_ID")
    private Long reviewId;

    @Column(length = 1000)
    private String reviewTitle;

    @Column(length = 1000)
    private String reviewContent;

    private Long productId;
    private Long userId;

    private Integer likeCount = 0;

    @JsonManagedReference
    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewImage> reviewImages = new ArrayList<>();

    @Builder
    public Review(Long reviewId, String reviewTitle, String reviewContent, Long productId, Long userId) {
        this.reviewId = reviewId;
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
        this.productId = productId;
        this.userId = userId;
    }

    public void editReview(EditReview edit) {
        this.reviewTitle = edit.getReviewTitle();
        this.reviewContent = edit.getReviewContent();
    }

    public void addReviewImage(ReviewImage reviewImage) {
        if(reviewImage.getReview() != this) reviewImage.addReview(this);
        reviewImages.add(reviewImage);
    }

    public void editReviewImage(List<ReviewImage> reviewImages) {
        this.reviewImages.clear();
        this.reviewImages.addAll(reviewImages);
    }

    public void addLikeCount() {
        likeCount++;
    }

    public void reduceLikeCount() {
        likeCount--;
    }
}
