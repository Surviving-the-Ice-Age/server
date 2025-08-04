package com.iceAge.server.global.config;

import com.iceAge.server.auth.infrastructure.security.CustomOAuth2UserService;
import com.iceAge.server.auth.infrastructure.security.CustomSuccessHandler;
import com.iceAge.server.auth.infrastructure.security.JWTFilter;
import com.iceAge.server.auth.infrastructure.security.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

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

        //JWT 인증 필터 추가
        http
                .addFilterBefore(new JWTFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

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
                        .requestMatchers("oauth2/authorization").permitAll()
                        .anyRequest().authenticated()
                );

        // CORS 설정

        http
                .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {

                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                        CorsConfiguration configuration = new CorsConfiguration();

                        configuration.setAllowedOrigins(
                                Arrays.asList(
                                        "http://localhost:3000",
                                        "https://localhost:3000",
                                        "https://ssgs-server.agong.store"
                                )
                        );
                        configuration.setAllowedMethods(Collections.singletonList("*"));
                        configuration.setAllowCredentials(true);
                        configuration.setAllowedHeaders(Collections.singletonList("*"));
                        configuration.setMaxAge(3600L);
                        configuration.setExposedHeaders(Arrays.asList("Set-Cookie", "Authorization"));


                        return configuration;
                    }
                }));


        return http.build();
    }
}
