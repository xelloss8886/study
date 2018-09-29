package com.richard.study.observer;

import java.util.concurrent.Future;

public interface Observer {

    void setFutureTask(String key, Future future);
}
