package com.iceAge.server.analysis.infrastructure.external;

import static com.iceAge.server.analysis.infrastructure.code.AnalysisCode.DISTRICT_RESPONSE_ERROR;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iceAge.server.analysis.presentation.dto.request.CommentRequestDTO;
import com.iceAge.server.analysis.presentation.dto.response.AllResponseDTO;
import com.iceAge.server.analysis.presentation.dto.response.DistrictResponseDTO;
import com.iceAge.server.analysis.presentation.dto.response.LargeCommentResponseDTO;
import com.iceAge.server.analysis.presentation.dto.response.ScoreResponseDTO;
import com.iceAge.server.analysis.presentation.dto.response.SmallCommentResponseDTO;
import com.iceAge.server.analysis.presentation.dto.response.SummaryResponseDTO;
import com.iceAge.server.global.exception.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
@Description("상권에 대한 정보를 fastapi 서버로 부터 받아 온다.")
@RequiredArgsConstructor
@Slf4j
public class DistrictApiClient {

  private final WebClient fastApiWebClient;

  public Mono<String> getDistrictData(int districtCode, String path) {
    return fastApiWebClient.get()
        .uri(uriBuilder -> uriBuilder
            .path(path)
            .queryParam("trdar_cd", districtCode)
            .build())
        .retrieve()
        .bodyToMono(DistrictResponseDTO.class)
        .<String>handle((response, sink) -> {
          if (response.getInsight() == null || response.getInsight().isEmpty()) {
            sink.error(new BaseException("인사이트 생성 실패 : 응답에 인사이트가 없습니다."));
            return;
          }
          sink.next(response.getInsight());
        })
        .onErrorResume(e -> {
          if (e instanceof WebClientResponseException) {
            WebClientResponseException ex = (WebClientResponseException) e;
            if (ex.getStatusCode().is4xxClientError()) {
              return Mono.error(new BaseException("잘못된 요청: HTTP " + ex.getStatusCode()));
            }
          }
          return Mono.error(new BaseException(DISTRICT_RESPONSE_ERROR));
        });
  }

  public Mono<String> getAllData(int districtCode, String categoryCode, String path) {
    return fastApiWebClient.get()
        .uri(uriBuilder -> uriBuilder
            .path(path)
            .queryParam("trdar_cd", districtCode)
            .queryParam("induty_cd", categoryCode)
            .build())
        .retrieve()
        .bodyToMono(AllResponseDTO.class)
        .<String>handle((response, sink) -> {
          if (response.getInsight() == null || response.getInsight().isEmpty()) {
            sink.error(new BaseException("인사이트 생성 실패 : 응답에 인사이트가 없습니다."));
            return;
          }
          sink.next(response.getInsight());
        })
        .onErrorResume(e -> {
          if (e instanceof WebClientResponseException) {
            WebClientResponseException ex = (WebClientResponseException) e;
            if (ex.getStatusCode().is4xxClientError()) {
              return Mono.error(new BaseException("잘못된 요청: HTTP " + ex.getStatusCode()));
            }
          }
          return Mono.error(new BaseException(DISTRICT_RESPONSE_ERROR));
        });
  }

  public Mono<ScoreResponseDTO> getScoreData(int districtCode, String categoryCode, String path) {
    return fastApiWebClient.get()
        .uri(uriBuilder -> uriBuilder
            .path(path)
            .queryParam("trdar_cd", districtCode)
            .queryParam("induty_cd", categoryCode)
            .build())
        .retrieve()
        .bodyToMono(ScoreResponseDTO.class);
  }

  public Mono<LargeCommentResponseDTO> getLargeCommentData(CommentRequestDTO commentRequestDTO, String path) {
    return fastApiWebClient.post()
        .uri(uriBuilder -> uriBuilder
            .path(path)
            .build())
        .bodyValue(commentRequestDTO)
        .retrieve()
        .bodyToMono(LargeCommentResponseDTO.class);
  }

  public Mono<SmallCommentResponseDTO> getSmallCommentData(CommentRequestDTO commentRequestDTO, String path) {
    return fastApiWebClient.post()
        .uri(uriBuilder -> uriBuilder
            .path(path)
            .build())
        .bodyValue(commentRequestDTO)
        .retrieve()
        .bodyToMono(SmallCommentResponseDTO.class);
  }

  public Mono<SummaryResponseDTO> getSummaryData(int districtCode, String path) {
    return fastApiWebClient.get()
        .uri(uriBuilder -> uriBuilder
            .path(path)
            .queryParam("trdar_cd", districtCode)
            .build())
        .retrieve()
        .bodyToMono(SummaryResponseDTO.class);
  }

}