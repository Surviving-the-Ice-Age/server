package com.iceAge.server.analysis.presentation.controller;

import static com.iceAge.server.analysis.infrastructure.code.AnalysisCode.PROMOTION_SAVE_SUCCESS;

import com.iceAge.server.analysis.domain.service.PromotionDomainService;
import com.iceAge.server.analysis.presentation.dto.request.PromotionRequestDTO;
import com.iceAge.server.auth.application.dto.CustomOAuth2User;
import com.iceAge.server.global.response.ApiResponseData;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PromotionController {
  private final PromotionDomainService promotionDomainService;
  @PostMapping("/api/promotion")
  public ResponseEntity<ApiResponseData<String>> savePromotion(@RequestBody PromotionRequestDTO promotionRequestDTO, @AuthenticationPrincipal
  CustomOAuth2User customOAuth2User){
    promotionDomainService.savePromotion(promotionRequestDTO, customOAuth2User);
    return ResponseEntity.ok().body(ApiResponseData.of(PROMOTION_SAVE_SUCCESS.getCode(),
        PROMOTION_SAVE_SUCCESS.getMessage(), "홍보물을 성공적으로 저장하였습니다."));
  }
}
