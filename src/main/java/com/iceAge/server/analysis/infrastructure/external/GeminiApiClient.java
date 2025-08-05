package com.iceAge.server.analysis.infrastructure.external;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import com.iceAge.server.analysis.presentation.dto.request.TextRequestDTO;
import com.iceAge.server.analysis.presentation.dto.response.TextResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Service;

@Service
public class GeminiApiClient {
  // The client gets the API key from the environment variable `GEMINI_API_KEY`.
  private final Client client;

  @Value("${gemini.model}")
  private String model;

  public GeminiApiClient(@Value("${gemini.api-key}") String apiKey) {
    if (apiKey == null || apiKey.isEmpty()) {
      throw new IllegalStateException("Gemini API 키가 설정되지 않았습니다."); //나중에 수정
    }
    this.client = Client.builder().apiKey(apiKey).build();
  }

  @Description("Gemini로 텍스트를 생성하여 반환")
  public TextResponseDTO generateText(TextRequestDTO textRequestDTO){

    String str = "내가 창업을 하려고 하는데, 업종은" + textRequestDTO.getCategory() + "이고, 지역은 " + textRequestDTO.getRegion() + "이고, 상권은 " + textRequestDTO.getDistrict() + "로 설정했어."
        + "주요 메뉴는 " + textRequestDTO.getMenu() + "이고, 컨셉은 " + textRequestDTO.getConcept() + "로 설정하려고 해. 그리고 주요 키워드는 " + textRequestDTO.getKeyword() +
        "로 설정할거야. 지금까지의 정보들을 바탕으로 가게홍보, 해시태그, 광고 문구를 작성해줘";

    GenerateContentResponse response =
        client.models.generateContent(
            model,
            str,
            null);

    return new TextResponseDTO(response.text());
  }

}
