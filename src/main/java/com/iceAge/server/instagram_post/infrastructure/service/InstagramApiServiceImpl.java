package com.iceAge.server.instagram_post.infrastructure.service;

import com.iceAge.server.instagram_post.application.dto.InstagramCommentsApiResponseDto;
import com.iceAge.server.instagram_post.application.dto.InstagramInsightApiResponseDto;
import com.iceAge.server.instagram_post.domain.service.InstagramApiService;
import com.iceAge.server.instagram_post.infrastructure.config.InstagramConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class InstagramApiServiceImpl implements InstagramApiService {

    private final WebClient instagramWebClient;
    private final InstagramConfig config;

    @Override
    public List<String> createImageContainer(List<String> imageUrls) {
        log.info("이미지 컨테이너 생성 시작: {}개 이미지", imageUrls.size());

        return imageUrls.stream()
                .map(this::createSingleImageContainer)
                .toList();
    }

    @Override
    public String createPostContainer(List<String> containerIds, String caption) {
        log.info("인스타그램 게시글 컨테이너 생성 시작: {}개 이미지 컨테이너", containerIds.size());

        String url = String.format("/%s/media", config.getAppId());

        Map<String, Object> requestBody = Map.of(
                "media_type", "CAROUSEL",
                "children", containerIds,
                "caption", caption,
                "access_token", config.getAccessToken());

        return instagramWebClient.post()
                .uri(url)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    String id = (String) response.get("id");
                    log.info("게시글 컨테이너 생성 완료: {}", id);
                    return id;
                })
                .block();
    }

    @Override
    public String publishPost(String creationId) {
        log.info("인스타그램 포스트 게시 시작: creation_id={}", creationId);

        String url = String.format("/%s/media_publish", config.getAppId());

        Map<String, Object> requestBody = Map.of(
                "creation_id", creationId,
                "access_token", config.getAccessToken());

        return instagramWebClient.post()
                .uri(url)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    String id = (String) response.get("id");
                    log.info("포스트 게시 완료: {}", id);
                    return id;
                })
                .block();
    }

    @Override
    public InstagramInsightApiResponseDto getPostInsights(String postId) {
        log.info("인스타그램 포스트 인사이트 조회 시작: postId={}", postId);

        String url = String.format("/%s/insights", postId);

        return instagramWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(url)
                        .queryParam("access_token", config.getAccessToken())
                        .queryParam("metric", "views,likes,comments,saved")
                        .build())
                .retrieve()
                .bodyToMono(InstagramInsightApiResponseDto.class)
                .doOnSuccess(response -> log.info("인사이트 조회 완료: postId={}", postId))
                .doOnError(error -> log.error("인사이트 조회 실패: postId={}", postId, error))
                .block();
    }

    @Override
    public InstagramCommentsApiResponseDto getPostComments(String postId) {
        log.info("인스타그램 포스트 댓글 조회 시작: postId={}", postId);

        String url = String.format("/%s/comments", postId);

        return instagramWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(url)
                        .queryParam("access_token", config.getAccessToken())
                        .build())
                .retrieve()
                .bodyToMono(InstagramCommentsApiResponseDto.class)
                .doOnSuccess(response -> log.info("댓글 조회 완료: postId={}", postId))
                .doOnError(error -> log.error("댓글 조회 실패: postId={}", postId, error))
                .block();
    }

    private String createSingleImageContainer(String imageUrl) {
        log.debug("단일 이미지 컨테이너 생성: {}", imageUrl);

        String url = String.format("/%s/media", config.getAppId());

        Map<String, Object> requestBody = Map.of(
                "image_url", imageUrl,
                "is_carousel_item", true,
                "access_token", config.getAccessToken());

        return instagramWebClient.post()
                .uri(url)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    String id = (String) response.get("id");
                    log.debug("이미지 컨테이너 생성 완료: {}", id);
                    return id;
                })
                .block();
    }
}