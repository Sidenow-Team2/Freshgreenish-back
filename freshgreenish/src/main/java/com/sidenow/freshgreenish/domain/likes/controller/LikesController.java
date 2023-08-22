package com.sidenow.freshgreenish.domain.likes.controller;

import lombok.RequiredArgsConstructor;
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
    @GetMapping("/product/{product-id}/check")
    public ResponseEntity checkLikes(@PathVariable("product-id") Long productId) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/product/{product-id}")
    public ResponseEntity addOrDeleteLikes(@PathVariable("product-id") Long productId) {
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity getLikes(Pageable pageable) {
        return ResponseEntity.ok().build();
    }
}
