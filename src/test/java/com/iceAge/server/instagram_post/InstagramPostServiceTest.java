package com.iceAge.server.instagram_post;

import com.iceAge.server.instagram_post.application.InstagramPostService;
import com.iceAge.server.instagram_post.application.dto.InstagramPostRequestDto;
import com.iceAge.server.instagram_post.application.dto.InstagramPostResponseDto;
import com.iceAge.server.instagram_post.domain.model.InstagramPost;
import com.iceAge.server.instagram_post.domain.repository.InstagramPostRepository;
import com.iceAge.server.instagram_post.domain.service.InstagramApiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("InstagramPostService 단위 테스트")
class InstagramPostServiceTest {

    @Mock
    private InstagramPostRepository instagramPostRepository;

    @Mock
    private InstagramApiService instagramApiService;

    @InjectMocks
    private InstagramPostService instagramPostService;

    private InstagramPostRequestDto requestDto;
    private List<String> mockContainerIds;
    private String mockPostId;
    private InstagramPost mockInstagramPost;

    @BeforeEach
    void setUp() {
        // 테스트 데이터 설정
        List<String> imageUrls = List.of("https://example.com/image1.jpg");
        String caption = "테스트 캡션";
        requestDto = new InstagramPostRequestDto(imageUrls, caption);

        mockContainerIds = List.of("container_123");
        mockPostId = "post_456";
        mockInstagramPost = InstagramPost.builder()
                .postId(mockPostId)
                .views(0L)
                .likes(0L)
                .comments(0L)
                .saved(0L)
                .build();
    }

    @Test
    @DisplayName("성공적인 포스트 업로드 테스트")
    void testSuccessfulUploadPost() {
        // given
        when(instagramApiService.createImageContainer(anyList())).thenReturn(mockContainerIds);
        when(instagramApiService.createPostContainer(anyList(), anyString())).thenReturn("creation_123");
        when(instagramApiService.publishPost(anyString())).thenReturn(mockPostId);
        when(instagramPostRepository.save(any(InstagramPost.class))).thenReturn(mockInstagramPost);

        // when
        InstagramPostResponseDto response = instagramPostService.uploadPost(requestDto);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo("SUCCESS");
        assertThat(response.getPostId()).isEqualTo(mockPostId);
        assertThat(response.getMessage()).contains("성공");
    }

    @Test
    @DisplayName("API 호출 실패 시 에러 처리 테스트")
    void testUploadPostWithApiFailure() {
        // given
        when(instagramApiService.createImageContainer(anyList()))
                .thenThrow(new RuntimeException("API 호출 실패"));

        // when
        InstagramPostResponseDto response = instagramPostService.uploadPost(requestDto);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo("FAILED");
        assertThat(response.getPostId()).isNull();
        assertThat(response.getMessage()).contains("실패");
    }

    @Test
    @DisplayName("여러 이미지로 포스트 업로드 테스트")
    void testUploadPostWithMultipleImages() {
        // given
        List<String> multipleImageUrls = List.of(
                "https://example.com/image1.jpg",
                "https://example.com/image2.jpg",
                "https://example.com/image3.jpg");
        InstagramPostRequestDto multipleImageRequest = new InstagramPostRequestDto(multipleImageUrls, "멀티 이미지 테스트");

        List<String> multipleContainerIds = List.of("container_1", "container_2", "container_3");

        when(instagramApiService.createImageContainer(anyList())).thenReturn(multipleContainerIds);
        when(instagramApiService.createPostContainer(anyList(), anyString())).thenReturn("creation_456");
        when(instagramApiService.publishPost(anyString())).thenReturn(mockPostId);
        when(instagramPostRepository.save(any(InstagramPost.class))).thenReturn(mockInstagramPost);

        // when
        InstagramPostResponseDto response = instagramPostService.uploadPost(multipleImageRequest);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo("SUCCESS");
        assertThat(response.getPostId()).isEqualTo(mockPostId);
    }

    @Test
    @DisplayName("긴 캡션으로 포스트 업로드 테스트")
    void testUploadPostWithLongCaption() {
        // given
        String longCaption = "이것은 매우 긴 캡션입니다. ".repeat(10) + "테스트 시간: " + System.currentTimeMillis();
        InstagramPostRequestDto longCaptionRequest = new InstagramPostRequestDto(
                List.of("https://example.com/image1.jpg"),
                longCaption);

        when(instagramApiService.createImageContainer(anyList())).thenReturn(mockContainerIds);
        when(instagramApiService.createPostContainer(anyList(), anyString())).thenReturn("creation_789");
        when(instagramApiService.publishPost(anyString())).thenReturn(mockPostId);
        when(instagramPostRepository.save(any(InstagramPost.class))).thenReturn(mockInstagramPost);

        // when
        InstagramPostResponseDto response = instagramPostService.uploadPost(longCaptionRequest);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo("SUCCESS");
        assertThat(response.getPostId()).isEqualTo(mockPostId);
    }
}