package com.dl.thread.demo;

/**
 * description:
 * Created by dailei on 2021/1/26
 */
public class ThreadDemo2 {

    static boolean isWaiting = true;
    synchronized static void get() throws InterruptedException {
        if(isWaiting) {
            System.out.println("waiting");
            //作用是挂起当前线程，释放获取到的锁，直到别的线程调用了这个对象的notify或notifyAll方法。
            ThreadDemo2.class.wait();
            System.out.println("ready to get");
        } else {
            System.out.println("get");
        }
    }

    synchronized static void set() throws InterruptedException {
        if(isWaiting) {
            System.out.println("Prepare");
            Thread.sleep(100);
            //notify:作用是唤醒因调用wait挂起的线程，如果有过个线程，随机唤醒一个。
            //notifyAll：Object类的方法。作用是唤醒全部因调用wait挂起的线程。
            ThreadDemo2.class.notify();
            System.out.println("ready and notify");
        } else {
            System.out.println("ready");
        }
    }

    public static void main(String[] args) {
        new Task1().start();
        new Task2().start();
    }

    static class Task1 extends Thread{
        @Override
        public void run() {
            try {
                get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Task2 extends Thread{
        @Override
        public void run() {
            try {
                set();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
