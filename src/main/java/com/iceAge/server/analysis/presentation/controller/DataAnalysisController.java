package com.iceAge.server.analysis.presentation.controller;

import com.iceAge.server.global.response.ApiResponseData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataAnalysisController {
  @GetMapping("/api/data")
  public ResponseEntity<ApiResponseData<?>> getDataAnalysis(@RequestParam("districtCode") String districtCode,
                                                            @RequestParam("categoryCode") String categoryCode){

  }
}
