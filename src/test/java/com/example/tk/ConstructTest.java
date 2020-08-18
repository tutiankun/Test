package com.example.tk;

import com.example.tk.service.ConstructService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ConstructTest extends BaseTest {
    @Autowired
    private ConstructService constructService;

    @Test
    public void test0(){
        constructService.test();
    }

    @Test
    public void testTry(){
        Integer integer = constructService.testTry();
        System.out.println(integer);
    }

}
