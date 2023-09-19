package com.sidenow.freshgreenish.domain.payment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TossController {
    @GetMapping("/toss")
    public String tossPopup(){
        return "toss";
    }
    @GetMapping("/home")
    public String home(){
        return "home";
    }
}
