package com.iceAge.server.analysis.presentation.dto.response;

import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

//이미지 생성된 것을 받아서 객체로 매핑
@Data
public class GetImageDTO {
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
