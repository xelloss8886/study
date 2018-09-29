package com.richard.study.controller;

import com.richard.study.service.FutureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FutureController {

    @Autowired
    private FutureService futureService;

    @GetMapping("/future")
    public String test() {
        return futureService.doTask();
    }
}
