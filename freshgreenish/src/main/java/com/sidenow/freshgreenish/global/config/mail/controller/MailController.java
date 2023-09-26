package com.sidenow.freshgreenish.global.config.mail.controller;

import com.sidenow.freshgreenish.global.config.mail.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;
    static int verifycode = 0;

    @PostMapping("/mypage/sendemail")
    public void mailSend(@AuthenticationPrincipal OAuth2User oauth){
        String userEmail = oauth.getAttribute("email");
        verifycode = mailService.sendMail(userEmail);
    }

    public static boolean UserVerificationn(int code){
        if (code == verifycode){
            return true;
        }
        return false;
    }
}