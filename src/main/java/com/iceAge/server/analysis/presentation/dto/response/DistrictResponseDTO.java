package com.iceAge.server.analysis.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
public class DistrictResponseDTO {
  @JsonProperty("상권코드")
  private long districtCode; // 상권코드

  @JsonProperty("인사이트")
  private String insight; // 인사이트

  @JsonProperty("이미지URL")
  private String imageUrl; // 이미지URL
}
