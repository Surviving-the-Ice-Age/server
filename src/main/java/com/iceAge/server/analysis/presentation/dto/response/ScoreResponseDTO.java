package com.iceAge.server.analysis.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ScoreResponseDTO {
  @JsonProperty("유동인구")
  private long floatingPopulation; // 상권코드

  @JsonProperty("동종업종_점포수")
  private String sameStoreCount; // 인사이트

  @JsonProperty("동종업종_평균매출")
  private String sameStoreAverageSales; // 이미지URL

  @JsonProperty("상권_평균매출")
  private String storeAverageSales; // 이미지URL

  @JsonProperty("일방문자수")
  private String dailyVisitors; // 이미지URL

  @JsonProperty("최종점수")
  private String finalScore; // 이미지URL
}
