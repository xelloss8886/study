package com.richard.study.observer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

public class FutureContext implements Observer {

    private Map<String, Future<String>> map = new HashMap<>();

    @Override
    public void setFutureTask(String key, Future future) {
        map.putIfAbsent(key, future);
    }

    public String getValueFromFuture(String key) {
        String result = null;
        try {
            result = map.get(key).get();
        }catch(Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return result;
    }

    public Future getFuture(String key) {
        return map.get(key);
    }
}
