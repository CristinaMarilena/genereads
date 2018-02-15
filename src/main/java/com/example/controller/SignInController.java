package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SignInController {

    @RequestMapping("/sign")
    public String signIn(){
        return "sign-in.html";
    }
}
