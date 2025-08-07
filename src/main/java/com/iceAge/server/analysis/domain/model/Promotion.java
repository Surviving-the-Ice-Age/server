package com.iceAge.server.analysis.domain.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iceAge.server.analysis.presentation.dto.request.PromotionRequestDTO;
import com.iceAge.server.auth.domain.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Description;

@Description("홍보 데이터")
@Entity
@Getter
@Table(name = "promotion")
@NoArgsConstructor
public class Promotion {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(columnDefinition = "TEXT")
  private String promotionText;

  @Column(columnDefinition = "TEXT")
  private String imgLinksJson; // JSON 문자열로 저장

  @Transient // DB에 저장하지 않는 임시 필드
  private List<String> imgLinks;

  @ManyToOne
  @Setter
  @JoinColumn(name = "user_id")
  private User user;

  // Jackson ObjectMapper (Spring에서 주입받거나 static으로 초기화)
  private static final ObjectMapper objectMapper = new ObjectMapper();

  // imgLinks getter에서 JSON을 List<String>으로 변환
  public List<String> getImgLinks() {
    if (imgLinks == null && imgLinksJson != null) {
      try {
        imgLinks = objectMapper.readValue(imgLinksJson, new TypeReference<List<String>>() {});
      } catch (IOException e) {
        throw new RuntimeException("Failed to parse imgLinks JSON", e);
      }
    }
    return imgLinks;
  }

  // imgLinks setter에서 List<String>을 JSON으로 변환
  private void setImgLinks(List<String> imgLinks) {
    this.imgLinks = imgLinks;
    try {
      this.imgLinksJson = objectMapper.writeValueAsString(imgLinks);
    } catch (IOException e) {
      throw new RuntimeException("Failed to serialize imgLinks to JSON", e);
    }
  }

  public Promotion(PromotionRequestDTO promotionRequestDTO){
    this.promotionText = promotionRequestDTO.getPromotion();
    setImgLinks(promotionRequestDTO.getImages());
  }

}
