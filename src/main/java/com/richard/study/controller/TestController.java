package com.richard.study.controller;

import com.richard.study.shared.SharedObject;
import com.richard.study.task.TestTask;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.core.task.support.TaskExecutorAdapter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RestController
public class TestController {


    @Autowired
    private Map<String, Object> testMap;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private WebClient webClient;

    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    private TestTask testTask;

    private static final int MAX_COUNT = 100;

    @Autowired
    private SharedObject sharedObject;

    @GetMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public void test() {
        Mono<String> mono = webClient.get().retrieve().bodyToMono(String.class);
        mono.subscribe((e) -> System.out.println(e));

        for(int i = 0 ; i < MAX_COUNT ; i++) {
            testTask.setSharedObject(sharedObject);
            taskExecutor.execute(testTask);
        }
        //prototype test
        String t = (String) applicationContext.getBean("testString");
    }

    @GetMapping("/webflux")
    public ResponseEntity<?> webflux() {
        log.info("webflux!!");
        return ResponseEntity.ok("good");
    }
}
