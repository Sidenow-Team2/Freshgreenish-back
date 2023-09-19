package com.sidenow.freshgreenish.domain.user.service;

import com.sidenow.freshgreenish.domain.user.entity.User;
import com.sidenow.freshgreenish.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDbService {
    private final UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("존재 하지 않는 유저입니다.")
        );
    }
}
