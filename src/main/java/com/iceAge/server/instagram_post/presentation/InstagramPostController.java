package com.iceAge.server.instagram_post.presentation;

import com.iceAge.server.auth.application.dto.CustomOAuth2User;
import com.iceAge.server.global.response.ApiResponseData;
import com.iceAge.server.global.response.CommonCode;
import com.iceAge.server.instagram_post.application.InstagramPostService;
import com.iceAge.server.instagram_post.application.dto.InstagramPostRequestDto;
import com.iceAge.server.instagram_post.application.dto.InstagramPostResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/instagram/posts")
@RequiredArgsConstructor
public class InstagramPostController {

    private final InstagramPostService instagramPostService;

    /**
     * 인스타그램 포스트 업로드
     */
    @PostMapping("/upload")
    public ResponseEntity<ApiResponseData<InstagramPostResponseDto>> uploadPost(
        @RequestBody InstagramPostRequestDto requestDto, @AuthenticationPrincipal CustomOAuth2User customOAuth2User) {

        InstagramPostResponseDto response = instagramPostService.uploadPost(requestDto, customOAuth2User);

        if ("SUCCESS".equals(response.getStatus())) {
            return ResponseEntity.ok(ApiResponseData.success(response));
        } else {
            return ResponseEntity.status(400).body(ApiResponseData.failure("ERROR", response.getMessage()));
        }
    }

}