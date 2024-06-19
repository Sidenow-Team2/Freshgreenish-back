package com.sidenow.freshgreenish.domain.likes.repository;

import com.sidenow.freshgreenish.domain.likes.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long>, CustomLikesRepository {
    Optional<Likes> findByUserIdAndProductId(Long userId, Long productId);
}
