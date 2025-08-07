package com.iceAge.server.analysis.domain.repository;

import com.iceAge.server.analysis.domain.model.Promotion;

public interface PromotionRepository {
  Promotion save(Promotion promotion);
  Promotion findByIdAndUserId(long id, long user_id);
}
