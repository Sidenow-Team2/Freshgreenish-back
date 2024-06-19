package com.sidenow.freshgreenish.domain.user.controller;

import com.sidenow.freshgreenish.domain.user.entity.User;
import com.sidenow.freshgreenish.domain.user.repository.UserRepository;
import com.sidenow.freshgreenish.global.config.auth.dto.SessionUser;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@RequiredArgsConstructor
@Controller
public class UserController {

    @Autowired private UserRepository userRepository;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model){
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if (user != null) {
            model.addAttribute("userName", user.getNickname());
            model.addAttribute("imgUrl", user.getPicture());
        }
        return "home";
    }

    /* 이미지 화면 조회 테스트용: 수정 예정 */
    @GetMapping("/showimage")
    public String index(@AuthenticationPrincipal OAuth2User oauth, Model model){
        String userEmail = oauth.getAttribute("email");
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(
                        NullPointerException::new
                );
        if (user != null) {
            model.addAttribute("userName", user.getNickname());
            model.addAttribute("imgUrl", user.getFilePath());
        }
        return "home";
    }


    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }


    @GetMapping("/oauth/loginInfo")
    @ResponseBody
    public String oauthLoginInfo(Authentication authentication, @AuthenticationPrincipal OAuth2User oAuth2UserPrincipal){
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        System.out.println(attributes);

        Map<String, Object> attributes1 = oAuth2UserPrincipal.getAttributes();

        return attributes.toString();
        //세션에 담긴 user 정보를 가져올 수 있음
    }






}
