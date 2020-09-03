package com.example.tk.currentLimit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrentLimit {
    /**
     * 请求次数
     * @return
     */
    int number() default 10;
    /**
     * 时间限制
     * @return
     */
    long time() default 3;


}


