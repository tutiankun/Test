package com.example.tk.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car {
    private String brand;
    private Integer price;
    private HashMap<Integer,Integer> paiTotal=new HashMap<>();


}
