package com.iceAge.server.instagram_post.presentation;

import com.iceAge.server.auth.application.dto.CustomOAuth2User;
import com.iceAge.server.global.response.ApiResponseData;
import com.iceAge.server.instagram_post.application.InstagramPostService;
import com.iceAge.server.instagram_post.application.dto.InstagramInsightResponseDto;
import com.iceAge.server.instagram_post.application.dto.InstagramPostRequestDto;
import com.iceAge.server.instagram_post.application.dto.InstagramPostResponseDto;
import com.iceAge.server.instagram_post.application.service.InstagramInsightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/instagram/posts")
@RequiredArgsConstructor
public class InstagramPostController {

    private final InstagramPostService instagramPostService;
    private final InstagramInsightService instagramInsightService;

    /**
     * 인스타그램 포스트 업로드
     */
    @PostMapping("/upload")
    public ResponseEntity<ApiResponseData<InstagramPostResponseDto>> uploadPost(
            @RequestBody InstagramPostRequestDto requestDto,
            @AuthenticationPrincipal CustomOAuth2User customOAuth2User) {

        InstagramPostResponseDto response = instagramPostService.uploadPost(requestDto, customOAuth2User);

        if ("SUCCESS".equals(response.getStatus())) {
            return ResponseEntity.ok(ApiResponseData.success(response));
        } else {
            return ResponseEntity.status(400).body(ApiResponseData.failure("ERROR", response.getMessage()));
        }
    }

    /**
     * 프로모션 ID에 연결된 인스타그램 게시글의 인사이트 요약 조회 (댓글 본문 제외)
     */
    @GetMapping("/insights/{promotionId}")
    public ResponseEntity<ApiResponseData<InstagramInsightResponseDto>> getInsightsSummary(
            @PathVariable("promotionId") long promotionId,
            @AuthenticationPrincipal CustomOAuth2User customOAuth2User) {
        InstagramInsightResponseDto response = instagramInsightService.getInsightsSummary(promotionId,
                customOAuth2User);
        return ResponseEntity.ok(ApiResponseData.success(response));
    }
}