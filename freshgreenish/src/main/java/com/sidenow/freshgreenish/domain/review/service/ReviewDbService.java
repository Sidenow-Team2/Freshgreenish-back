package com.sidenow.freshgreenish.domain.review.service;

import com.sidenow.freshgreenish.domain.review.dto.GetReviewOnMyPage;
import com.sidenow.freshgreenish.domain.review.dto.GetReviewOnProductDetail;
import com.sidenow.freshgreenish.domain.review.entity.Review;
import com.sidenow.freshgreenish.domain.review.entity.ReviewImage;
import com.sidenow.freshgreenish.domain.review.repository.ReviewRepository;
import com.sidenow.freshgreenish.global.exception.BusinessLogicException;
import com.sidenow.freshgreenish.global.exception.ExceptionCode;
import com.sidenow.freshgreenish.global.file.UploadFile;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewDbService {
    private final ReviewRepository reviewRepository;

    public void saveReview(Review review) {
        reviewRepository.save(review);
    }

    public void saveReviewImage(List<UploadFile> reviewImages, Review review) {
        reviewImages.forEach(reviewImage -> {
            ReviewImage createReviewImage = ReviewImage.builder()
                    .originFileName(reviewImage.getOriginFileName())
                    .fileName(reviewImage.getFileName())
                    .filePath(reviewImage.getFilePath())
                    .fileSize(reviewImage.getFileSize())
                    .build();
            review.addReviewImage(createReviewImage);
        });
    }

    public Review ifExistsReturnReview(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.REVIEW_NOT_FOUND));
    }

    public Page<GetReviewOnProductDetail> getReviewOnProductDetail(Long userId, Long productId, Pageable pageable, Integer sortId) {
        if (userId != null && sortId == 1)
            return reviewRepository.getReviewOnProductDetailOrderByReviewIdUponLogin(userId, productId, pageable);
        else if (userId != null && sortId == 2)
            return reviewRepository.getReviewOnProductDetailOrderByLikeCountUponLogin(userId, productId, pageable);
        else if (userId == null && sortId == 2)
            return reviewRepository.getReviewOnProductDetailOrderByLikeCount(productId, pageable);
        return reviewRepository.getReviewOnProductDetailOrderByReviewId(productId, pageable);
    }

    public Page<GetReviewOnMyPage> getReviewOnMyPage(Long userId, Pageable pageable) {
        return reviewRepository.getReviewOnMyPage(userId, pageable);
    }
}
