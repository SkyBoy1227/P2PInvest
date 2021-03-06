package com.sky.app.p2pinvest.common;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Process;
import android.support.test.espresso.core.internal.deps.guava.util.concurrent.ThreadFactoryBuilder;

import com.mob.MobSDK;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


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

    /**
     * 线程池
     */
    public static ExecutorService singleThreadPool;

    @Override
    public void onCreate() {
        super.onCreate();

        context = this.getApplicationContext();
        handler = new Handler();
        // 实例化当前application的线程即为主线程
        mainThread = Thread.currentThread();
        // 获取当前线程的id
        mainThreadId = Process.myTid();

        // 手动创建线程池
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("my-pool-%d").build();
        singleThreadPool = new ThreadPoolExecutor(5, 10,
                100L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

        // 设置未捕获异常的处理器
//        CrashHandler.getInstance().init();

        // 初始化ShareSDK
        MobSDK.init(this);
    }
}
