package com.sidenow.freshgreenish.domain.review.repository;

import com.sidenow.freshgreenish.domain.review.dto.GetReviewOnMyPage;
import com.sidenow.freshgreenish.domain.review.dto.GetReviewOnProductDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomReviewRepository {
    Page<GetReviewOnProductDetail> getReviewOnProductDetailOrderByReviewId(Long productId, Pageable pageable);
    Page<GetReviewOnProductDetail> getReviewOnProductDetailOrderByReviewIdUponLogin(Long userId, Long productId, Pageable pageable);
    Page<GetReviewOnProductDetail> getReviewOnProductDetailOrderByLikeCount(Long productId, Pageable pageable);
    Page<GetReviewOnProductDetail> getReviewOnProductDetailOrderByLikeCountUponLogin(Long userId, Long productId, Pageable pageable);

    Page<GetReviewOnMyPage> getReviewOnMyPage(Long userId, Pageable pageable);
}
