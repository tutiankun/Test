package com.example.tk.controllers;

import com.example.tk.currentLimit.CurrentLimit;
import com.example.tk.currentLimit.LxRateLimit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/currentLimit")
public class CurrentLimitController {

    @CurrentLimit(number = 3, time = 5)
    @GetMapping("/test")
    public String test() {
        return "正常访问";
    }


    @GetMapping("/testAnnotation")
    @LxRateLimit(perSecond = 1.0,timeOut = 3)
    public String testAnnotation() {
        return "get token success";
    }

}
