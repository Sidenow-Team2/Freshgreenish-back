package com.sidenow.freshgreenish.domain.likes.dto;

import lombok.Getter;

@Getter
public class WrapLikes {
    private boolean likes;

    public WrapLikes(boolean likes) {
        this.likes = likes;
    }
}
