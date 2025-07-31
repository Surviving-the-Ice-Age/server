package com.iceAge.server.analysis.infrastructure.external;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import com.iceAge.server.analysis.presentation.dto.request.TextRequestDTO;
import com.iceAge.server.analysis.presentation.dto.response.TextResponseDTO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class GeminiApiClient {
  private final Client client;

  public GeminiApiClient(@Value("${gemini.api-key}") String apiKey) {
    if (apiKey == null || apiKey.isEmpty()) {
      throw new IllegalStateException("Gemini API 키가 설정되지 않았습니다."); //나중에 수정
    }
    this.client = Client.builder().apiKey(apiKey).build();
  }

  @Description("Gemini로 텍스트를 생성하여 반환")
  public TextResponseDTO generateText(TextRequestDTO textRequestDTO){
    // The client gets the API key from the environment variable `GEMINI_API_KEY`.
    GenerateContentResponse response =
        client.models.generateContent(
            textRequestDTO.getModel(),
            textRequestDTO.getPrompt(),
            null);

    return new TextResponseDTO(response.text());
  }

}
