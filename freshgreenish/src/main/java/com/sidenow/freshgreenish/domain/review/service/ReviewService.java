package com.sidenow.freshgreenish.domain.review.service;

import com.sidenow.freshgreenish.domain.product.entity.Product;
import com.sidenow.freshgreenish.domain.product.service.ProductDbService;
import com.sidenow.freshgreenish.domain.review.dto.EditReview;
import com.sidenow.freshgreenish.domain.review.dto.PostReview;
import com.sidenow.freshgreenish.domain.review.entity.Review;
import com.sidenow.freshgreenish.domain.review.entity.ReviewImage;
import com.sidenow.freshgreenish.domain.review.entity.ReviewLikes;
import com.sidenow.freshgreenish.domain.review.repository.ReviewLikesRepository;
import com.sidenow.freshgreenish.global.exception.BusinessLogicException;
import com.sidenow.freshgreenish.global.exception.ExceptionCode;
import com.sidenow.freshgreenish.global.file.FileHandler;
import com.sidenow.freshgreenish.global.file.UploadFile;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewDbService reviewDbService;
    private final ProductDbService productDbService;
    private final ReviewLikesRepository reviewLikesRepository;
    private final FileHandler fileHandler;

    @SneakyThrows
    @Transactional
    public void postReview(Long userId, Long productId, PostReview post, List<MultipartFile> reviewImage) {
        Product findProduct = productDbService.ifExistsReturnProduct(productId);
        Review findReview = Review.builder()
                .reviewTitle(post.getReviewTitle())
                .reviewContent(post.getReviewContent())
                .userId(userId)
                .productId(productId)
                .build();

        List<UploadFile> reviewImages = fileHandler.uploadFileList(reviewImage);

        reviewDbService.saveReviewImage(reviewImages, findReview);
        reviewDbService.saveReview(findReview);
    }

    @SneakyThrows
    @Transactional
    public void editReview(Long userId, Long reviewId, EditReview edit, List<MultipartFile> reviewImage) {
        Review findReview = reviewDbService.ifExistsReturnReview(reviewId);
        CompareUserIdWithUserIdOfReviewForEditing(findReview, userId);
        findReview.editReview(edit);

        List<Long> deleteImageId = edit.getDeleteReviewImageId();
        List<ReviewImage> originalReviewImage = new ArrayList<>(findReview.getReviewImages());
        if (originalReviewImage.size() != 0) {
            for (Long imageId : deleteImageId) {
                for (int j = 0; j < originalReviewImage.size(); j++) {
                    if (Objects.equals(originalReviewImage.get(j).getReviewImageId(), imageId)) {
                        originalReviewImage.remove(j);
                        break;
                    }
                }
            }
        }

        List<UploadFile> reviewImages = fileHandler.uploadFileList(reviewImage);
        findReview.editReviewImage(originalReviewImage);

        reviewDbService.saveReviewImage(reviewImages, findReview);
        reviewDbService.saveReview(findReview);
    }

    @Transactional
    public void deleteReview(Long userId, Long reviewId) {
        Review findReview = reviewDbService.ifExistsReturnReview(reviewId);
        CompareUserIdWithUserIdOfReviewForDelete(findReview, userId);

        if (findReview.getDeleted().equals(true)) {
            throw new BusinessLogicException(ExceptionCode.REVIEW_ALREADY_DELETE);
        }

        findReview.setDeleted(true);

        findReview.getReviewImages()
                .forEach(reviewImage -> fileHandler.delete(reviewImage.getFilePath()));

        reviewDbService.saveReview(findReview);
    }

    /** --- reviewLike --- **/

    public boolean isUserReviewLikes(Long userId, Long reviewId) {
        if (userId == null) return false;
        Optional<ReviewLikes> findLikes = reviewLikesRepository.findByUserIdAndReviewId(userId, reviewId);
        return findLikes.isPresent();
    }

    public boolean changeReviewLikesStatus(Long userId, Long reviewId) {
        Optional<ReviewLikes> findLikes = reviewLikesRepository.findByUserIdAndReviewId(userId, reviewId);
        Review findReview = reviewDbService.ifExistsReturnReview(reviewId);
        if (findLikes.isPresent()) {
            reviewLikesRepository.delete(findLikes.get());
            findReview.reduceLikeCount();
            reviewDbService.saveReview(findReview);
            return false;
        }

        reviewLikesRepository.save(
                ReviewLikes.builder()
                        .userId(userId)
                        .reviewId(reviewId)
                        .build());
        findReview.addLikeCount();
        reviewDbService.saveReview(findReview);
        return true;
    }

    /** --- exception --- **/

    private void CompareUserIdWithUserIdOfReviewForEditing(Review review, Long userId) {
        if (!review.getUserId().equals(userId)) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED_FOR_UPDATE);
        }
    }

    private void CompareUserIdWithUserIdOfReviewForDelete(Review review, Long userId) {
        if (!review.getUserId().equals(userId)) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED_FOR_DELETE);
        }
    }
}
