package com.iceAge.server.analysis.infrastructure.repository;

import com.iceAge.server.analysis.domain.model.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaPromotionRepository extends JpaRepository<Promotion, Long> {
  Promotion findByIdAndUserId(long id, long user_id);
}
