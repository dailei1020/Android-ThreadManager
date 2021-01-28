package com.dl.thread.demo;

import androidx.annotation.Nullable;

/**
 * description:线程局部变量
 * Created by dailei on 2021/1/28
 */
class ThreadLocalDemo {
    public static void main(String[] args) {
        threadLocalDemo();

    }

    /**
     * 打印结果:
     * main value:-1
     * main new value:5
     * main removed and value:-1
     * Thread-0 value:-1
     * Thread-0 new value:5
     * Thread-0 removed and value:-1
     *
     * 分析：ThreadLocal中主线程和子线程的值不是同一个
     */
    private static void threadLocalDemo() {
        ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>() {
            @Nullable
            @Override
            protected Integer initialValue() {
                //提供初始值
                return -1;
            }
        };

        //获取值
        System.out.println(Thread.currentThread().getName() + " value:" + threadLocal.get());

        //设置值
        threadLocal.set(5);
        System.out.println(Thread.currentThread().getName() + " new value:" + threadLocal.get());

        //移除值
        threadLocal.remove();
        System.out.println(Thread.currentThread().getName() + " removed and value:" + threadLocal.get());

        threadLocal.set(10);

        new Thread(){
            @Override
            public void run() {
                //获取值
                System.out.println(Thread.currentThread().getName() + " value:" + threadLocal.get());

                //设置值
                threadLocal.set(5);
                System.out.println(Thread.currentThread().getName() + " new value:" + threadLocal.get());

                //移除值
                threadLocal.remove();
                System.out.println(Thread.currentThread().getName() + " removed and value:" + threadLocal.get());
            }
        }.start();

       }

}
