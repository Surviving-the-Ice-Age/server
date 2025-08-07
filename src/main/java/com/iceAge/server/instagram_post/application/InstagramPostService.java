package com.iceAge.server.instagram_post.application;

import com.iceAge.server.analysis.domain.model.Promotion;
import com.iceAge.server.analysis.domain.repository.PromotionRepository;
import com.iceAge.server.auth.application.dto.CustomOAuth2User;
import com.iceAge.server.auth.domain.model.User;
import com.iceAge.server.auth.infrastructure.repository.UserRepository;
import com.iceAge.server.instagram_post.application.dto.InstagramPostRequestDto;
import com.iceAge.server.instagram_post.application.dto.InstagramPostResponseDto;
import com.iceAge.server.instagram_post.domain.model.InstagramPost;
import com.iceAge.server.instagram_post.domain.repository.InstagramPostRepository;
import com.iceAge.server.instagram_post.domain.service.InstagramApiService;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class InstagramPostService {

    private final InstagramPostRepository instagramPostRepository;
    private final InstagramApiService instagramApiService;
    private final PromotionRepository promotionRepository;
    private final UserRepository userRepository;
    private static final int INSIGHTS_DURATION_DAYS = 7; // 인사이트 조회 기간 (7일)
    /**
     * 인스타그램 포스트 업로드
     */
    public InstagramPostResponseDto uploadPost(InstagramPostRequestDto requestDto, @AuthenticationPrincipal
                                               CustomOAuth2User customOAuth2User) {

        User user = userRepository.findByUsername(customOAuth2User.getUsername()).get();
        Promotion promotion = promotionRepository.findByIdAndUserId(requestDto.getPromotion_id(),user.getId());

        log.info("인스타그램 포스트 업로드 시작: 이미지 {}개, 캡션: {}",
            promotion.getImgLinks().size(), promotion.getPromotionText());

        try {
            // 1. 이미지 컨테이너 생성
            List<String> containerIds = instagramApiService.createImageContainer(promotion.getImgLinks());
            log.info("이미지 컨테이너 생성 완료: {}개", containerIds.size());

            // 2. 게시글 컨테이너 생성
            String creationId = instagramApiService.createPostContainer(containerIds, promotion.getPromotionText());
            log.info("게시글 컨테이너 생성 완료: {}", creationId);

            // 3. 포스트 게시
            String postId = instagramApiService.publishPost(creationId);
            log.info("인스타그램 포스트 게시 완료: {}", postId);

            // 4. 포스트 정보 저장
            // todo - 유저 정보와 연결 필요
            InstagramPost instagramPost = InstagramPost.
                    builder()
                    .postId(postId)
                    .insightsStartDate(LocalDate.now())
                    .insightsEndDate(LocalDate.now().plusDays(INSIGHTS_DURATION_DAYS))
                    .build();

            instagramPost.addPromotion(promotion); // 인스타그램 게시물과 연관관계 매핑

            instagramPostRepository.save(instagramPost);

            return InstagramPostResponseDto.of(postId, "포스트 업로드 성공");

        } catch (Exception e) {
            log.error("인스타그램 포스트 업로드 실패", e);
            return InstagramPostResponseDto.of(null, "포스트 업로드에 실패: " + e.getMessage());
        }
    }
}
