package com.example.oauth2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Oauth2Controller {

    @GetMapping("/")
    public String index(){
        return "index"; // index.html(로그인 UI)
    }
}
