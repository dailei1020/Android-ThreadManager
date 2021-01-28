package com.dl.thread.demo;

import java.util.Timer;
import java.util.TimerTask;

/**
 * description:
 * 每一个Timer仅对应唯一一个线程。
 * Timer不保证任务执行的十分精确。
 * Timer类的线程安全的。
 * Created by dailei on 2021/1/28
 */
class TimerDemo {
    public static void main(String[] args) {
        //delayTimeTodo();
        //delayRepeatTodo();
        //delayRepeatTodo2();
        cancelRepeatTask();
    }

    //取消任务
    private void cancelTask() {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                //取消任务，因为任务已经执行，这里不能停下来，仍会执行
                //任务开始后不能取消
                cancel();
                System.out.println("Run...");
            }
        };
        timer.schedule(timerTask, 1000);
        //取消任务，因为任务延迟一秒执行，还未开始就可以取消了
        timer.cancel();
    }

    //取消重复任务
    private static void cancelRepeatTask() {
        Timer timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            int count = 0;

            @Override
            public void run() {
                //取消任务。会打印，Run :0,Run :1,Run :2
                //任务开始运行后不可以取消，重复执行的后续任务可以取消
                //虽然执行了3行后没有打印了，但是Java虚拟机仍然不会停止这个线程。
//                if (count == 2) {
//                    cancel();
//                }

                //取消任务。会打印，Run :0,Run :1,Run :2.
                //线程停止
                //    public void cancel() {
                //        synchronized(queue) {
                //            thread.newTasksMayBeScheduled = false;
                //            queue.clear();
                //            queue.notify();  // In case queue was already empty.
                //        }
                //    }
                //queue.clear()实际上在notify之前做了清空数组的操作，然后notify唤醒之前的wait，线程结束退出。
                if (count == 2) {
                    timer.cancel();
                }

                System.out.println("Run :" + count++);
            }
        };
        timer.schedule(timerTask, 0, 1000);

    }


    private static void delayTimeTodo() {
        Timer timer = new Timer();

        //在指定的延迟执行任务
        timer.schedule(new TimerTask() {
            final long startTime = System.currentTimeMillis();

            @Override
            public void run() {
                System.out.println("Schedule time:" + (scheduledExecutionTime() - startTime));
                System.out.println("Run at:" + (System.currentTimeMillis() - startTime));

            }
        }, 1000);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 900);
    }

    /**
     * 延迟指定时间重复执行
     * timer.schedule 会因为有其他的线程任务干扰而延迟后面重复执行的任务，后面所有的任务都会延迟执行
     */
    private static void delayRepeatTodo() {
        Timer timer = new Timer();

        //在指定的延迟执行任务,延迟0秒每隔1秒重复执行
        timer.schedule(new TimerTask() {
            final long startTime = System.currentTimeMillis();

            @Override
            public void run() {
                System.out.println("Schedule time:" + (scheduledExecutionTime() - startTime));
                System.out.println("Run at:" + (System.currentTimeMillis() - startTime));

            }
        }, 0, 1000);

    }

    /**
     * 延迟指定时间重复执行
     * timer.scheduleAtFixedRate 不会因为有其他的线程任务干扰而延迟后面重复执行的任务
     */
    private static void delayRepeatTodo2() {
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            final long startTime = System.currentTimeMillis();

            @Override
            public void run() {
                System.out.println("Schedule time:" + (scheduledExecutionTime() - startTime));
                System.out.println("Run at:" + (System.currentTimeMillis() - startTime));
            }
        }, 0, 1000);

    }
}
