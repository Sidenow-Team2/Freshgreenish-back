package com.sidenow.freshgreenish.domain.review.repository;

import com.sidenow.freshgreenish.domain.review.entity.ReviewLikes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewLikesRepository extends JpaRepository<ReviewLikes, Long>{
    Optional<ReviewLikes> findByUserIdAndReviewId(Long userId, Long reviewId);
}
