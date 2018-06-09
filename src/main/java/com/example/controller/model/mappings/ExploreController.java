package com.example.controller.model.mappings;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/v1/")
public class ExploreController {

    @RequestMapping("/exploreresults")
    public String getResultsPage(){
        return "/explore-results.htm";
    }
}
