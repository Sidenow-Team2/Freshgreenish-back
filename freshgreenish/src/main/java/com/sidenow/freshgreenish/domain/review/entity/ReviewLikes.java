package com.sidenow.freshgreenish.domain.review.entity;

import com.sidenow.freshgreenish.global.utils.Auditable;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE review_likes SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class ReviewLikes extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REVIEW_LIKES_ID")
    private Long reviewLikesId;
    private Long userId;
    private Long reviewId;

    @Builder
    public ReviewLikes(Long reviewLikesId, Long userId, Long reviewId) {
        this.reviewLikesId = reviewLikesId;
        this.userId = userId;
        this.reviewId = reviewId;
    }
}
