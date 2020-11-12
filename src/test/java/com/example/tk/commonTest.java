package com.example.tk;

import com.example.tk.vo.Car;
import com.example.tk.vo.Person;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Test
    public void test3(){
        int a=3;
        for (int i = 0; i < 5; i++) {
            System.out.println(i);
            if (i==a){
                return;
            }
            System.out.println("i="+i);
        }
        System.out.println("测试");

    }

    @Test
    public void test(){
        ArrayList<Person> list = new ArrayList<>();
        list=null;
        Map<Integer, String> map = list.stream().collect(Collectors.toMap(Person::getAge, Person::getName));
        System.out.println(map);

    }



}
