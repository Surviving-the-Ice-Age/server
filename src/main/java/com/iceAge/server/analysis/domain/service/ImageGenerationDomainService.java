package com.iceAge.server.analysis.domain.service;

import com.iceAge.server.analysis.presentation.dto.request.ImageRequestDTO;
import com.iceAge.server.analysis.presentation.dto.request.TextRequestDTO;
import com.iceAge.server.analysis.presentation.dto.response.ImageResponseDTO;
import com.iceAge.server.analysis.presentation.dto.response.TextResponseDTO;
import java.util.List;
import reactor.core.publisher.Mono;

public interface ImageGenerationDomainService {
  Mono<List<String>> generateImage(ImageRequestDTO imageRequestDTO);
}
