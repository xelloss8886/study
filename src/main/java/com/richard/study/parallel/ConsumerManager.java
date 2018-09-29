package com.richard.study.parallel;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Consumer들 관리 클래스
 */
@Slf4j
@Component(value = "consumerManager")
public class ConsumerManager implements Callable<Object>{

    private static final int MAX_CAPACITY = 4;

    private static final int DATA_SIZE_FROM_QUEUE = 100;

    private List<ExecutorService> taskList = new ArrayList<>(4);

    private Map<Integer, Future> futureMap = new HashMap<>();

    private final CountDownLatch latch = new CountDownLatch(MAX_CAPACITY);

    @Autowired
    private BlockingQueue queue;

    @PostConstruct
    public void init() {
        for(int i = 0 ; i < MAX_CAPACITY ; i++) {
            log.info("Post Construct");
            taskList.add(Executors.newSingleThreadExecutor());
        }
    }

    @Override
    public Object call() {
        int index = 0;
        log.info("consumer run..");
        while(true) {
            for(int i = 0 ; i < MAX_CAPACITY ; i++) {
                if(index == MAX_CAPACITY) index = initIndex();
                List<Object> list = Stream.of(queue).limit(DATA_SIZE_FROM_QUEUE).collect(Collectors.toList());
                //getData from queue
                ExecutorService executorService = taskList.get(i);
                ConsumerTask consumerTask = new ConsumerTask(latch);
                consumerTask.setData(list);
                Future f = executorService.submit(consumerTask);
                futureMap.put(i, f);
            }

            try {
                latch.await();
                log.info("latch await..");
            } catch(Exception e) {
                e.printStackTrace();
            }

            AtomicInteger order = new AtomicInteger(0);
            Iterator<Map.Entry<Integer, Future>> it = futureMap.entrySet().iterator();
            while(it.hasNext()) {
                Map.Entry<Integer, Future> e = it.next();
                Integer key = e.getKey();
                Future val = e.getValue();
                try {
                    log.info("Job is done. >>> key = " + key + " , value = " + val.get());
                } catch(Exception e1) {
                    e1.printStackTrace();
                }

            }
        }
    }

    private int initIndex() {
        return 0;
    }
}
