package com.sidenow.freshgreenish.domain.likes.repository;

import com.sidenow.freshgreenish.domain.likes.dto.GetLikesList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomLikesRepository {
    Page<GetLikesList> getLikesList(Long userId, Pageable pageable);
}
