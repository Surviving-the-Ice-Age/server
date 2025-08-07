package com.iceAge.server.instagram_post;

import com.iceAge.server.instagram_post.application.InstagramPostService;
import com.iceAge.server.instagram_post.application.dto.InstagramPostRequestDto;
import com.iceAge.server.instagram_post.application.dto.InstagramPostResponseDto;
import com.iceAge.server.instagram_post.domain.service.InstagramApiService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {
        "instagram.app-id=${INSTAGRAM_APP_ID:17841476257793092}",
        "instagram.access-token=${INSTAGRAM_ACCESS_TOKEN:test-token}"
})
@DisplayName("인스타그램 포스트 통합 테스트")
class InstagramPostIntegrationTest {

    @Autowired
    private InstagramPostService instagramPostService;

    @Autowired
    private InstagramApiService instagramApiService;

    @Test
    @DisplayName("이미지 컨테이너 생성 테스트")
    void testCreateImageContainer() {
        // given
        List<String> imageUrls = List.of(
                "https://img7.yna.co.kr/etc/inner/KR/2020/05/26/AKR20200526116600030_03_i_P4.jpg"
        );

        // when
        List<String> containerIds = instagramApiService.createImageContainer(imageUrls);

        // then
        assertThat(containerIds).isNotEmpty();
        assertThat(containerIds).hasSize(1);
        assertThat(containerIds.get(0)).isNotNull();
        
        System.out.println("생성된 컨테이너 ID: " + containerIds.get(0));
    }

    @Test
    @DisplayName("인스타그램 포스트 업로드 전체 테스트")
    void testUploadPost() {
        // given
        List<String> imageUrls = List.of(
                "https://img7.yna.co.kr/etc/inner/KR/2020/05/26/AKR20200526116600030_03_i_P4.jpg"
        );
        String caption = "테스트 포스트 - " + System.currentTimeMillis();
        
        InstagramPostRequestDto requestDto = new InstagramPostRequestDto(imageUrls, caption);

        // when
        InstagramPostResponseDto response = instagramPostService.uploadPost(requestDto);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo("SUCCESS");
        assertThat(response.getPostId()).isNotNull();
        assertThat(response.getMessage()).contains("성공");
        
        System.out.println("업로드된 포스트 ID: " + response.getPostId());
        System.out.println("응답 메시지: " + response.getMessage());
    }

    @Test
    @DisplayName("여러 이미지로 포스트 업로드 테스트")
    void testUploadPostWithMultipleImages() {
        // given
        List<String> imageUrls = List.of(
                "https://img7.yna.co.kr/etc/inner/KR/2020/05/26/AKR20200526116600030_03_i_P4.jpg",
                "https://img7.yna.co.kr/etc/inner/KR/2020/05/26/AKR20200526116600030_03_i_P4.jpg"
        );
        String caption = "멀티 이미지 테스트 포스트 - " + System.currentTimeMillis();
        
        InstagramPostRequestDto requestDto = new InstagramPostRequestDto(imageUrls, caption);

        // when
        InstagramPostResponseDto response = instagramPostService.uploadPost(requestDto);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo("SUCCESS");
        assertThat(response.getPostId()).isNotNull();
        
        System.out.println("멀티 이미지 포스트 ID: " + response.getPostId());
    }

    @Test
    @DisplayName("긴 캡션으로 포스트 업로드 테스트")
    void testUploadPostWithLongCaption() {
        // given
        List<String> imageUrls = List.of(
                "https://img7.yna.co.kr/etc/inner/KR/2020/05/26/AKR20200526116600030_03_i_P4.jpg"
        );
        String longCaption = "이것은 매우 긴 캡션입니다. " +
                "인스타그램 API를 통해 자동으로 업로드되는 테스트 포스트입니다. " +
                "DDD 구조로 구현된 서비스에서 생성되었습니다. " +
                "테스트 시간: " + System.currentTimeMillis();
        
        InstagramPostRequestDto requestDto = new InstagramPostRequestDto(imageUrls, longCaption);

        // when
        InstagramPostResponseDto response = instagramPostService.uploadPost(requestDto);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo("SUCCESS");
        assertThat(response.getPostId()).isNotNull();
        
        System.out.println("긴 캡션 포스트 ID: " + response.getPostId());
    }
} 