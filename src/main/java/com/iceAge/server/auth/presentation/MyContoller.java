package com.iceAge.server.auth.presentation;

import com.iceAge.server.auth.application.dto.CustomOAuth2User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyContoller {

    @GetMapping("/")
    @ResponseBody
    public String mainAPI(@AuthenticationPrincipal CustomOAuth2User customOAuth2User) {
        return "Welcome to IceAge Server!" + customOAuth2User.getUsername();
    }
}
