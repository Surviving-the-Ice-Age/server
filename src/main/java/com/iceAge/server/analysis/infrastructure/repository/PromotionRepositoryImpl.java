package com.iceAge.server.analysis.infrastructure.repository;

import com.iceAge.server.analysis.domain.model.Promotion;
import com.iceAge.server.analysis.domain.repository.PromotionRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PromotionRepositoryImpl implements PromotionRepository {
  private final JpaPromotionRepository jpaPromotionRepository;

  @Override
  public Promotion save(Promotion promotion){
    return jpaPromotionRepository.save(promotion);
  }

}
