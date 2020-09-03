package com.example.tk.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonAutoConfiguration {

    @Value("${spring.redisson.address}")
    private String addressUrl;



    /**
     * @return org.redisson.api.RedissonClient
     * @Author huangwb
     * @Description //
     * @Date 2020/3/19 22:54
     * @Param []
     **/
    @Bean("redissonClient")
    public RedissonClient getRedisson() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress(addressUrl);
        return Redisson.create(config);
    }
    /**
     * @return
     * @Author huangwb
     * @Description //
     * @Date  20203/19 10:54
     * @Param
     *
     **/
   /* @Bean
    public RedissonClient getRedisson() {
        RedissonClient redisson;
        Config config = new Config();
        config.useMasterSlaveServers()
                //可以用"rediss://"来启用SSL连接
                .setMasterAddress("redis://***(主服务器IP):6379").setPassword("web2017")
                .addSlaveAddress("redis://***（从服务器IP）:6379")
                .setReconnectionTimeout(10000)
                .setRetryInterval(5000)
                .setTimeout(10000)
                .setConnectTimeout(10000);//（连接超时，单位：毫秒 默认值：3000）;

        //  哨兵模式config.useSentinelServers().setMasterName("mymaster").setPassword("web2017").addSentinelAddress("***(哨兵IP):26379", "***(哨兵IP):26379", "***(哨兵IP):26380");
        redisson = Redisson.create(config);
        return redisson;
    }*/

}
