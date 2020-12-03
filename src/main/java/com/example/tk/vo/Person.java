package com.example.tk.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Person {
    private String name;
    private Integer age;
    private boolean error;
    private String country;
}
