package com.example.controller.model.mappings;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ParserController {

    @RequestMapping("/parser")
    public String returnParserPage(){
        return "parser.html";
    }
}
