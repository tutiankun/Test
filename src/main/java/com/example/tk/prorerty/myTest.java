package com.example.tk.prorerty;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class myTest {


    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(PropertyConfig.class);
        BmwCar bmwCar = context.getBean(BmwCar.class);
        String brand = bmwCar.getBrand();
        System.out.println(brand);
        String host = bmwCar.getConnectionFactory().getHost();
        System.out.println(host);
    }



    @Test
    public void test1(){
/*        BmwCar bmwCar = context.getBean(BmwCar.class);
        MyConnectionFactory factory = context.getBean(MyConnectionFactory.class);
        if (factory instanceof ConnectionFactory){
            System.out.println(factory);
        }

        String brand = bmwCar.getBrand();
        System.out.println(brand);
        String host = bmwCar.getConnectionFactory().getHost();
        System.out.println(host);*/


    }
}
