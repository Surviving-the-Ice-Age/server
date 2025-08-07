package com.iceAge.server.instagram_post.application.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Instagram Comments API 응답을 파싱하기 위한 DTO
 */
@Getter
@NoArgsConstructor
public class InstagramCommentsApiResponseDto {

    private List<CommentData> data;

    @Getter
    @NoArgsConstructor
    public static class CommentData {
        private String timestamp;
        private String text;
        private String id;
    }
}