package com.iceAge.server.analysis.application.service;

import static com.iceAge.server.analysis.infrastructure.code.AnalysisCode.MODEL_NOT_FOUND;
import static com.iceAge.server.analysis.infrastructure.code.AnalysisCode.PROMPT_NOT_FOUND;

import com.iceAge.server.analysis.domain.service.TextGenerationDomainService;
import com.iceAge.server.analysis.infrastructure.external.GeminiApiClient;
import com.iceAge.server.analysis.presentation.dto.request.TextRequestDTO;
import com.iceAge.server.analysis.presentation.dto.response.TextResponseDTO;
import com.iceAge.server.global.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TextGenerationService implements TextGenerationDomainService {
  private final GeminiApiClient geminiApiClient;

  @Override
  public TextResponseDTO generateText(TextRequestDTO textRequestDTO) {
    if(textRequestDTO.getModel() == null || textRequestDTO.getModel().isEmpty()){
      throw new BaseException(MODEL_NOT_FOUND);
    }

    if(textRequestDTO.getPrompt() == null || textRequestDTO.getPrompt().isEmpty()){
      throw new BaseException(PROMPT_NOT_FOUND);
    }

    return geminiApiClient.generateText(textRequestDTO);
  }
}
