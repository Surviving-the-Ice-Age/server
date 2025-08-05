package com.iceAge.server.analysis.application.service;

import com.iceAge.server.analysis.domain.service.DataGetTestDomainService;
import com.iceAge.server.analysis.infrastructure.config.WebClientConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DataGetTestService implements DataGetTestDomainService {

  private final WebClient webClient;
  @Override
  public Mono<String> getDataTest() {
    return webClient.get()
        .uri("/api/example")
        .retrieve()
        .bodyToMono(String.class); // FastAPI 응답을 Mono<String>으로 변환
  }

  @Override
  public Mono<String> getFastApi(String district){
    return webClient.get()
        .uri(uriBuilder -> uriBuilder
            .path("/api/resident/gender-age")
            .queryParam("trdar_cd", district)
            .build())
        .header("accept", "application/json")
        .retrieve()
        .bodyToMono(String.class);
  }
}
