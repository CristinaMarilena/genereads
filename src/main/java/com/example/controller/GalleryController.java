package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GalleryController {

    @RequestMapping("/gallery3col")
    public String returnGallery3ColPage(){
        return "gallery-3col.htm";
    }

    @RequestMapping("/gallery4col")
    public String returnGallery4ColPage(){
        return "gallery-4col.htm";
    }

    @RequestMapping("/gallery6col")
    public String returnGallery6ColPage(){
        return "gallery-6col.htm";
    }

    @RequestMapping("/gallery4colCircle")
    public String returnGallery4ColCirclePage(){
        return "gallery-4col-circle.htm";
    }

    @RequestMapping("/gallerySingle")
    public String returnGallerySinglePage(){
        return "gallery-single.htm";
    }

}
