package com.richard.study;

import com.richard.study.parallel.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import reactor.core.publisher.Hooks;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@SpringBootApplication(scanBasePackages = "com.richard.study")
public class StudyApplication {
    public static void main(String[] args) {
        SpringApplication.run(StudyApplication.class, args);
    }

    @Autowired
    private Producer producer;

    @Autowired
    private Callable consumerManager;

    @Autowired
    private Callable fluxConsumerManager;

    @Bean
    public BlockingQueue queue() {
        return new LinkedBlockingQueue();
    }

    @Bean
    public CommandLineRunner runner() {
        Hooks.onOperatorDebug();
        return (a) -> {
            TaskExecutor taskExecutor = new ConcurrentTaskExecutor(Executors.newSingleThreadExecutor());
            taskExecutor.execute(producer);
            ConcurrentTaskExecutor consumerExecutor = new ConcurrentTaskExecutor(Executors.newSingleThreadExecutor());
            consumerExecutor.submit(fluxConsumerManager);
        };
    }
}
