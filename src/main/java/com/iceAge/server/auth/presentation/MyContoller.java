package com.iceAge.server.auth.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyContoller {

    @GetMapping("/")
    @ResponseBody
    public String mainAPI() {
        return "Welcome to IceAge Server!";
    }
}
