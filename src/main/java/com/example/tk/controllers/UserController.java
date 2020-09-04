package com.example.tk.controllers;

import com.example.tk.service.UserService;
import com.example.tk.utils.WebResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/testJdbc")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/getUser")
    public WebResult<String> selectById(Integer id){
        return new WebResult<>(WebResult.SUCCESS_CODE,userService.selectById(id));
    }



}
