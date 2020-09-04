package com.example.tk.service;

import com.example.tk.Mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public String selectById(Integer id){
        return userMapper.selectById(id);
    }

}
