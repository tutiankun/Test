package com.example.tk.threadPool;

import java.util.concurrent.ExecutorService;

/**
 * Created by tiankun.tu@ttpai.cn on 2019/8/26.
 */
public abstract class AbstractThreadPool {

    /**
     * 创建线程池
     * 
     * @param corePoolSize
     * @param maximumPoolSize
     * @param keepAliveTime
     * @param unit
     * @param workQueue
     * @return
     */
    public abstract ExecutorService creatThreadPool();

    /**
     * 销毁线程池
     * 
     * @param executorService
     */
    public abstract void destroyThreadPool(ExecutorService executorService);
}
