package com.example.tk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@PropertySources(value = {
        @PropertySource("classpath:jdbc.properties")
})
@MapperScan(basePackages = "com.example.tk.mapper")
@EnableRetry
public class TkApplication {

    public static void main(String[] args) {
        SpringApplication.run(TkApplication.class, args);
    }

}
