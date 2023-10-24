package com.sidenow.freshgreenish.domain.review.service;

import com.sidenow.freshgreenish.domain.purchase.entity.Purchase;
import com.sidenow.freshgreenish.domain.purchase.service.PurchaseDbService;
import com.sidenow.freshgreenish.domain.review.dto.EditReview;
import com.sidenow.freshgreenish.domain.review.dto.GetReviewOnMyPage;
import com.sidenow.freshgreenish.domain.review.dto.GetReviewOnProductDetail;
import com.sidenow.freshgreenish.domain.review.dto.PostReview;
import com.sidenow.freshgreenish.domain.review.entity.Review;
import com.sidenow.freshgreenish.domain.review.entity.ReviewImage;
import com.sidenow.freshgreenish.domain.review.entity.ReviewLikes;
import com.sidenow.freshgreenish.domain.review.repository.ReviewLikesRepository;
import com.sidenow.freshgreenish.domain.user.service.UserDbService;
import com.sidenow.freshgreenish.global.exception.BusinessLogicException;
import com.sidenow.freshgreenish.global.exception.ExceptionCode;
import com.sidenow.freshgreenish.global.file.AwsS3Service;
import com.sidenow.freshgreenish.global.file.FileHandler;
import com.sidenow.freshgreenish.global.file.UploadFile;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.core.user.OAuth2User;
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
    private final PurchaseDbService purchaseDbService;
    private final UserDbService userDbService;
    private final ReviewLikesRepository reviewLikesRepository;
    private final AwsS3Service awsS3Service;
    private final FileHandler fileHandler;

    @SneakyThrows
    @Transactional
    public void postReview(OAuth2User oauth, Long purchaseId, PostReview post, List<MultipartFile> reviewImage) {
        Long userId = userDbService.findUserIdByOauth(oauth);
        Purchase findPurchase = purchaseDbService.ifExistsReturnPurchase(purchaseId);

        Review findReview = Review.builder()
                .reviewTitle(post.getReviewTitle())
                .reviewContent(post.getReviewContent())
                .userId(userId)
                .purchaseId(purchaseId)
                .productId(findPurchase.getProductId())
                .build();

        List<UploadFile> reviewImages = fileHandler.uploadFileList(reviewImage);

        reviewDbService.saveReviewImage(reviewImages, findReview);
        reviewDbService.saveReview(findReview);
    }

    @SneakyThrows
    @Transactional
    public void postReviewS3(OAuth2User oauth, Long purchaseId, PostReview post, List<MultipartFile> reviewImage) {
        Long userId = userDbService.findUserIdByOauth(oauth);
        Purchase findPurchase = purchaseDbService.ifExistsReturnPurchase(purchaseId);

        Review findReview = Review.builder()
                .reviewTitle(post.getReviewTitle())
                .reviewContent(post.getReviewContent())
                .userId(userId)
                .purchaseId(purchaseId)
                .productId(findPurchase.getProductId())
                .build();

        String reviewImageDir = "reviewImage";
        List<UploadFile> reviewImages = awsS3Service.uploadFileList(reviewImage, reviewImageDir);

        reviewDbService.saveReviewImage(reviewImages, findReview);
        reviewDbService.saveReview(findReview);
    }

    @SneakyThrows
    @Transactional
    public void editReview(OAuth2User oauth, Long reviewId, EditReview edit, List<MultipartFile> reviewImage) {
        Long userId = userDbService.findUserIdByOauth(oauth);
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

    @SneakyThrows
    @Transactional
    public void editReviewS3(OAuth2User oauth, Long reviewId, EditReview edit, List<MultipartFile> reviewImage) {
        Long userId = userDbService.findUserIdByOauth(oauth);
        Review findReview = reviewDbService.ifExistsReturnReview(reviewId);
        CompareUserIdWithUserIdOfReviewForEditing(findReview, userId);
        findReview.editReview(edit);

        String reviewImageDir = "reviewImage";
        List<Long> deleteImageId = edit.getDeleteReviewImageId();
        List<ReviewImage> originalReviewImage = new ArrayList<>(findReview.getReviewImages());
        if (originalReviewImage.size() != 0) {
            for (Long imageId : deleteImageId) {
                for (int j = 0; j < originalReviewImage.size(); j++) {
                    if (Objects.equals(originalReviewImage.get(j).getReviewImageId(), imageId)) {
                        awsS3Service.delete(originalReviewImage.get(j).getFilePath(), reviewImageDir);
                        originalReviewImage.remove(j);
                        break;
                    }
                }
            }
        }

        List<UploadFile> reviewImages = awsS3Service.uploadFileList(reviewImage, reviewImageDir);
        findReview.editReviewImage(originalReviewImage);

        reviewDbService.saveReviewImage(reviewImages, findReview);
        reviewDbService.saveReview(findReview);
    }

    @Transactional
    public void deleteReview(OAuth2User oauth, Long reviewId) {
        Long userId = userDbService.findUserIdByOauth(oauth);
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

    @Transactional
    public void deleteReviewS3(OAuth2User oauth, Long reviewId) {
        Long userId = userDbService.findUserIdByOauth(oauth);
        Review findReview = reviewDbService.ifExistsReturnReview(reviewId);
        CompareUserIdWithUserIdOfReviewForDelete(findReview, userId);

        if (findReview.getDeleted().equals(true)) {
            throw new BusinessLogicException(ExceptionCode.REVIEW_ALREADY_DELETE);
        }

        findReview.setDeleted(true);

        String reviewImageDir = "reviewImage";
        findReview.getReviewImages()
                .forEach(reviewImage -> awsS3Service.delete(reviewImage.getOriginFileName(), reviewImageDir));

        reviewDbService.saveReview(findReview);
    }

    public Page<GetReviewOnProductDetail> getReviewOnProductDetail(OAuth2User oauth, Long productId, Pageable pageable, Integer sortId) {
        Long userId = userDbService.findUserIdByOauth(oauth);
        return reviewDbService.getReviewOnProductDetail(userId, productId, pageable, sortId);
    }

    public Page<GetReviewOnMyPage> getReviewOnMyPage(OAuth2User oauth, Pageable pageable) {
        Long userId = userDbService.findUserIdByOauth(oauth);
        return reviewDbService.getReviewOnMyPage(userId, pageable);
    }

    /** --- reviewLike --- **/

    public boolean isUserReviewLikes(OAuth2User oauth, Long reviewId) {
        Long userId = userDbService.findUserIdByOauth(oauth);
        if (userId == null) return false;

        Optional<ReviewLikes> findLikes = reviewLikesRepository.findByUserIdAndReviewId(userId, reviewId);
        return findLikes.isPresent();
    }

    public boolean changeReviewLikesStatus(OAuth2User oauth, Long reviewId) {
        Long userId = userDbService.findUserIdByOauth(oauth);
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
