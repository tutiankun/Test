package com.example.tk.prorerty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BmwCar extends Car {

    @Value("宝马")
    public void setBrand(String brand){
        super.setBrand(brand);
    }
    @Autowired
    //@Qualifier("myConnectionFactory")
    public void setConnectionFactory(ConnectionFactory myConnectionFactory){
        super.setConnectionFactory(myConnectionFactory);
    }






}
