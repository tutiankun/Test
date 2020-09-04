package com.example.tk.Mapper;

import org.apache.ibatis.annotations.Select;

public interface UserMapper {

   //@Select("select name from BOSS_ACCOUNT where id=#{id}")
    String selectById(Integer id);

}
