package com.iceAge.server.auth.application.dto;

import java.util.Map;
import lombok.RequiredArgsConstructor;

public class NaverResponse implements OAuth2Resposne{

    private final Map<String, Object> attributes;

    public NaverResponse(Map<String, Object> attributes) {
        this.attributes = (Map<String, Object>) attributes.get("response");
    }
    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getProviderId() {
        return attributes.get("id").toString(); // 고유 식별자
    }

    @Override
    public String getEmail() {
        return attributes.get("email").toString();
    }

    @Override
    public String getNickname() {
        return attributes.get("nickname").toString();
    }
}
