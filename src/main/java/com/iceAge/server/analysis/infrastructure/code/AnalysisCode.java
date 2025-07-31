package com.iceAge.server.analysis.infrastructure.code;

import com.iceAge.server.global.response.Code;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AnalysisCode implements Code {
  TEXT_GENERATION_SUCCESS(HttpStatus.OK, "AN200", "텍스트가 성공적으로 생성되었습니다."),
  MODEL_NOT_FOUND(HttpStatus.BAD_REQUEST, "AN400", "GEMINI 모델을 찾을 수 없습니다."),
  PROMPT_NOT_FOUND(HttpStatus.BAD_REQUEST, "AN401", "GEMINI 프롬프트 내용이 없습니다");

  private final HttpStatus status;
  private final String code;
  private final String message;
}
