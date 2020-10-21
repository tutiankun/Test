package com.example.tk.retry;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.tk.vo.Car;
import lombok.extern.slf4j.Slf4j;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;

@Slf4j
public class DemoService {
    @Retryable(value= {Exception.class},maxAttempts = 3)
    public void call() throws Exception {
        System.out.println("do something...");
        throw new Exception("RPC调用异常");
    }
    @Recover
    public void recover(RemoteAccessException e) {
        System.out.println(e.getMessage());
    }

    @Retryable(maxAttempts = 3, backoff = @Backoff(value = 3000, multiplier = 1.5))
    public Car getCustomer(String customerId) {
/*        if (true) {
            JSONArray data = retObj.getJSONArray("data");
            JSONO.tojson

            if (data != null && !data.isEmpty()) {
                return data.toJavaList(Car.class).get(0);
            }
        } else {
            log.error("异常，{}", customerId);
            throw new RuntimeException("获数据失败");
        }*/
        return null;
    }

    /**
     * @Retryable被注解的方法发生异常时会重试
     * @Retryable注解中的参数说明：
     * maxAttempts :最大重试次数，默认为3，如果要设置的重试次数为3，可以不写；
     * value：抛出指定异常才会重试
     * include：和value一样，默认为空，当exclude也为空时，所有异常都重试
     * exclude：指定不处理的异常，默认空，当include也为空时，所有异常都重试
     * backoff：重试等待策略，默认使用@Backoff@Backoff的value默认为1000L，我们设置为2000L。
     * @Backoff重试补偿机制，默认没有
     * @Backoff注解中的参数说明：
     * value：隔多少毫秒后重试，默认为1000L，我们设置为3000L；
     * delay：和value一样，但是默认为0；
     * multiplier（指定延迟倍数）默认为0，表示固定暂停1秒后进行重试，如果把multiplier设置为1.5，则第一次重试为2秒，第二次为3秒，第三次为4.5秒。
     * 4. 可以在指定方法上标记@Recover来开启重试失败后调用的方法(注意,需跟重处理方法在同一个类中)
     * @Recover：
     * 当重试到达指定次数时，被注解的方法将被回调，可以在该方法中进行日志处理。需要注意的是发生的异常和入参类型一致时才会回调。
     * 5. 采坑提示
     * 1、由于retry用到了aspect增强，所有会有aspect的坑，就是方法内部调用，会使aspect增强失效，那么retry当然也会失效。参考改链接
     * public class demo {
     *     public void A() {
     *         B();
     *     }
     *
     *     //这里B不会执行
     *     @Retryable(Exception.class)
     *     public void B() {
     *         throw new RuntimeException("retry...");
     *     }
     * }

     * 11
     * 2、重试机制，不能在接口实现类里面写。所以要做重试，必须单独写个service。
     * 3、maxAttemps参数解释的是说重试次数，但是我再打断点的时候发现这个=1时，方法一共只执行了一次。
     */


}
