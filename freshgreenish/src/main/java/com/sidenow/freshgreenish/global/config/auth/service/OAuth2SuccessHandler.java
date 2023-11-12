package com.sidenow.freshgreenish.global.config.auth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sidenow.freshgreenish.domain.user.entity.Role;
import com.sidenow.freshgreenish.domain.user.entity.User;
import com.sidenow.freshgreenish.domain.user.repository.UserRepository;
import com.sidenow.freshgreenish.global.config.auth.dto.SessionUser;
import com.sidenow.freshgreenish.global.config.auth.dto.Token;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Map;



@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final HttpSession httpSession;
    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
        var attributes = oAuth2User.getAttributes();

        log.info("Principal에서 꺼낸 OAuth2User = {}", oAuth2User);

        // 최초 로그인이라면 회원가입 처리
        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user));

        String targetUrl;
        log.info("토큰 발행 시작");

        Token token = tokenService.generateToken(user.getEmail(), "USER");
        log.info("{}", token);
        targetUrl = UriComponentsBuilder.fromUriString("/")
                .queryParam("token", "token")
                .build().toUriString();
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    private User saveOrUpdate(Map attributes){
        User user = userRepository.findByEmail((String)attributes.get("email"))
                .map(entity -> entity.update((String)attributes.get("name"), (String)attributes.get("picture")))
                .orElse(User.builder()
                        .nickname((choiceName(customOAuth2UserService.getRegistrationId(), attributes)))
                        .email((String)attributes.get("email"))
                        .socialType(customOAuth2UserService.getRegistrationId())
                        .filePath(choicePicture(customOAuth2UserService.getRegistrationId(), attributes))
                        .role(Role.USER)
                        .build());
        return userRepository.save(user);
    }
    private String choiceName(String socialType, Map attributes){
        String name = null;
        switch(socialType){
            case "naver":
                name = (String)attributes.get("nickname");
                break;
            case "google":
                name = (String)attributes.get("name");
                break;
        }
        return name;
    }
    private String choicePicture(String socialType, Map attributes){
        String picture = null;
        switch(socialType){
            case "naver":
                picture = (String)attributes.get("profile_image");
                break;
            case "google":
                picture = (String)attributes.get("picture");
                break;
        }
        return picture;
    }
}