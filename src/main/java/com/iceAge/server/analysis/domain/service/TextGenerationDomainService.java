package com.iceAge.server.analysis.domain.service;

import com.iceAge.server.analysis.presentation.dto.request.TextRequestDTO;
import com.iceAge.server.analysis.presentation.dto.response.TextResponseDTO;

public interface TextGenerationDomainService {
  public TextResponseDTO generateText(TextRequestDTO textRequestDTO);
}
