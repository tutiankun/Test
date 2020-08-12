package com.example.demo;

import com.example.demo.service.ConstructService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class constructTest extends BaseTest {
    @Autowired
    private ConstructService constructService;

    @Test
    public void test0(){
        constructService.test();
    }

}
