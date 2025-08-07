package com.iceAge.server.analysis.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SummaryResponseDTO {
  @JsonProperty("상권명")
  private String storeName;

  @JsonProperty("총_상주인구")
  private int totalLive;

  @JsonProperty("총_직장인구")
  private int totalWorker;

  @JsonProperty("총_유동인구")
  private int totalFloating;
  @JsonProperty("상주인구_그래프")
  private String livingGraph;

  @JsonProperty("유동인구_그래프")
  private String movingGraph;

  @JsonProperty("직장인구_그래프")
  private String workerGraph;
}
