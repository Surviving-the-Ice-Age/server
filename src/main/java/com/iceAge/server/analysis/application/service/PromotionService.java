package com.iceAge.server.analysis.application.service;

import static com.iceAge.server.analysis.infrastructure.code.AnalysisCode.USER_NOT_FOUND;

import com.iceAge.server.analysis.domain.model.Promotion;
import com.iceAge.server.analysis.domain.repository.PromotionRepository;
import com.iceAge.server.analysis.domain.service.PromotionDomainService;
import com.iceAge.server.analysis.presentation.dto.request.PromotionRequestDTO;
import com.iceAge.server.auth.application.dto.CustomOAuth2User;
import com.iceAge.server.auth.domain.model.User;
import com.iceAge.server.auth.infrastructure.repository.UserRepository;
import com.iceAge.server.global.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PromotionService implements PromotionDomainService {
  private final PromotionRepository promotionRepository;
  private final UserRepository userRepository;

  @Override
  public void savePromotion(PromotionRequestDTO promotionRequestDTO, @AuthenticationPrincipal CustomOAuth2User customOAuth2User) {
    User user = userRepository.findByUsername(customOAuth2User.getUsername()).orElseThrow(() -> new BaseException(USER_NOT_FOUND));

    Promotion promotion =  promotionRepository.save(new Promotion(promotionRequestDTO));
    user.setPromotion(promotion);

    userRepository.save(user);
  }
}
