package com.example.tk;

import com.example.tk.vo.Person;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

public class commonTest {

    @Test
    public void test1(){
        String key=String.format("%s&%s&%s","天坤","你好","吗");
        System.out.println(key);

    }

    @Test
    public void test2(){
        List<Person> people = get();
        System.out.println(people);
    }

    public List<Person> get(){
       return Lists.newArrayList();
    }


}
