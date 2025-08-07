package com.iceAge.server.analysis.presentation.dto.request;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PromotionRequestDTO {
  private final String promotion;
  private final List<String> images;
}
