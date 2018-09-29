package com.richard.study.singleton;

public class Singleton2 {

    private Singleton2() {}

    private static class Singleton2Holder {
        private static final Singleton2 INSTANCE = new Singleton2();
    }

    public static Singleton2 getInstance() {
        return Singleton2Holder.INSTANCE;
    }
}
