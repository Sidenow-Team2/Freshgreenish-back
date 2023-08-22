package com.sidenow.freshgreenish.domain.review.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REVIEW_IMAGE_ID")
    private Long reviewImageId;
    private String originFileName;
    private String fileName;
    private String filePath;
    private Long fileSize;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REVIEW_ID")
    private Review review;

    @Builder
    public ReviewImage(Long reviewImageId, String originFileName, String fileName, String filePath, Long fileSize) {
        this.reviewImageId = reviewImageId;
        this.originFileName = originFileName;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }

    public void addReview(Review review) {
        this.review = review;
    }
}
