package com.example.tk.annotations.currentLimit.controllers;

import com.example.tk.designMode.Singleton;
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
        //测试静态内部类实现单例模式,验证在并发情况下只创建一个Singleton对象
        Singleton instance = Singleton.getInstance();
        System.out.println(instance);
        return new WebResult<>(WebResult.SUCCESS_CODE,userService.selectById(id));
    }



}
