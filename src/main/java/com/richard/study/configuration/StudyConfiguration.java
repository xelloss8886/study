package com.richard.study.configuration;

import com.richard.study.parallel.ConsumerTask;
import com.richard.study.shared.SharedObject;
import org.apache.tomcat.util.collections.ConcurrentCache;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.task.TaskExecutor;
import org.springframework.core.task.support.TaskExecutorAdapter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;
import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_SINGLETON;

@Configuration
public class StudyConfiguration implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Bean
    @Scope(value = SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public Map<String, Object> testMap() {
        return new ConcurrentHashMap<>();
    }

    @Bean
    @Scope(value = SCOPE_PROTOTYPE)
    public String testString() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHhmmssSSS"));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Bean
    public WebClient webClient() {
        return WebClient
                .builder()
                .baseUrl("http://localhost:8080/webflux")
                .defaultCookie("cookieKey", "cookieValue")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE)
                .defaultUriVariables(Collections.singletonMap("url", "http://localhost:8080/webflux"))
                .build();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        return new ConcurrentTaskExecutor(Executors.newFixedThreadPool(5));
    }

    @Bean
    public SharedObject sharedObject() {
        return new SharedObject();
    }
}
