package com.iceAge.server.analysis.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
public class DistrictAnalysisResponseDTO {
  private String genderResidence; //가장 많은 주거 인구(성별-나이)
  private String genderWorker; //가장 많은 노동 인구(성별-나이)
  private String floatingTime; //유동 인구가 많은 피크 타임
  private String floatingAge; //가장 많은 유동 인구(나이)
  private String areaAverage; //해당 상권 평균 매출


  public DistrictAnalysisResponseDTO(String genderResidence, String genderWorker, String floatingTime, String floatingAge, String areaAverage){
    this.genderResidence  = genderResidence;
    this.genderWorker = genderWorker;
    this.floatingTime = floatingTime;
    this.floatingAge = floatingAge;
    this.areaAverage = areaAverage;
  }

}
