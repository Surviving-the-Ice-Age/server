package com.iceAge.server.instagram_post.domain.service;

import com.iceAge.server.instagram_post.application.dto.InstagramCommentsApiResponseDto;
import com.iceAge.server.instagram_post.application.dto.InstagramInsightApiResponseDto;
import java.util.List;

/**
 * Instagram API 연동을 위한 도메인 서비스 인터페이스
 */
public interface InstagramApiService {

    /**
     * 이미지 컨테이너들을 생성합니다.
     */
    List<String> createImageContainer(List<String> imageUrls);

    /**
     * 게시글 컨테이너를 생성합니다.
     */
    String createPostContainer(List<String> containerIds, String caption);

    /**
     * 게시글을 게시합니다.
     */
    String publishPost(String creationId);

    /**
     * 게시글의 인사이트 정보를 조회합니다.
     */
    InstagramInsightApiResponseDto getPostInsights(String postId);

    /**
     * 게시글의 댓글을 조회합니다.
     */
    InstagramCommentsApiResponseDto getPostComments(String postId);
}