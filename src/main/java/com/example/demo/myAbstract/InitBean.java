package com.example.demo.myAbstract;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class InitBean   {
    @Autowired
    private  Teacher teacher;
    private static HashMap<Integer,People> map=new HashMap<>();

/*    static {
        map.put(1,teacher);
    }*/

    public static People getBean(Integer key){
        return map.get(key);
    }

  /*  @Override
    public void afterPropertiesSet() throws Exception {
        map.put(1,teacher);
    }*/
}
