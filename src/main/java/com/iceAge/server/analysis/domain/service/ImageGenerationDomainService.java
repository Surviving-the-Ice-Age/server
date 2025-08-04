package com.iceAge.server.analysis.domain.service;

import com.iceAge.server.analysis.presentation.dto.request.ImageRequestDTO;
import com.iceAge.server.analysis.presentation.dto.response.ImageResponseDTO;
import java.util.List;
import reactor.core.publisher.Mono;

public interface ImageGenerationDomainService {
  ImageResponseDTO generateImage(ImageRequestDTO imageRequestDTO);
}
