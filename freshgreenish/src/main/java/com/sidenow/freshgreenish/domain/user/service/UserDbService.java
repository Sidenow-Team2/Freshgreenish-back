package com.sidenow.freshgreenish.domain.user.service;

import com.sidenow.freshgreenish.domain.user.entity.User;
import com.sidenow.freshgreenish.domain.user.repository.UserRepository;
import com.sidenow.freshgreenish.global.exception.BusinessLogicException;
import com.sidenow.freshgreenish.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDbService {
    private final UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("존재 하지 않는 유저입니다.")
        );
    }

    public User ifExistsReturnUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
    }

    public User findUserByEmail(OAuth2User oauth) {
        String userEmail = oauth.getAttribute("email");
        return userRepository.findByEmail(userEmail)
                .orElseThrow(NullPointerException::new);
    }
}
