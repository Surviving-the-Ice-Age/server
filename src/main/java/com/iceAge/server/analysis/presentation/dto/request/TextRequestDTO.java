package com.iceAge.server.analysis.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class TextRequestDTO {
  @NotNull
  private String model;

  @NotNull
  private String prompt;
}
