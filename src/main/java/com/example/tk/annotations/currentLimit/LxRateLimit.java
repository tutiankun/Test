package com.example.tk.annotations.currentLimit;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LxRateLimit {

    /**
     * 每秒向桶中放入令牌的数量   默认最大即不做限流
     */
    double perSecond() default Double.MAX_VALUE;

    /**
     * 获取令牌的等待时间  默认0
     */
    int timeOut() default 0;

    /**
     * 超时时间单位
     */
    TimeUnit timeOutUnit() default TimeUnit.SECONDS;


}
