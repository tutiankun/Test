package com.example.demo.test;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.bean.Car;
import com.example.demo.bean.Person;
import com.example.demo.config.BeanConfig;
import com.example.demo.myAbstract.AbstactBean;
import com.example.demo.myAbstract.InitBeanA;
import com.example.demo.myAbstract.ThreadLocalUtil;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MyTest {

    ApplicationContext context = new AnnotationConfigApplicationContext(BeanConfig.class);

    @Test
    public void test1(){
        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        InitBeanA initBeanA = (InitBeanA)context.getBean("initBeanA");
        initBeanA.testa();
    }

    @Test
    public void test2(){
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
        int pageSize=2;
        int count=list.size();
        //判定有多少页
        int countPage=count % pageSize > 0 ? (count / pageSize) + 1 : count / pageSize;
        System.out.println(countPage);
        for (int i = 1; i <= countPage; i++) {
            List<Integer> subList = list.subList(pageSize * (i-1), ((pageSize * i) > count ? count : (pageSize * i)));
            for (Integer integer : subList) {
                System.out.println("第"+i+"页:"+integer);
            }
        }
    }

    @Test
    public void test3(){
        HashMap<String, Object> map = new HashMap<>();
        String o = (String) map.get("1");
        if (Objects.isNull(o)){
            System.out.println(1111);
        }

    }

    @Test
    public void test4(){
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(BeanConfig.class);
        annotationConfigApplicationContext.close();
    }

    @Test
    public void test5(){
        int i=0;
        do {
            i++;
            if (i==4){
                break;
            }
            System.out.println("线程休眠getbDealInfo方法第"+i+"次");

        }while (i<4);
    }


    @Test
    public void test6(){
        BigDecimal bigDecimal= BigDecimal.valueOf(0.1);
        bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_DOWN);
        BigDecimal bd = new BigDecimal(10000);
        Integer price = bd.multiply(bigDecimal).intValue();
        System.out.println(price);
    }

    @Test
    public void test7(){
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        Map<String, String> stringMap = list.stream().collect(Collectors.toMap(a -> a, Function.identity()));
        for (Map.Entry<String, String> entry : stringMap.entrySet()) {
            System.out.println("key:"+entry.getKey());
            System.out.println("value:"+entry.getValue());
        }
        String d = stringMap.get("d");
        System.out.println(d);

        List<String> strings = list.subList(1, list.size());
        System.out.println(strings);

    }

    @Test
    public void test8(){
        List<Integer> list = new ArrayList<>();
        list.add(1);
        for (int i = 0; i <5 ; i++) {
            int a=i % list.size();
            System.out.println(a);
        }
    }

    @Test
    public void test9(){
        Car car = new Car();
        HashMap<Integer, Integer> paiTotal = car.getPaiTotal();
        paiTotal.put(1,2);
        String toJson = JSONObject.toJSONString(car);
        System.out.println(toJson);
    }

    @Test
    public void test10(){
       BigDecimal amount=new BigDecimal(0);
       BigDecimal a= new BigDecimal("0.35");
       BigDecimal b= new BigDecimal("0.38");
       BigDecimal c= new BigDecimal("0.27");
        BigDecimal v1 = amount.multiply(a);
        int i = v1.intValue();
        BigDecimal subtract = v1.subtract(new BigDecimal(i));
        System.out.println("v1:"+v1);
        System.out.println("subtract:"+subtract);
        BigDecimal v2 = amount.multiply(b);
        System.out.println("v2:"+v2);
        BigDecimal v3 = amount.multiply(c);
        System.out.println("v3:"+v3);

        DecimalFormat df2 = new DecimalFormat("#.00");
        String v11 = df2.format(v1);
        System.out.println("v11:"+v11);
        String v22 = df2.format(v2);
        System.out.println("v22:"+v22);
        String v33 = df2.format(v3);
        System.out.println("v33:"+v33);
        Map<Integer, Integer> map = new LinkedHashMap<>();
        map.put(1,25);
        map.put(3,45);
        map.put(2,30);
        List<Map.Entry<Integer, Integer>> list = new ArrayList<>(map.entrySet());
        list.sort(new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        System.out.println(map);
    }

    @Test
    public void test11(){
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.remove(0);
        list = new ArrayList<>();
        System.out.println(111);
    }


    @Test
    public void test12(){
        ThreadLocalUtil.set("a",1111);

        System.out.println("----------------------");
        ThreadLocalUtil.set("b",22222);

        Object a = ThreadLocalUtil.get("a");
        System.out.println("a="+a);

        Object b = ThreadLocalUtil.get("b");
        System.out.println("b="+b);

        ThreadLocal<HashMap<String,Integer>> threadLocal = new ThreadLocal<>();
        HashMap<String, Integer> map = threadLocal.get();

    }
    public static String ss="测试"+new Date();
    @Test
    public void test13() throws InterruptedException {
        for (int i = 0; i <3 ; i++) {
            System.out.println(ss);
            Thread.sleep(3000);
        }
    }

    @Test
    public void test14(){
        AbstactBean abstactBean = new AbstactBean() {
            @Override
            protected boolean getAllow(Integer i) {
                System.out.println(1111111);
                return false;
            }
        };
        System.out.println("测试抽象:"+abstactBean);
    }

    @Test
    public void test15() throws ParseException {
        String beginTime=new String("2017-06-09 10:22:22");
        String endTime=new String("2017-05-08 11:22:22");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date sd1=df.parse(beginTime);
        Date sd2=df.parse(endTime);
        System.out.println(sd1.before(sd2));
        System.out.println(sd1.after(sd2));
    }

    @Test
    public void test16() throws ParseException {
        Map tag=new HashMap();
        tag.put("name"," 张三");
        tag.put("age",18);
        tag.put("price",1.2);
        /*Person object = JSONObject.parseObject(JSON.toJSONString(tag), new TypeReference<Person>() {
        });*/
        Person object = new Person();
        System.out.println(object.toString());

    }


    }










