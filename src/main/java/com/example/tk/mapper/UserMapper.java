package com.example.tk.mapper;

public interface UserMapper {

   //@Select("select name from BOSS_ACCOUNT where id=#{id}")
    String selectById(Integer id);

}
