package com.iceAge.server.analysis.presentation.controller;

import static com.iceAge.server.analysis.infrastructure.code.AnalysisCode.IMAGE_GENERATION_SUCCESS;

import com.iceAge.server.analysis.domain.service.ImageGenerationDomainService;
import com.iceAge.server.analysis.presentation.dto.request.ImageRequestDTO;
import com.iceAge.server.analysis.presentation.dto.response.ImageResponseDTO;
import com.iceAge.server.global.response.ApiResponseData;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class GenerateImageController {
  private final ImageGenerationDomainService imageGenerationDomainService;

  @PostMapping("/api/generate/image")
  public ResponseEntity<ApiResponseData<ImageResponseDTO>> getImage(@RequestBody ImageRequestDTO imageRequestDTO){
    return ResponseEntity.ok().body(ApiResponseData.of(IMAGE_GENERATION_SUCCESS.getCode(), IMAGE_GENERATION_SUCCESS.getMessage(), imageGenerationDomainService.generateImage(imageRequestDTO)));
  }

}
