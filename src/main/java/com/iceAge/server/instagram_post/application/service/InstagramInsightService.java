package com.iceAge.server.instagram_post.application.service;

import com.iceAge.server.auth.application.dto.CustomOAuth2User;
import com.iceAge.server.instagram_post.application.dto.InstagramCommentsApiResponseDto;
import com.iceAge.server.instagram_post.application.dto.InstagramInsightApiResponseDto;
import com.iceAge.server.instagram_post.application.dto.InstagramInsightResponseDto;
import com.iceAge.server.instagram_post.domain.model.InstagramPost;
import com.iceAge.server.instagram_post.domain.model.InstagramPostComment;
import com.iceAge.server.instagram_post.domain.service.InstagramApiService;
import com.iceAge.server.instagram_post.infrastructure.repository.InstagramPostCommentJpaRepository;
import com.iceAge.server.instagram_post.infrastructure.repository.InstagramPostJpaRepository;
import com.iceAge.server.analysis.infrastructure.repository.JpaPromotionRepository;
import com.iceAge.server.analysis.domain.model.Promotion;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InstagramInsightService {

    private final InstagramApiService instagramApiService;
    private final InstagramPostJpaRepository instagramPostJpaRepository;
    private final InstagramPostCommentJpaRepository instagramPostCommentJpaRepository;
    private final JpaPromotionRepository jpaPromotionRepository;

    /**
     * 프로모션 ID에 연결된 게시글의 인사이트 요약을 조회합니다.
     */
    public InstagramInsightResponseDto getInsightsSummary(long promotionId, CustomOAuth2User customOAuth2User) {
        // TODO: 사용자 권한 검증(프로모션 소유자 확인) 필요 시 User 조회 추가
        Promotion promotion = jpaPromotionRepository.findById(promotionId)
            .orElseThrow(() -> new IllegalArgumentException("프로모션을 찾을 수 없습니다: " + promotionId));

        InstagramPost instagramPost = Optional.ofNullable(promotion.getInstagramPost())
            .orElseThrow(() -> new IllegalStateException("프로모션에 연결된 인스타그램 포스트가 없습니다: " + promotionId));

        int postIdAsInt;
        try {
            postIdAsInt = Integer.parseInt(instagramPost.getPostId());
        } catch (NumberFormatException e) {
            postIdAsInt = 0;
        }

        long views = Optional.ofNullable(instagramPost.getViews()).orElse(0L);
        long likes = Optional.ofNullable(instagramPost.getLikes()).orElse(0L);
        long comments = Optional.ofNullable(instagramPost.getCommentsCount()).orElse(0L);
        long saved = Optional.ofNullable(instagramPost.getSaved()).orElse(0L);

        String startDate = Optional.ofNullable(instagramPost.getInsightsStartDate()).map(Object::toString).orElse(null);
        String endDate = Optional.ofNullable(instagramPost.getInsightsEndDate()).map(Object::toString).orElse(null);

        return InstagramInsightResponseDto.builder()
            .postId(postIdAsInt)
            .views(views)
            .likes(likes)
            .commentsCount(comments)
            .saved(saved)
            .insightsStartDate(startDate)
            .insightsEndDate(endDate)
            .build();
    }

    /**
     * 인사이트 기간 내의 모든 게시글들의 인사이트 정보를 업데이트합니다.
     */
    @Transactional
    public void updateAllPostsInsights() {
        log.info("모든 인스타그램 포스트 인사이트 업데이트 시작");

        // 모든 게시글 조회 (인사이트 기간은 Instagram API에서 처리)
        List<InstagramPost> postsInInsightPeriod = instagramPostJpaRepository.findAll();

        log.info("인사이트 업데이트 대상 게시글 수: {}", postsInInsightPeriod.size());

        int successCount = 0;
        int failCount = 0;

        for (InstagramPost post : postsInInsightPeriod) {
            try {
                updateSinglePostInsights(post.getPostId());
                successCount++;
                log.debug("게시글 인사이트 업데이트 성공: postId={}", post.getPostId());
            } catch (Exception e) {
                failCount++;
                log.error("게시글 인사이트 업데이트 실패: postId={}", post.getPostId(), e);
            }
        }

        log.info("모든 인스타그램 포스트 인사이트 업데이트 완료 - 성공: {}, 실패: {}", successCount, failCount);
    }

    /**
     * 단일 포스트의 인사이트를 업데이트합니다.
     */
    private InstagramPost updateSinglePostInsights(String postId) {
        log.info("인스타그램 포스트 인사이트 업데이트 시작: postId={}", postId);

        // Instagram API로부터 인사이트 데이터 조회
        InstagramInsightApiResponseDto insightResponse = instagramApiService.getPostInsights(postId);

        // Instagram API로부터 댓글 데이터 조회
        InstagramCommentsApiResponseDto commentsResponse = instagramApiService.getPostComments(postId);

        // 데이터베이스에서 기존 포스트 조회
        InstagramPost instagramPost = instagramPostJpaRepository.findByPostId(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 포스트를 찾을 수 없습니다: " + postId));

        // 인사이트 데이터 파싱 및 업데이트
        updatePostInsights(instagramPost, insightResponse);

        // 댓글 데이터 처리
        updatePostComments(instagramPost, commentsResponse);

        return instagramPost;
    }

    private void updatePostInsights(InstagramPost instagramPost, InstagramInsightApiResponseDto insightResponse) {
        Map<String, Long> insightMap = insightResponse.getData().stream()
                .collect(Collectors.toMap(
                        InstagramInsightApiResponseDto.InsightData::getName,
                        data -> data.getValues().get(0).getValue()));

        Long views = insightMap.getOrDefault("views", 0L);
        Long likes = insightMap.getOrDefault("likes", 0L);
        Long comments = insightMap.getOrDefault("comments", 0L);
        Long saved = insightMap.getOrDefault("saved", 0L);

        instagramPost.updateInstagramPostFromInsights(views, likes, comments, saved);

        log.info("포스트 인사이트 업데이트 완료: postId={}, views={}, likes={}, comments={}, saved={}",
                instagramPost.getPostId(), views, likes, comments, saved);
    }

    private void updatePostComments(InstagramPost instagramPost, InstagramCommentsApiResponseDto commentsResponse) {
        if (commentsResponse.getData() == null || commentsResponse.getData().isEmpty()) {
            log.info("조회된 댓글이 없습니다: postId={}", instagramPost.getPostId());
            return;
        }

        // 기존 댓글 ID 목록 조회
        List<String> existingCommentIds = instagramPostCommentJpaRepository
                .findByInstagramPost(instagramPost)
                .stream()
                .map(InstagramPostComment::getCommentId)
                .toList();

        // 새로운 댓글만 필터링하여 저장
        List<InstagramPostComment> newComments = commentsResponse.getData().stream()
                .filter(commentData -> !existingCommentIds.contains(commentData.getId()))
                .map(commentData -> InstagramPostComment.createComment(
                        commentData.getId(),
                        instagramPost,
                        commentData.getText()))
                .toList();

        if (!newComments.isEmpty()) {
            instagramPostCommentJpaRepository.saveAll(newComments);
            log.info("새로운 댓글 저장 완료: postId={}, 새 댓글 수={}",
                    instagramPost.getPostId(), newComments.size());
        } else {
            log.info("저장할 새로운 댓글이 없습니다: postId={}", instagramPost.getPostId());
        }
    }

}