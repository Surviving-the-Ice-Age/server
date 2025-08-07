package com.iceAge.server.analysis.presentation.controller;

import static com.iceAge.server.analysis.infrastructure.code.AnalysisCode.ANALYSIS_SUCCESS;
import static com.iceAge.server.analysis.infrastructure.code.AnalysisCode.COMMENT_ANALYSIS_SUCCESS;
import static com.iceAge.server.analysis.infrastructure.code.AnalysisCode.DISTRICT_ANALYSIS_SUCCESS;
import static com.iceAge.server.analysis.infrastructure.code.AnalysisCode.SCORE_SUCCESS;
import static com.iceAge.server.analysis.infrastructure.code.AnalysisCode.SUMMARY_SUCCESS;

import com.iceAge.server.analysis.domain.service.DataAnalysisDomainService;
import com.iceAge.server.analysis.presentation.dto.request.CommentRequestDTO;
import com.iceAge.server.analysis.presentation.dto.response.AllAnalysisResponseDTO;
import com.iceAge.server.analysis.presentation.dto.response.DistrictAnalysisResponseDTO;
import com.iceAge.server.analysis.presentation.dto.response.ScoreResponseDTO;
import com.iceAge.server.analysis.presentation.dto.response.SummaryResponseDTO;
import com.iceAge.server.global.response.ApiResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

  @GetMapping("/api/data/score")
  public ResponseEntity<ApiResponseData<ScoreResponseDTO>> getDataScore(@RequestParam(value = "districtCode") String districtCode,
                                                                        @RequestParam(value = "categoryCode") String categoryCode){
    return ResponseEntity.ok().body(ApiResponseData.of(SCORE_SUCCESS.getCode(), SCORE_SUCCESS.getMessage(), dataAnalysisDomainService.getScoreData(Integer.parseInt(districtCode), categoryCode)));
  }

  @PostMapping("/api/sentiment/predict")
  public ResponseEntity<ApiResponseData<?>> getSentimentPredict(@RequestBody CommentRequestDTO commentRequestDTO){
    if(commentRequestDTO.getMessages().size() <= 10){
      return ResponseEntity.ok().body(ApiResponseData.of(COMMENT_ANALYSIS_SUCCESS.getCode(), COMMENT_ANALYSIS_SUCCESS.getMessage(), dataAnalysisDomainService.getSmallCommentAnalysis(commentRequestDTO)));
    }

    return ResponseEntity.ok().body(ApiResponseData.of(COMMENT_ANALYSIS_SUCCESS.getCode(), COMMENT_ANALYSIS_SUCCESS.getMessage(), dataAnalysisDomainService.getLargeCommentAnalysis(commentRequestDTO)));
  }

  @GetMapping("/api/area/summary")
  public ResponseEntity<ApiResponseData<SummaryResponseDTO>> getSummaryData(@RequestParam(value = "districtCode") String districtCode){
    return ResponseEntity.ok().body(ApiResponseData.of(SUMMARY_SUCCESS.getCode(), SUMMARY_SUCCESS.getMessage(), dataAnalysisDomainService.getSummaryData(Integer.parseInt(districtCode))));

  }
}
