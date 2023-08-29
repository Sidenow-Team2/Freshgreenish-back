package com.sidenow.freshgreenish.domain.review.service;

import com.sidenow.freshgreenish.domain.review.entity.Review;
import com.sidenow.freshgreenish.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewDbService {
    private final ReviewRepository reviewRepository;

    public void saveReview(Review review) {
        reviewRepository.save(review);
    }
}
