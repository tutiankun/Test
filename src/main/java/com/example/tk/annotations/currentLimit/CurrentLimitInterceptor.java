package com.example.tk.annotations.currentLimit;

import com.example.tk.exception.BaseException;
import com.example.tk.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class CurrentLimitInterceptor implements HandlerInterceptor {
    private final static String SEPARATOR = "-";

    @Autowired
    private RedisService redisService;
    @Autowired
    private RedissonClient redissonClient;

    private static final String key="redission";

    /**
     *注意:并发场景下redis读取问题,使用redis分布式锁可以解决,但是性能受到影响
     * 可以使用redission
     * https://github.com/redisson/redisson/wiki/2.-%E9%85%8D%E7%BD%AE%E6%96%B9%E6%B3%95#28-%E4%B8%BB%E4%BB%8E%E6%A8%A1%E5%BC%8F
     * 加synchronized也无法解决分布式项目部署环境下redis读脏的问题
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            //通过HandlerMethod获取方法CurrentLimit注解
            CurrentLimit currentLimit = handlerMethod.getMethodAnnotation(CurrentLimit.class);
            //如果此方法不存在限流注解,直接放行
            if (Objects.isNull(currentLimit)){
                return true;
            }
            RLock lock = redissonClient.getLock(key+SEPARATOR+request.getServletPath());
            try {
                boolean tryLock = lock.tryLock(5, 6, TimeUnit.SECONDS);
                if (tryLock){
                    int number = currentLimit.number();
                    long time = currentLimit.time();
                    //如果次数和时间限制都大于0证明此处需要限流
                    if (time > 0 && number > 0) {
                        //这里的可以定义的是项目路径+API路径+ip，当然我这里就没去获取实际的ip了。key可以根据你们项目实际场景去设定
                        String key = request.getContextPath() + SEPARATOR + request.getServletPath() + SEPARATOR + "ip";
                        //获取reids缓存中的访问次数
                        Object value = redisService.get(key);
                        Long numberRedis = Objects.isNull(value)?0L: Long.valueOf(String.valueOf(value));
                        //如果是第一次访问，则设置此ip访问此API次数为1，并设置失效时间为注解中的时间
                        if (numberRedis==0) {
                            redisService.set(key, 1L, time);
                            return true;
                        }
                        //如果访问次数大于注解设定则抛出异常
                        if (numberRedis >= number) {
                            throw new BaseException("请求频繁，请稍后重试！");
                        }
                        //如果满足限流条件则更新缓存次数
                        redisService.updateValue(key, numberRedis + 1);
                        return true;
                    }
                }
            }catch (Exception e){
                log.error("限流异常",e);
                throw e;
            }finally {
                lock.unlock();
            }
        }
        return false;
    }


}
