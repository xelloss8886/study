package com.richard.study.parallel;

import com.richard.study.entity.SalaryEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class ConsumerTask implements Callable<Boolean> {

    private List<Object> list = new ArrayList<>();

    private Object notifier = new Object();

    private CountDownLatch latch = null;

    private ThreadLocal<List<Object>> local = new ThreadLocal<>();

    public ConsumerTask(CountDownLatch latch) {
        this.latch = latch;
    }

    public void setData(List<Object> list) {
        log.info("Set DATA!");
        this.list = list;
    }

    @Override
    public Boolean call() {
        log.info("consumer task list size : " + list.size() + " > doTask");

        while(true) {
            try {
                //TODO : list size가 0 이면 각 Thread는 대기상태로 들어가야함
                ListIterator listIterator = list.listIterator();
                while(listIterator.hasNext()) {
                    Object obj = listIterator.next();
                    if(obj instanceof String) {
                        String instance = String.valueOf(obj);
                        log.info(instance);
                    } else if (obj instanceof SalaryEntity) {
                        SalaryEntity entity = (SalaryEntity) obj;
                        log.info("Thread name " + Thread.currentThread().getName() + ", entity : " + entity.toString());
                    }
                }
                latch.countDown();
                return true;
            } catch (RuntimeException e) {
                log.error("", e);
            }
        }
    }
}
