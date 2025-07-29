package com.iceAge.server.global.config;

import jakarta.persistence.EntityManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class JpaConfig {

  //나중에 jwt 추가하면 주석해제

//  @Bean
//  public AuditorAware<String> auditorAware() {
//    return new UserAuditorAware();
//  }

}
