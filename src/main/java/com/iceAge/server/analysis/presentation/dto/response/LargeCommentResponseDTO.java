package com.iceAge.server.analysis.presentation.dto.response;

import java.util.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Description;

@Description("댓글이 10개 초과하는 경우 응답하는 dto 형식")
@Data
public class LargeCommentResponseDTO {
  private int count;
  private int valid_results;
  private float positive_ratio;
}

