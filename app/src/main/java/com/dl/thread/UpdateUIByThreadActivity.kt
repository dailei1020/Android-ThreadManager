package com.dl.thread

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main2.*
import java.util.concurrent.Executors

/**
 * 更新UI的几种方式
 */
class UpdateUIByThreadActivity : Activity() {
    private lateinit var mThreadUpdateButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        mThreadUpdateButton = threadUpdateButton;
    }

    fun threadUpdateButtonOnclick(view: View) {
        Executors.defaultThreadFactory().newThread(Runnable {

//            //使用View的post方法在线程中更新UI
//            mThreadUpdateButton.post(Runnable {
//                mThreadUpdateButton.text = "在线程中更新UI成功"
//            })
            //使用View的postDelayed方法在线程中延迟一秒更新UI
            mThreadUpdateButton.postDelayed(Runnable {
                mThreadUpdateButton.text = "在线程中更新UI成功"
            }, 1000)
        }).start()
    }
}