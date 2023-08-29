package com.sidenow.freshgreenish.domain.review.repository;

import com.sidenow.freshgreenish.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long>, CustomReviewRepository {
}
