package com.richard.study.observer;

public interface Subject {
    public void add(Observer observer); //옵저버 객체 추가
    public void remove(Observer observer); //옵저버 객체 삭제
    public void notifyObservers(); //옵저버 객체들에게 상태 푸시
}
