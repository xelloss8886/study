package com.richard.study.service;

import com.richard.study.observer.FutureContext;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class FutureService {

    private AsyncTaskExecutor taskExecutor;

    private static final int MAX_THREAD_POOL_SIZE = 5;

    @PostConstruct
    public void init() {
        taskExecutor = new ConcurrentTaskExecutor(Executors.newFixedThreadPool(6));
    }

    public String doTask() {
        String result = null;
        FutureContext context = createFutureContext();

        try {
            result = getFutureFromContext(context);

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }

    private FutureContext createFutureContext() {
        FutureContext context = new FutureContext();
        for(int i = 0 ; i < MAX_THREAD_POOL_SIZE ; i++) {
            Future<String> future = null;
            if(i == 0) {
                 future = taskExecutor.submit(() -> {
                    Thread.sleep(1*1000L);
                    return Thread.currentThread().getName();
                });
            } else {
                future = taskExecutor.submit(() -> Thread.currentThread().getName());
            }
            context.setFutureTask(String.valueOf(i), future);
        }
        return context;
    }

    private String getFutureFromContext(FutureContext context) throws ExecutionException, InterruptedException{
        int test = 0;
        long startTime = System.currentTimeMillis();
        Future<String> lastFuture = null;
        while(true) {
            for(int i = 0 ; i < MAX_THREAD_POOL_SIZE ; i++) {
                Future<String> testt = context.getFuture(String.valueOf(i));
                if(!testt.isDone()) {
                    continue;
                } else {
                    lastFuture = testt;
                    System.out.println(testt.get());
                }
            }
            test++;
            if (test == 100000) break;
        }
        System.out.println(System.currentTimeMillis() - startTime);
        return lastFuture.get();
    }
}
