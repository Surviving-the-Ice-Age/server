package com.iceAge.server.auth.infrastructure.security;

import com.iceAge.server.auth.application.dto.CustomOAuth2User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JWTUtil jwtUtil;
    private static final int TOKEN_MAX_AGE = 60 * 60 * 60*60; // 쿠키의 유효 기간을 60시간*60으로 설정. 테스트용
    private final String REDIRECT_URL;

    public CustomSuccessHandler(JWTUtil jwtUtil, @Value("${app.login.redirect.url}") String redirectUrl) {
        this.jwtUtil = jwtUtil;
        this.REDIRECT_URL = redirectUrl;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        //OAuth2User
        CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();

        String username = customUserDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        String token = jwtUtil.createJwt(username, role, (long) TOKEN_MAX_AGE);

        response.addCookie(createCookie("Authorization", token));
        response.sendRedirect(REDIRECT_URL);
    }

    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(TOKEN_MAX_AGE); // 쿠키의 유효 기간을 60시간으로 설정
        cookie.setAttribute("SameSite", "None");
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }
}