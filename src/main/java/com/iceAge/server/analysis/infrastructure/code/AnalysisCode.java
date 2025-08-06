package com.iceAge.server.analysis.infrastructure.code;

import com.iceAge.server.global.response.Code;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties.Http;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AnalysisCode implements Code {
  //응답 성공 반환 코드
  TEXT_GENERATION_SUCCESS(HttpStatus.OK, "AN200", "텍스트가 성공적으로 생성되었습니다."),
  IMAGE_GENERATION_SUCCESS(HttpStatus.OK, "AN201", "이미지가 성공적으로 생성되었습니다."),
  PROMOTION_SAVE_SUCCESS(HttpStatus.OK, "AN202", "홍보물을 성공적으로 저장하였습니다."),
  DISTRICT_ANALYSIS_SUCCESS(HttpStatus.OK, "AN203", "상권 분석 결과를 성공적으로 응답하였습니다"),
  ANALYSIS_SUCCESS(HttpStatus.OK, "AN204", "분석 결과를 성공적으로 응답하였습니다"),


  //요청 실패 코드
  MODEL_NOT_FOUND(HttpStatus.BAD_REQUEST, "AN400", "GEMINI 모델을 찾을 수 없습니다."),
  PROMPT_NOT_FOUND(HttpStatus.BAD_REQUEST, "AN401", "GEMINI 프롬프트 내용이 없습니다"),
  NO_FILE_HERE(HttpStatus.BAD_REQUEST, "AN402", "파일이 존재하지 않습니다."),
  NOT_VALID_FILE(HttpStatus.BAD_REQUEST, "AN403", "유효한 이미지 형식이 아닙니다."),
  USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "AN404", "해당 유저를 찾을 수 없습니다."),
  NOT_VALID_CODE(HttpStatus.BAD_REQUEST, "AN405", "상권 코드 또는 업종 코드가 유효하지 않습니다."),

  //응답 실패 코드
  DISTRICT_RESPONSE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "AN500", "해당 상권의 데이터를 불러 올 수 없습니다.");


  private final HttpStatus status;
  private final String code;
  private final String message;
}
