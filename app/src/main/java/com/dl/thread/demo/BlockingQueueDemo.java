package com.dl.thread.demo;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;

/**
 * description:阻塞队列的使用
 * Created by dailei on 2021/1/30
 */
class BlockingQueueDemo {
    public static void main(String[] args) {
        BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<Integer>(3);
        Executors.defaultThreadFactory().newThread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; ; i++) {
                    try {
                        Thread.sleep(100);
//                        //抛出异常的添加元素的方法
//                        blockingQueue.add(i);
                        //无限期阻塞当前线程添加元素的方法
                        blockingQueue.put(i);
                        System.out.println("Make value:" + i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        Executors.defaultThreadFactory().newThread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; ; i++) {
                    try {
                        Thread.sleep(100);
                        //抛出异常的移除元素的方法
                        //int value = blockingQueue.remove();

                        //无限期阻塞当前线程移除元素的方法
                        int value = blockingQueue.take();
                        System.out.println("Get value:" + value);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
