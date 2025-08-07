package com.iceAge.server.instagram_post.infrastructure.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 인스타그램 인사이트 관련 설정
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "instagram.insight")
public class InstagramInsightConfig {

    /**
     * 인사이트 조회 기간 (일 단위, 기본값: 30일)
     */
    private int insightPeriodDays = 30;

    /**
     * 스케줄러 활성화 여부 (기본값: true)
     */
    private boolean schedulerEnabled = true;

    /**
     * API 호출 간격 (밀리초, 기본값: 1000ms = 1초)
     */
    private long apiCallIntervalMs = 1000;
}