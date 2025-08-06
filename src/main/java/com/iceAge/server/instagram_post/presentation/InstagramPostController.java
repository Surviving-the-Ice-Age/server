package com.iceAge.server.instagram_post.presentation;

import com.iceAge.server.global.response.ApiResponseData;
import com.iceAge.server.global.response.CommonCode;
import com.iceAge.server.instagram_post.application.InstagramPostService;
import com.iceAge.server.instagram_post.application.dto.InstagramPostRequestDto;
import com.iceAge.server.instagram_post.application.dto.InstagramPostResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
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
            @RequestBody InstagramPostRequestDto requestDto) {

        log.info("인스타그램 포스트 업로드 요청: 이미지 {}개", requestDto.getImageUrls().size());

        InstagramPostResponseDto response = instagramPostService.uploadPost(requestDto);

        if ("SUCCESS".equals(response.getStatus())) {
            return ResponseEntity.ok(ApiResponseData.success(response));
        } else {
            return ResponseEntity.status(400).body(ApiResponseData.failure("ERROR", response.getMessage()));
        }
    }

    /**
     * 테스트용 엔드포인트 - 이미지 컨테이너 생성만 테스트
     */
    @PostMapping("/test/container")
    public ResponseEntity<ApiResponseData<String>> testCreateContainer() {
        List<String> testImageUrls = List.of(
                "https://img7.yna.co.kr/etc/inner/KR/2020/05/26/AKR20200526116600030_03_i_P4.jpg");

        InstagramPostRequestDto requestDto = new InstagramPostRequestDto(testImageUrls, "테스트 포스트");

        try {
            InstagramPostResponseDto response = instagramPostService.uploadPost(requestDto);
            return ResponseEntity.ok(ApiResponseData.success("테스트 완료: " + response.getPostId()));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(ApiResponseData.failure("ERROR", "테스트 실패: " + e.getMessage()));
        }
    }
}