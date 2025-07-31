package com.iceAge.server.analysis.presentation.controller;

import static com.iceAge.server.analysis.infrastructure.code.AnalysisCode.TEXT_GENERATION_SUCCESS;

import com.iceAge.server.analysis.application.service.TextGenerationService;
import com.iceAge.server.analysis.presentation.dto.request.TextRequestDTO;
import com.iceAge.server.analysis.presentation.dto.response.TextResponseDTO;
import com.iceAge.server.global.response.ApiResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GenerateTextController {
  private final TextGenerationService textGenerationService;
  @PostMapping("/api/generate/text")
  public ResponseEntity<ApiResponseData<TextResponseDTO>> generateText(@RequestBody TextRequestDTO textRequestDTO){
    return ResponseEntity.ok().body(ApiResponseData.of(TEXT_GENERATION_SUCCESS.getCode(), TEXT_GENERATION_SUCCESS.getMessage(), textGenerationService.generateText(textRequestDTO)));
  }

}
