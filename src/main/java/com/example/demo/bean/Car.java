package com.example.demo.bean;

import lombok.Data;

import java.util.HashMap;

@Data
public class Car {
    private String brand;
    private Integer price;
    private HashMap<Integer,Integer> paiTotal=new HashMap<>();

    public Car(String brand, Integer price) {
        this.brand = brand;
        this.price = price;
    }
    public Car() {
    }
}
