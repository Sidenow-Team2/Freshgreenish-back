package com.sidenow.freshgreenish.domain.user.service;

import com.sidenow.freshgreenish.domain.address.repository.AddressRepository;
import com.sidenow.freshgreenish.domain.user.entity.User;
import com.sidenow.freshgreenish.domain.user.repository.MyInfoRepository;
import com.sidenow.freshgreenish.domain.user.repository.UserRepository;
import com.sidenow.freshgreenish.global.exception.BusinessLogicException;
import com.sidenow.freshgreenish.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyInfoService {

    private final UserRepository userRepository;

    public void changeNickname(@AuthenticationPrincipal OAuth2User oauth, String nickname) {
        if (userRepository.existsByNickname(nickname)) {
            throw new BusinessLogicException(ExceptionCode.DUPLICATE_NICKNAME);
        } else{
            String userEmail = oauth.getAttribute("email");
            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(
                            NullPointerException::new
                    );
            user.setNickname(nickname);
            userRepository.save(user);
        }
    }
}
