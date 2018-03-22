package com.example.controller;

import com.example.examples.AddAccountExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @Autowired
    AddAccountExample addAccount;

    @RequestMapping("/")
    public String home(){
        return "index.htm";
    }

    @RequestMapping("/addaccount")
    public String addAccountEx(){
        addAccount.addAccount();
        return "yey";
    }
}
