package com.example.controller.model.mappings;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyLibraryController {

    @RequestMapping("/mylibrary")
    public String returnMyLibrary(){
        return "my-library.htm";
    }
}
