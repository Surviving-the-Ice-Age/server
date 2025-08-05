package com.iceAge.server.analysis.domain.service;

import com.iceAge.server.analysis.domain.model.Promotion;
import com.iceAge.server.analysis.presentation.dto.request.PromotionRequestDTO;
import com.iceAge.server.auth.application.dto.CustomOAuth2User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface PromotionDomainService {
  void savePromotion(PromotionRequestDTO promotionRequestDTO, @AuthenticationPrincipal CustomOAuth2User customOAuth2User);
}
