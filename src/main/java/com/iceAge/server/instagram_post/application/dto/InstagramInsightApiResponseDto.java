package com.iceAge.server.instagram_post.application.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Instagram Insights API 응답을 파싱하기 위한 DTO
 */
@Getter
@NoArgsConstructor
public class InstagramInsightApiResponseDto {

    private List<InsightData> data;

    @Getter
    @NoArgsConstructor
    public static class InsightData {
        private String name;
        private String period;
        private List<ValueData> values;
        private String title;
        private String description;
        private String id;
    }

    @Getter
    @NoArgsConstructor
    public static class ValueData {
        private long value;
    }
}