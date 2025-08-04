package com.iceAge.server.analysis.infrastructure.external;

import com.iceAge.server.analysis.presentation.dto.request.ImageRequest;
import com.iceAge.server.analysis.presentation.dto.request.ImageRequestDTO;
import com.iceAge.server.analysis.presentation.dto.response.GetImageDTO;
import com.iceAge.server.global.exception.BaseException;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class ImagenApiClient {
  @Value("${image.api-url}")
  private String API_URL;

  @Value("${gemini.api-key}")
  private String API_KEY;

  public Mono<List<String>> generateImage(ImageRequestDTO imageRequestDTO) {
    WebClient webClient = WebClient.builder()
        .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(10 * 1024 * 1024)) // 10MB로 증가
        .build();
    ImageRequest imageRequest = new ImageRequest(imageRequestDTO);

    // API 호출 및 DTO로 변환
    return webClient.post()
        .uri(API_URL)
        .header("x-goog-api-key", API_KEY)
        .header("Content-Type", "application/json")
        .bodyValue(imageRequest)
        .retrieve()
        .bodyToMono(GetImageDTO.class)
        .map(response -> {
          if (response.getImages() == null || response.getImages().isEmpty()) {
            throw new BaseException("이미지 생성 실패: 응답에 이미지가 없습니다.");
          }
          return response.getImages();
        })
        .onErrorMap(e -> new RuntimeException("Imagen API 호출 실패: " + e.getMessage()));
  }

}