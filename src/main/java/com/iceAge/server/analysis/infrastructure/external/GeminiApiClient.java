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

    String str = "자, 넌 이제 마케팅 담당자이자 홍보 담당자이자 인스타그램에 홍보물을 업로드하는 전문가야. 내가 창업을 하려고 하는데, 업종은" + textRequestDTO.getCategory() + "이고, 지역은 " + textRequestDTO.getRegion() + "이고, 상권은 " + textRequestDTO.getDistrict() + "로 설정했어."
        + "주요 메뉴는 " + textRequestDTO.getMenu() + "이고, 컨셉은 " + textRequestDTO.getConcept() + "로 설정하려고 해. 그리고 주요 키워드는 " + textRequestDTO.getKeyword() +
        "로 설정할거야. 굵은 텍스트를 표시하기 위한 * 넣지말고, [,] 와 같은 괄호넣지 말고, 인스타그램을 사용하는 사용자들에게 제공하는 텍스트를 만들려고 하는거야. 그러니 그에 걸맞는 내용만 들어가도록 텍스트를 생성해줘 우리에게 대답하는 문장은 제외해줘야해";

    GenerateContentResponse response =
        client.models.generateContent(
            model,
            str,
            null);

    return new TextResponseDTO(response.text());
  }

}
