package com.iceAge.server.analysis.infrastructure.config;

import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
  @Value("${fastApi.url}")
  private String fastApiUrl;
  @Bean
  public WebClient webClient() {
    return WebClient.builder()
        .baseUrl(fastApiUrl) // FastAPI 기본 URL
        .defaultHeader("Content-Type", "application/json")
        .build();
  }

  @Bean
  public WebClient fastApiWebClient(){
    return WebClient.builder()
        .baseUrl(fastApiUrl) // base URL 설정
        .defaultHeader(HttpHeaders.ACCEPT, "application/json") // API 명세서 준수
        .build();
  }
}
