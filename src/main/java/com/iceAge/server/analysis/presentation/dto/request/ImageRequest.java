package com.iceAge.server.analysis.presentation.dto.request;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Description;

@Getter
@Description("Imagen 모델에 요청을 보내기 위한 객체")
public class ImageRequest {
  private final List<Instance> instances;
  private final Parameter parameters;

  @Getter
  @RequiredArgsConstructor
  private static class Instance{
    private final String prompt;
  }

  @Getter
  @RequiredArgsConstructor
  private static class Parameter{
    private final int sampleCount;
  }

  public ImageRequest(ImageRequestDTO imageRequestDTO){
    ArrayList<Instance> list = new ArrayList<>();

    String base_str = imageRequestDTO.getCategory() + " 업종이고, " + imageRequestDTO.getRegion() + " 지역에, " + imageRequestDTO.getDistrict() + " 상권에서, " + imageRequestDTO.getMenu() + " 메뉴를 팔 예정이고, " +
        imageRequestDTO.getConcept() + " 컨셉을 가지고, " + imageRequestDTO.getKeyword() + " 키워드를 중심으로 가진 가게를 창업 할 생각이야.";

    String reqPrompt = base_str + "이 가게에 어울리는 사실적인 가게 내부 인테리어만 있는 이미지 1장, 먹음직스러워 보이고 사실적인" + imageRequestDTO.getMenu() + "메뉴 이미지만 있는 이미지 1장, " + "가게의 컨셉과 어울리는 사실적으로 보이는 외관만 있는 이미지 1장을 만들어줘";

    Instance instance = new Instance(reqPrompt);

    list.add(instance);

    this.instances = list;
    this.parameters = new Parameter(3);
  }

}
