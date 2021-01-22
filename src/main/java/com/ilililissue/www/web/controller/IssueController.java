package com.ilililissue.www.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IssueController {

    @GetMapping("/api/v1/index")
    public String index(){
        return "index";
    }
}
