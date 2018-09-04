package com.example.controller.customized;

import com.example.user.accesed.AccessedBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/")
public class SingleBookPageController {

    @Autowired
    private AccessedBook accessedBook;

    @RequestMapping(value = "/book/{bookUrl}")
    public String getBookPage(@PathVariable String bookUrl){
        accessedBook.setBookUrl(bookUrl);
        return "/gallery-single.htm";
    }
}
