package com.sidenow.freshgreenish.domain.user.controller;

import com.sidenow.freshgreenish.domain.address.dto.GetAddressDTO;
import com.sidenow.freshgreenish.domain.user.service.MyInfoService;
import com.sidenow.freshgreenish.global.config.mail.controller.MailController;
import com.sidenow.freshgreenish.global.exception.BusinessLogicException;
import com.sidenow.freshgreenish.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MyInfoController {

    private final MyInfoService myInfoService;
    boolean isEmailAuthenticatedUser = false;

    @PostMapping("/mypage/verification")
    public boolean UserVerificationn(int code){
        isEmailAuthenticatedUser = MailController.UserVerificationn(code);

        return isEmailAuthenticatedUser; // 인증 완료시 true
    }

    @PostMapping("/mypage/changenicknmae")
    public void ChangeNickname(@AuthenticationPrincipal OAuth2User oauth, @RequestParam String nickname){
        if (!isEmailAuthenticatedUser){
            throw new BusinessLogicException(ExceptionCode.EMAIL_VERIFICATION_FIRST);
        } else{
            myInfoService.changeNickname(oauth, nickname);
        }
    }
}
