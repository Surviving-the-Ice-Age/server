package com.iceAge.server.analysis.presentation.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ImageResponseDTO {
  private final List<String> images;
}
