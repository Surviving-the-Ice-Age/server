package com.iceAge.server.analysis.domain.service;

import reactor.core.publisher.Mono;

public interface DataGetTestDomainService {
  Mono<String> getDataTest();
}
