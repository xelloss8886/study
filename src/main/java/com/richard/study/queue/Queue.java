package com.richard.study.queue;

public class Queue {

    private Object[] queue;
    private int size = 0;
    private int rear = -1;
    private int front = -1;

    Queue(int size) {
        this.size = size;
        //사이즈 만큼 큐 생성
        this.queue = new Object[size];
    }

    public void enQueue(Object element) {

        //큐 포화상태 검사
        if (isFull()) {
            throw new QueueOverflow();
        }

        queue[++rear] = element;
    }

    public Object deQueue() {

        //공백큐 검사
        if (isEmpty()) {
            throw new QueueUnderflow();
        }

        ++front;
        Object temp = queue[front];

        //help gc;
        queue[front] = null;

        //비어있다면 다시 원점으로 초기화
        if (isEmpty()) {
            rear = -1;
            front = -1;
        }

        return temp;
    }

    public boolean isFull() {
        return rear == size-1 ? true : false;
    }

    public boolean isEmpty() {
        return front == rear ? true : false;
    }

    public int getSize() {
        return size;
    }


    static class QueueOverflow extends RuntimeException {

    }

    static class QueueUnderflow extends RuntimeException {

    }
}
