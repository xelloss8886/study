package com.richard.study.singleton;

public class Singleton {

    private volatile static Singleton INSTANCE = null;

    private Singleton() {}

    public Singleton getInstance() {
        if(INSTANCE == null) {
            synchronized (this) {
                if(INSTANCE == null) {
                    INSTANCE = new Singleton();
                }
            }
        }
        return INSTANCE;
    }
}
