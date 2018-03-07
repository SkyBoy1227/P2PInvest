package com.sky.app.p2pinvest.common;

import android.app.Application;
import android.content.Context;
import android.os.Handler;


/**
 * Created with Android Studio.
 * 描述: 自定义Application
 * Date: 2018/3/6
 * Time: 15:46
 *
 * @author 晏琦云
 * @version ${VERSION}
 */
public class MyApplication extends Application {
    // 在整个应用执行过程中，需要提供的变量

    /**
     * 需要使用的上下文对象
     */
    public static Context context;

    /**
     * 需要使用的handler
     */
    public static Handler handler;

    /**
     * 提供主线程对象
     */
    public static Thread mainThread;

    /**
     * 提供主线程对象的id
     */
    public static int mainThreadId;

    @Override
    public void onCreate() {
        super.onCreate();

        context = this.getApplicationContext();
        handler = new Handler();
        // 实例化当前application的线程即为主线程
        mainThread = Thread.currentThread();
        // 获取当前线程的id
        mainThreadId = android.os.Process.myTid();

        // 设置未捕获异常的处理器
//        CrashHandler.getInstance().init();
    }
}
