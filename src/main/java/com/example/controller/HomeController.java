package com.example.controller;

import com.example.examples.AddAccountExample;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String home(){
        return "index.htm";
    }

    @RequestMapping("/addaccount")
    public String addAccountEx(){
        new AddAccountExample().addAccount();
        return "yey";
    }
}
