package com.iceAge.server.instagram_post.application.dto;

import java.util.Optional;
import lombok.Builder;
import lombok.Getter;

@Getter
public class InstagramPostResponseDto {

    private final Optional<String> postId;
    private final String status;
    private final String message;

    @Builder
    private InstagramPostResponseDto(String postId, String status, String message) {
        this.postId = Optional.ofNullable(postId);
        this.status = status;
        this.message = message;
    }

    public static InstagramPostResponseDto of(String postId, String message) {
        String status = postId != null ? "SUCCESS" : "FAILED";
        return InstagramPostResponseDto.builder()
                .postId(postId)
                .status(status)
                .message(message)
                .build();
    }

    public String getPostId() {
        return postId.orElse(null);
    }
}