package com.iceAge.server.analysis.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;

@Getter
public class ImageRequestDTO {
  //업종
  @NotNull
  private String category;

  //지역
  @NotNull
  private String region;

  //상권
  @NotNull
  private String district;

  //주요 메뉴
  @NotNull
  private String menu;

  //콘셉트
  @NotNull
  private String concept;

  //키워드
  @NotNull
  private String keyword;
}
