package com.iceAge.server.analysis.presentation.controller;

import com.iceAge.server.analysis.application.service.DataGetTestService;
import com.iceAge.server.analysis.domain.service.DataGetTestDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class DataGetTestController {

  private final DataGetTestDomainService dataGetTestDomainService;
  @GetMapping("/api/test")
  public Mono<String> getData(){
    return dataGetTestDomainService.getDataTest();
  }

  @GetMapping("/api/fastapi/{district}")
  public Mono<String> getFastApi(@PathVariable String district){
    return dataGetTestDomainService.getFastApi(district);
  }

}
