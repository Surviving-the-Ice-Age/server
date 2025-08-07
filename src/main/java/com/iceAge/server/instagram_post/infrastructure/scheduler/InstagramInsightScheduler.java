package com.iceAge.server.instagram_post.infrastructure.scheduler;

import com.iceAge.server.instagram_post.application.service.InstagramInsightService;
import com.iceAge.server.instagram_post.infrastructure.config.InstagramInsightConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 인스타그램 인사이트 데이터를 주기적으로 업데이트하는 스케줄러
 */
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "instagram.insight.scheduler-enabled", havingValue = "true", matchIfMissing = true)
public class InstagramInsightScheduler {

    private final InstagramInsightService instagramInsightService;
    private final InstagramInsightConfig insightConfig;

    /**
     * 매 시간마다 인스타그램 포스트 인사이트를 업데이트합니다.
     * cron 표현식: 0 0 * * * * (매 시간 정각)
     */
    @Scheduled(cron = "0 0 * * * *")
//    @Scheduled(cron = "0 * * * * *")
    public void updateInstagramInsights() {
        log.info("=== 인스타그램 인사이트 스케줄러 시작 ===");

        try {
            instagramInsightService.updateAllPostsInsights();
            log.info("=== 인스타그램 인사이트 스케줄러 완료 ===");
        } catch (Exception e) {
            log.error("=== 인스타그램 인사이트 스케줄러 실행 중 오류 발생 ===", e);
        }
    }

    /**
     * 테스트용 - 5분마다 실행 (개발/테스트 환경에서만 사용)
     * 운영 환경에서는 이 메서드를 제거하거나 주석 처리하세요.
     */
    // @Scheduled(fixedRate = 300000) // 5분 = 300,000ms
    public void updateInstagramInsightsForTest() {
        log.info("=== [테스트] 인스타그램 인사이트 스케줄러 시작 ===");

        try {
            instagramInsightService.updateAllPostsInsights();
            log.info("=== [테스트] 인스타그램 인사이트 스케줄러 완료 ===");
        } catch (Exception e) {
            log.error("=== [테스트] 인스타그램 인사이트 스케줄러 실행 중 오류 발생 ===", e);
        }
    }
}