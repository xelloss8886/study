package com.richard.study.task;

import com.richard.study.shared.SharedObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TestTask implements Runnable{

    private SharedObject sharedObject;

    @Override
    public void run() {
        sharedObject.setThreadName(Thread.currentThread().getName());
        if(!sharedObject.getThreadName().equals(Thread.currentThread().getName())) {
            log.error("", new IllegalArgumentException("This thread name : " + Thread.currentThread().getName() +
                    " , SharedObject name : " + sharedObject.getThreadName()));
        }
        log.info(sharedObject.getThreadName());
    }

    public void setSharedObject(SharedObject sharedObject) {
        this.sharedObject = sharedObject;
    }
}
