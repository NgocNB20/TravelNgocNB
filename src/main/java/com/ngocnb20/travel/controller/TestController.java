package com.ngocnb20.travel.controller;

import com.ngocnb20.travel.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class TestController {
    @Autowired
    FileStorageService fileStorageService;
    public String testTemplate(){
        return "test";
    }

}
