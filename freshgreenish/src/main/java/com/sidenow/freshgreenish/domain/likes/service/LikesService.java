package com.sidenow.freshgreenish.domain.likes.service;

import com.sidenow.freshgreenish.domain.likes.repository.LikesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikesService {
    private final LikesRepository likesRepository;
}
