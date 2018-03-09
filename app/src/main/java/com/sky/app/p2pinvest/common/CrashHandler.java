package com.sky.app.p2pinvest.common;

import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.sky.app.p2pinvest.util.UIUtils;

/**
 * Created with Android Studio.
 * 描述: 程序中的未捕获的全局异常的捕获（单例）
 * Date: 2018/3/7
 * Time: 10:11
 *
 * @author 晏琦云
 * @version ${VERSION}
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static CrashHandler crashHandler = null;

    /**
     * 系统默认的处理未捕获异常的处理器
     */
    private Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler;

    /**
     * 单例模式：（懒汉式）
     * 本身实例化未捕获异常的处理器的操作就是系统在一个单独的线程中完成的，
     * 所以不涉及到多线程的问题，所以使用懒汉式更好！
     */
    private CrashHandler() {

    }

    public static CrashHandler getInstance() {
        if (crashHandler == null) {
            crashHandler = new CrashHandler();
        }
        return crashHandler;
    }

    public void init() {
        defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 将当前类设置为出现未捕获异常的处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 一旦系统出现未捕获的异常，就会调用如下的回调方法
     *
     * @param t
     * @param e
     */
    @Override
    public void uncaughtException(Thread t, Throwable e) {
//        Log.e("TAG", "亲，出现了未捕获的异常！" + e.getMessage());

        MyApplication.singleThreadPool.execute(() -> {
            // prepare()和loop()之间的操作就是在主线程中执行的！
            // 在android系统中，默认情况下，一个线程中是不可以调用Looper进行消息的处理的。除非是主线程
            Looper.prepare();
            Toast.makeText(UIUtils.getContext(), "亲，出现了未捕获的异常！", Toast.LENGTH_SHORT).show();
            Looper.loop();
        });

        collectionException(e);
        try {
            Thread.sleep(2000);
            // 移除当前的activity
            ActivityManager.getInstance().removeCurrentActivity();
            // 结束当前的进程
            android.os.Process.killProcess(android.os.Process.myPid());
            // 结束虚拟机
            System.exit(0);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * 收集异常信息
     *
     * @param e
     */
    private void collectionException(Throwable e) {
        String message = e.getMessage();
        // 收集具体的客户的手机、系统的信息
        String phoneMessage = Build.DEVICE + " : " + Build.MODEL + " : " + Build.PRODUCT + " : " + Build.VERSION.SDK_INT;
        // 发送给后台此异常信息
        MyApplication.singleThreadPool.execute(() -> {
            // 需要按照指定的url，访问后台的sevlet,将异常信息发送过去
            Log.e("TAG", "exception = " + message);
            Log.e("TAG", "phoneMessage = " + phoneMessage);
        });
        MyApplication.singleThreadPool.shutdown();
    }
}
