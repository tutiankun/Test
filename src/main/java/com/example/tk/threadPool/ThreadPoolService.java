package com.example.tk.threadPool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

/**
 * Created by tiankun.tu@ttpai.cn on 2019/8/26.
 */
@Service
@Slf4j
public class ThreadPoolService extends AbstractThreadPool {

    /**
     * 核心线程数,在多核CPU时代，我们要让每一个CPU核心都参与计算，将CPU的性能充分利用起来，这样才算是没有浪费服务器配置
     */
    private static final int DEFAULT_MAX_CONCURRENT = Runtime.getRuntime().availableProcessors() * 2;

    /**
     * 默认队列大小
     */
    private static final int DEFAULT_SIZE = 500;

    /**
     * 默认线程存活时间
     */
    private static final long DEFAULT_KEEP_ALIVE = 60L;

    /**
     * 执行队列
     */
    private static BlockingQueue<Runnable> executeQueue = new ArrayBlockingQueue<>(DEFAULT_SIZE);

    /**
     * 线程超时时间
     */
    private static final long AWAIT_TIME = 10 * 1000;


    @Override
    public ExecutorService creatThreadPool() {
        return new ThreadPoolExecutor(DEFAULT_MAX_CONCURRENT, DEFAULT_MAX_CONCURRENT * 4, DEFAULT_KEEP_ALIVE,
                TimeUnit.SECONDS, executeQueue);
    }

    @Override
    public void destroyThreadPool(ExecutorService executorService) {
        if (executorService != null) {
            executorService.shutdown();
        }
        try {
            if (!executorService.awaitTermination(AWAIT_TIME, TimeUnit.MILLISECONDS)) {
                // 超时的时候向线程池中所有的线程发出中断(interrupted)。
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            log.error("awaitTermination interrupted:", e);
            executorService.shutdownNow();
        }
    }
}
