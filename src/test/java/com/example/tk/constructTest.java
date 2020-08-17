package com.example.tk;

import com.example.tk.service.ConstructService;
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
