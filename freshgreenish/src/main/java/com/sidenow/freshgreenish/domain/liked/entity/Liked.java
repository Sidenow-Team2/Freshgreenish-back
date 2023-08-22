package com.sidenow.freshgreenish.domain.liked.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Liked {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LIKED_ID")
    private Long likedId;
    private Long userId;
    private Long productId;

    @Builder
    public Liked(Long likedId, Long userId, Long productId) {
        this.likedId = likedId;
        this.userId = userId;
        this.productId = productId;
    }
}
