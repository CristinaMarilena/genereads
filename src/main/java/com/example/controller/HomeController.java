package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by cristina on 16.08.2017.
 */
@Controller
public class HomeController {

    @RequestMapping("/")
    public String home(){
        return "index.htm";
    }
}
