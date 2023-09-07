package com.sidenow.freshgreenish.config.security;

import com.sidenow.freshgreenish.config.security.service.CustomOAuth2UserService;
import com.sidenow.freshgreenish.domain.user.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;


@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name()) // 유저만 접근 가능
                    .anyRequest().authenticated()
                .and()
                    .logout()
                    .logoutSuccessUrl("/") // 로그아웃 성공시 이동할 URL
                .and()
                    .oauth2Login()
                        .loginPage("/login")
                        .defaultSuccessUrl("/")
                        .failureUrl("/login")
                        .userInfoEndpoint() // 사용자 정보 가져옴
                            .userService(customOAuth2UserService); // 가져온 사용자 정보 처리

        return http.build();
    }
}
