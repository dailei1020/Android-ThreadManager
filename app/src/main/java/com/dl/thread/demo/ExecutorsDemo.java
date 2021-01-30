package com.dl.thread.demo;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * description:
 * Created by dailei on 2021/1/30
 */
class ExecutorsDemo {
    public static void main(String[] args) {
//        threadFactoryDemo();
//        executorServiceDemo();
        executorServiceDemo2();

    }

    private static void executorServiceDemo() {
        //使用线程池运行Runnable对象
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("Run on thread:"+Thread.currentThread().getName());
            }
        });
    }

    /**
     * 带有返回值的线程池
     */
    private static void executorServiceDemo1() {
        //使用线程池运行Runnable对象
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                //1 抛出异常
                if(true) {
                    throw  new RuntimeException("hello callable");
                }
                return Thread.currentThread().getName();
            }
        });

        try {
            //此方法会阻塞主线程，直到拿到返回值
            System.out.println("Result:"+future.get());
        } catch (ExecutionException e) {
            //这里将会打印1抛出的异常
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 指定延时执行一个Runnable
     */
    private static void executorServiceDemo2() {
        //使用线程池运行Runnable对象
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);
        scheduledExecutorService.schedule(new Runnable() {
            final long startTime = System.currentTimeMillis();
            @Override
            public void run() {
                System.out.println("Run at:"+(System.currentTimeMillis() - startTime));

            }
        },1000, TimeUnit.MILLISECONDS); //延迟一秒执行
//        //按照一定的频率执行
//        scheduledExecutorService.scheduleAtFixedRate()；
//        //按照延迟一定的频率执行
//        scheduledExecutorService.scheduleWithFixedDelay();

        //关闭线程池，等待任务完成后关闭
        scheduledExecutorService.shutdown();

        //关闭线程池，立即关闭，未执行的任务将不会执行
        scheduledExecutorService.shutdownNow();

        //缓存线程池和固定线程数量线程池的区别
        //newCachedThreadPool，可变大小的线程池，有任务就会有新的线程
        ExecutorService executorService = Executors.newCachedThreadPool();

        //固定大小的线程池，如果有任务没有分配到线程，将等待有线程的时候再执行
        ExecutorService executorService1 = Executors.newFixedThreadPool(5);

    }

    /**
     * 线程工厂的使用
     */
    private static void threadFactoryDemo() {

        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        Thread thread = threadFactory.newThread(new Runnable() {
            @Override
            public void run() {
                //to do
            }
        });
    }
}
