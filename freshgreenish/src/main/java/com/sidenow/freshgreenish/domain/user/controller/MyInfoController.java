package com.sidenow.freshgreenish.domain.user.controller;

import com.sidenow.freshgreenish.domain.address.dto.PostAddressDTO;
import com.sidenow.freshgreenish.domain.user.service.MyInfoService;
import com.sidenow.freshgreenish.global.config.mail.controller.MailController;
import com.sidenow.freshgreenish.global.exception.BusinessLogicException;
import com.sidenow.freshgreenish.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


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

    @PatchMapping("/mypage/changeaddress")
    public void ChangeDefaultAddress(@AuthenticationPrincipal OAuth2User oauth, @RequestBody PostAddressDTO dto){
        if (!isEmailAuthenticatedUser){
            throw new BusinessLogicException(ExceptionCode.EMAIL_VERIFICATION_FIRST);
        } else{
            myInfoService.changeAddress(oauth, dto);
        }
    }

    @PostMapping("/mypage/changeImage")
    public void ChangeUserImage(@AuthenticationPrincipal OAuth2User oauth, @RequestParam(name="filepath") MultipartFile filepath) throws Exception {
//        if (!isEmailAuthenticatedUser){
//            throw new BusinessLogicException(ExceptionCode.EMAIL_VERIFICATION_FIRST);
//        } else{
//            myInfoService.changeImage(oauth, filepath);
//        }
        myInfoService.changeImage(oauth, filepath);

        if (!isEmailAuthenticatedUser){
            throw new BusinessLogicException(ExceptionCode.EMAIL_VERIFICATION_FIRST);
        } else{
            myInfoService.changeImage(oauth, filepath);
        }
    }
}
