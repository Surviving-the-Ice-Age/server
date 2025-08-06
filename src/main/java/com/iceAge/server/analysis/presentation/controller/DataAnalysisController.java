package com.iceAge.server.analysis.presentation.controller;

import static com.iceAge.server.analysis.infrastructure.code.AnalysisCode.ANALYSIS_SUCCESS;
import static com.iceAge.server.analysis.infrastructure.code.AnalysisCode.DISTRICT_ANALYSIS_SUCCESS;

import com.iceAge.server.analysis.domain.service.DataAnalysisDomainService;
import com.iceAge.server.analysis.presentation.dto.response.AllAnalysisResponseDTO;
import com.iceAge.server.analysis.presentation.dto.response.DistrictAnalysisResponseDTO;
import com.iceAge.server.global.response.ApiResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DataAnalysisController {
  private final DataAnalysisDomainService dataAnalysisDomainService;
  @GetMapping("/api/data/district")
  public ResponseEntity<ApiResponseData<DistrictAnalysisResponseDTO>> getDataAnalysis(@RequestParam(value = "districtCode") String districtCode){
    return ResponseEntity.ok().body(ApiResponseData.of(DISTRICT_ANALYSIS_SUCCESS.getCode(), DISTRICT_ANALYSIS_SUCCESS.getMessage(), dataAnalysisDomainService.getAllDistrictData(Integer.parseInt(districtCode))));
  }

  @GetMapping("/api/data/all")
  public ResponseEntity<ApiResponseData<AllAnalysisResponseDTO>> getAllDataAnalysis(@RequestParam(value = "districtCode") String districtCode,
                                                                                    @RequestParam(value = "categoryCode") String categoryCode){
    return ResponseEntity.ok().body(ApiResponseData.of(ANALYSIS_SUCCESS.getCode(), ANALYSIS_SUCCESS.getMessage(), dataAnalysisDomainService.getAllData(Integer.parseInt(districtCode), categoryCode)));
  }
}
