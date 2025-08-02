package com.iceAge.server.global.config;

import com.iceAge.server.auth.application.CustomOAuth2UserService;
import com.iceAge.server.auth.application.CustomSuccessHandler;
import com.iceAge.server.auth.application.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomSuccessHandler customSuccessHandler;
    private final JWTUtil jwtUtil;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        //csrf 설정은 비활성화합니다.
        http
                .csrf(csrf -> csrf.disable());

        //Form Login은 비활성화합니다.
        http
                .formLogin(form -> form.disable());

        //HTTP Basic 인증은 비활성화합니다.
        http
                .httpBasic(httpBasic -> httpBasic.disable());

        //oatuh2 인증 사용
        http
                .oauth2Login((oauth2) -> oauth2
                                .userInfoEndpoint((userInfoEndpointConfig ->
                                        userInfoEndpointConfig.userService(customOAuth2UserService)))
                        .successHandler(customSuccessHandler));

        // 세션 설정 : STATELESS (상태 비저장)
        http
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        //경로별 인가 작업
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/","/api/v1/auth").permitAll()
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}
