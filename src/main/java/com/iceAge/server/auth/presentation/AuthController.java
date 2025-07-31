package com.iceAge.server.auth.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    @GetMapping("/api/v1/auth")
    public String authCheck(@AuthenticationPrincipal OAuth2User user) {
        return "✅ 로그인 성공: " + user.getAttribute("name");
    }
}
