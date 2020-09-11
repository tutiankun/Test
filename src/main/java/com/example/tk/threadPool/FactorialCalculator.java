package com.example.tk.threadPool;

import java.util.concurrent.*;

public class FactorialCalculator implements Callable<String> {

    private Integer number;
    public FactorialCalculator(Integer number) {
        this.number = number;
    }

    @Override
    public String call() {
        long id = Thread.currentThread().getId();
        return "线程id:"+id+"  number:"+number;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i ++){
            FactorialCalculator factorialCalculator = new FactorialCalculator(i);
            Future<String> res = executor.submit(factorialCalculator);
            String resulet = res.get();
            System.out.println(resulet);

        }

    }

}
