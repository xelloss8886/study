package com.richard.study.observer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SubjectImpl implements Subject {

    List<Observer> list = new CopyOnWriteArrayList<>();

    @Override
    public void add(Observer observer) {
        list.add(observer);
    }

    @Override
    public void remove(Observer observer) {
        if(list.contains(observer)) list.remove(observer);
    }

    @Override
    public void notifyObservers() {

    }
}
