package com.iceAge.server.analysis.presentation.dto.response;

import java.util.List;
import lombok.Data;
import org.springframework.context.annotation.Description;

@Description("댓글이 10개 미만인 경우 응답하는 dto 형식")
@Data
public class SmallCommentResponseDTO {
  private int count;
  private int valid_results;
  private float positive_ratio;

  private List<Comment> results;

  @Data
  static class Comment{
    String text;
    String label;
    float confidence;
  }
}
