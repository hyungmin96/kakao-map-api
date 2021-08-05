package com.example.kakaomap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MapController {

    @GetMapping("/registMapAddress")
    public String getHome(){
        return "/common/writerMapAddress";
    }

    @GetMapping("/clientMapAddress")
    public String getClient(){
        return "/common/clientMapAddress";
    }

    @GetMapping("/registExchangeInfo")
    public String getRegistInfo(){
        return "/common/writerExchangeInfo";
    }

}
