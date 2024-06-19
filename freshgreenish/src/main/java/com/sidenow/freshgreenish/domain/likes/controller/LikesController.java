package com.sidenow.freshgreenish.domain.likes.controller;

import com.sidenow.freshgreenish.domain.dto.MultiResponseDto;
import com.sidenow.freshgreenish.domain.likes.dto.GetLikesList;
import com.sidenow.freshgreenish.domain.likes.dto.WrapLikes;
import com.sidenow.freshgreenish.domain.likes.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/likes")
public class LikesController {
    private final LikesService likesService;

    @GetMapping("/product/{productId}/check")
    public ResponseEntity checkLikes(@PathVariable("productId") Long productId) {
        Long userId = 1L;
        boolean isLikes = likesService.isUserLikes(userId, productId);
        return ResponseEntity.ok().body(new WrapLikes(isLikes));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity addOrDeleteLikes(@PathVariable("productId") Long productId) {
        Long userId = 1L;
        boolean isLikes = likesService.changeLikesStatus(userId, productId);
        return ResponseEntity.ok().body(new WrapLikes(isLikes));
    }

    @GetMapping
    public ResponseEntity getLikes(Pageable pageable) {
        Long userId = 1L;
        Page<GetLikesList> LikesList = likesService.findLikesList(userId, pageable);
        return ResponseEntity.ok().body(new MultiResponseDto<>(LikesList));
    }
}
