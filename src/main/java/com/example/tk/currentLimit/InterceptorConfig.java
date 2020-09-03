package com.example.tk.currentLimit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Autowired
    private CurrentLimitInterceptor currentLimitInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //此处也可以通过addPathPatterns方法添加此拦截器对部分请求路径有效，也可以通过excludePathPatterns过滤请求路径
        registry.addInterceptor(currentLimitInterceptor);
    }


}
