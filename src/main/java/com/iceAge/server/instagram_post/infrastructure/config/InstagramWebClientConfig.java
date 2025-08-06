package com.iceAge.server.instagram_post.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class InstagramWebClientConfig {

    @Bean("instagramWebClient")
    public WebClient instagramWebClient() {
        return WebClient.builder()
                .baseUrl("https://graph.facebook.com/v19.0")
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}