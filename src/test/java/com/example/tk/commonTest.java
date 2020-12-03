package com.example.tk;

import com.example.tk.vo.Car;
import com.example.tk.vo.Person;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
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

    @Test
    public void test4(){
        testResult();
    }

    public  boolean testResult() {

        for (int j=0;j<3;j++){
            for(int i=1; i<=5; i++) {
                System.out.println("-------------->开始：" + i);
                if(i == 3) {
                    return true;
                }
                System.out.println("-------------->结束：" + i);
            }
        }
        return true;
    }

    @Test
    public void test5(){
        String time = "2020/02:13";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date date = null;
        try {
            date = sdf.parse(time);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test6(){
        ArrayList<String> list1 = new ArrayList<>();
        list1.add("1");
        list1.add("2");
        list1.add("3");
        ArrayList<String> list2 = new ArrayList<>();
        list2.add("2");
        list2.add("3");
        list2.add("4");
        Map<String, String> tempMap = list2.parallelStream().collect
                (Collectors.toMap(Function.identity(), Function.identity(), (oldData, newData) -> newData));
        List<String> list3 = list1.parallelStream().filter(str ->
                !tempMap.containsKey(str)
        ).collect(Collectors.toList());
        list3.forEach(s -> System.out.println(s));
    }

    @Test
    public void tes7(){
        ArrayList<Person> list = new ArrayList<>();
        Person person1 = new Person("a",1,true,"USA");
        Person person2 = new Person("a",3,true,"USA");
        Person person3 = new Person("b",5,true,"CHINA");
        list.add(person1);
        list.add(person2);
        list.add(person3);
        Map<String, Integer> integerMap = list.stream().collect(Collectors.toMap(s->s.getName()+"&"+s.getCountry(),
                Person::getAge,(key1 , key2)->key1+key2));

        integerMap.entrySet().forEach(item->
            System.out.println("key:"+item.getKey() +" value:"+item.getValue())
        );



    }





}
