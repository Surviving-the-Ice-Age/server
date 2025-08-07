package com.iceAge.server.analysis.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class AllAnalysisResponseDTO {
  private String salesGender; // 해당 상권/업종에서 비중이 높은 성별
  private String salesTime; // 해당 상권/업종에서 매출이 가장 높은 시간대
  private String salesAge; // 해당 상권/업종에서 매출 비중이 높은 연령
  private String salesMonthly; // 해당 상권/업종 월 평균 매출 & 증감 수치 (전분기, 전년 동분기 대비)

  private String storeCount; // 해당 상권/업종 최근 분기 점포 수 & 증감 수치 (전분기, 전년 동분기 대비)
  private String storeOpen; // 해당 상권/업종 최근 점포 개업 현황 (해당 상권, 해당 업종)
  private String storeClose; // 해당 상권/업종 최근 점포 폐업 현황 (해당 상권, 해당 업종)
  private String storeFranchise; // 해당 상권/업종 프랜차이즈 비율 (해당 상권, 해당 업종)
}
