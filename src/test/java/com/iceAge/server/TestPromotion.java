package com.iceAge.server;

import com.iceAge.server.analysis.domain.model.Promotion;
import com.iceAge.server.auth.domain.model.User;
import com.iceAge.server.auth.infrastructure.repository.UserRepository;
import com.iceAge.server.global.exception.BaseException;
import org.aspectj.lang.annotation.RequiredTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RestController;

@SpringBootTest
public class TestPromotion {

  @Autowired
  UserRepository userRepository;
  @Test
  public void test(){
    User user = userRepository.findByUsername("google 104827573075197890297").orElseThrow(IllegalAccessError::new);
    System.out.println(user.getName());

    for(Promotion promotion : user.getPromotion()){
      System.out.println(promotion.getId());
    }
  }
}
