package com.iceAge.server.instagram_post.application.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InstagramInsightResponseDto {

    private final int postId;

    private final long views;

    private final long likes;

    private final long commentsCount;

    private final long saved;

    private final String insightsStartDate;

    private final String insightsEndDate;

    private final List<String> comments;
}
