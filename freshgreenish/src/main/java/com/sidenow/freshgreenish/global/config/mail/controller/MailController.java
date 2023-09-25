package com.sidenow.freshgreenish.global.config.mail.controller;

import com.sidenow.freshgreenish.global.config.mail.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @PostMapping("/mail")
    public String MailSend(@RequestParam String mail){

        int number = mailService.sendMail(mail);

        String num = "" + number;

        return num;
    }

}