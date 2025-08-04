package com.iceAge.server.analysis.presentation.dto.response;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Data
public class ImageResponseDTO {
  private List<Prediction> predictions;

  public List<String> getImages() {
    return predictions.stream()
        .map(Prediction::getBytesBase64Encoded)
        .collect(Collectors.toList());
  }

  @Data
  public static class Prediction {
    private String mimeType;
    private String bytesBase64Encoded;
  }
}
