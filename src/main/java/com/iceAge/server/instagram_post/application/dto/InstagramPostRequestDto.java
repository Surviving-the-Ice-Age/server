package com.iceAge.server.instagram_post.application.dto;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class InstagramPostRequestDto {
    private final long promotion_id;
}