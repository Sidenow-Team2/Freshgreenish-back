package com.sidenow.freshgreenish.global.config;

import com.sidenow.freshgreenish.global.config.auth.service.CustomOAuth2UserService;
import com.sidenow.freshgreenish.domain.user.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;


@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        MvcRequestMatcher.Builder mvc = new MvcRequestMatcher.Builder(introspector);
        http
                .csrf((csrf) -> csrf.disable())
                .headers((headers) -> headers
                        .frameOptions((fo) -> fo.disable()))
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(mvc.pattern("/"), mvc.pattern("/oauth2/**"), mvc.pattern("/login"), mvc.pattern("/h2-console"), mvc.pattern("/h2-console/**")).permitAll()
                        .requestMatchers(mvc.pattern("/api/v1/**")).hasRole(Role.USER.name()) // 유저만 접근 가능
                        .anyRequest().authenticated())
                .logout(logout-> logout
                        .logoutUrl("/logout")
                        .deleteCookies("remove")
                        .logoutSuccessUrl("/login"))
                .oauth2Login(oauth2Login -> oauth2Login
                        .loginPage("/login")
                        .defaultSuccessUrl("/")
                );

        return http.build();
    }


}
