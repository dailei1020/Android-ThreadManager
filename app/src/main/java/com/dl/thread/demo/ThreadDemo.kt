package com.dl.thread.demo

/**
 * description:
 * Created by dailei on 2021/1/26
 */
object ThreadDemo {
    @JvmStatic
    fun main(args: Array<String>) {
        Task().start()
        Task().start()
    }

    internal class Count {
        private var lock: Any = Object()

        /**
         * 无同步代码块
         * 造成数据异常
         *
         * @throws InterruptedException
         */
        @Throws(InterruptedException::class)
        fun add() {
            val temp = value
            Thread.sleep(100)
            value = temp + 1
            println("value is = $value")
        }

        /**
         * 同步代码块
         *
         * @throws InterruptedException
         */
        @Throws(InterruptedException::class)
        fun add1() {
            synchronized(lock) {
                val temp = value
                Thread.sleep(100)
                value = temp + 1
                println("value is = $value")
            }
        }

        /**
         * 同步方法
         *
         * @throws InterruptedException
         */
        @Synchronized
        @Throws(InterruptedException::class)
        fun add2() {
            val temp = value
            Thread.sleep(100)
            value = temp + 1
            println("value is = $value")
        }


        /**
         * 同步类
         */
        @Throws(InterruptedException::class)
        fun add4() {
            synchronized(Count::class.java) {
                val temp = value
                Thread.sleep(100)
                value = temp + 1
                println("value is = $value")
            }
        }

        companion object {
            var value = 0

            /**
             * 同步静态方法
             *
             * @throws InterruptedException
             */
            @Synchronized
            @Throws(InterruptedException::class)
            fun add3() {
                val temp = value
                Thread.sleep(100)
                value = temp + 1
                println("value is = $value")
            }
        }
    }

    internal class Task : Thread() {
        override fun run() {
            try {
                count.add1()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }

        companion object {
            var count = Count()
        }
    }

    /**
     * 如果调用此类，创建了两个Count,互不影响，此时add1()的方法同步锁失效
     * 如果调用静态同步锁方法，同步方法生效
     */
    internal class Task1 : Thread() {
        override fun run() {
            try {
                val count = Count()
                count.add1()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }
}