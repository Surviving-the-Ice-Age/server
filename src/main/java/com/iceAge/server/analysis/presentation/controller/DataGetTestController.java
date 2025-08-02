package com.iceAge.server.analysis.presentation.controller;

import com.iceAge.server.analysis.application.service.DataGetTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class DataGetTestController {

  private final DataGetTestService dataGetTestService;
  @GetMapping("/api/test")
  public Mono<String> getData(){
    return dataGetTestService.getDataTest();
  }

}
