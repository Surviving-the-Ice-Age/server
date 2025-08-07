package com.iceAge.server.instagram_post.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InstagramConfig {

    @Value("${instagram.app-id}")
    private String appId;

    @Value("${instagram.access-token}")
    private String accessToken;

    public String getAppId() {
        return appId;
    }

    public String getAccessToken() {
        return accessToken;
    }
}