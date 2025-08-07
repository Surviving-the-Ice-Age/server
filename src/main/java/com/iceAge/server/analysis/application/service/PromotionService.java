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
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Transient;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PromotionService implements PromotionDomainService {
  private final PromotionRepository promotionRepository;
  private final UserRepository userRepository;

  @Override
  public void savePromotion(PromotionRequestDTO promotionRequestDTO, @AuthenticationPrincipal CustomOAuth2User customOAuth2User) {
    User user = userRepository.findByUsername(customOAuth2User.getUsername()).orElseThrow(() -> new BaseException(USER_NOT_FOUND));

    Promotion promotion =  promotionRepository.save(new Promotion(promotionRequestDTO));

    //홍보게시물이 추가될때 마다 업데이트
    user.addPromotionList(promotion);

    userRepository.save(user);
  }
}
