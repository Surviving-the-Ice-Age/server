package com.iceAge.server.instagram_post.domain.service;

import java.util.List;

public interface InstagramApiService {

    /**
     * 인스타그램 이미지 컨테이너 생성
     * 
     * @param imageUrls 업로드할 이미지 URL 목록
     * @return 생성된 컨테이너 ID 목록
     */
    List<String> createImageContainer(List<String> imageUrls);

    /**
     * 인스타그램 게시글 컨테이너 생성
     *
     * @param containerIds 이미지 컨테이너 ID 목록
     * @param caption      포스트 캡션
     * @return 생성된 게시글 컨테이너 ID
     */
    String createPostContainer(List<String> containerIds, String caption);

    /**
     * 인스타그램 포스트 게시
     *
     * @param creationId 게시글 컨테이너 ID
     * @return 게시된 포스트 ID
     */
    String publishPost(String creationId);
}