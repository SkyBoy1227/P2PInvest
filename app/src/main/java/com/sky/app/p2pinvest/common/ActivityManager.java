package com.sky.app.p2pinvest.common;

import android.app.Activity;

import java.util.Stack;

/**
 * Created with Android Studio.
 * 描述: 统一应用程序中所有的Activity的栈管理（单例）
 * 涉及到activity的添加、删除指定、删除当前、删除所有、返回栈大小的方法
 * Date: 2018/3/6
 * Time: 17:15
 *
 * @author 晏琦云
 * @version ${VERSION}
 */
public class ActivityManager {
    /**
     * 单例模式：饿汉式
     */
    private ActivityManager() {

    }

    private static ActivityManager activityManager = new ActivityManager();

    public static ActivityManager getInstance() {
        return activityManager;
    }

    /**
     * 提供栈的对象
     */
    private Stack<Activity> activityStack = new Stack<>();

    /**
     * activity的添加
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (activity != null) {
            activityStack.add(activity);
        }
    }

    /**
     * 删除指定的activity
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        if (activity != null) {
            for (int i = activityStack.size() - 1; i >= 0; i--) {
                Activity currentActivity = activityStack.get(i);
                if (currentActivity.getClass().equals(activity.getClass())) {
                    // 销毁当前的activity
                    currentActivity.finish();
                    // 从栈空间移除
                    activityStack.remove(i);
                }
            }
        }
    }

    /**
     * 删除当前的activity
     */
    public void removeCurrentActivity() {
        // 方式一：
//        Activity activity = activityStack.get(activityStack.size() - 1);
//        activity.finish();
//        activityStack.remove(activityStack.size() - 1);
        // 方式二：
        Activity activity = activityStack.lastElement();
        activity.finish();
        activityStack.remove(activity);
    }

    /**
     * 删除所有的activity
     */
    public void removeAllActivity() {
        for (int i = activityStack.size() - 1; i >= 0; i--) {
            Activity activity = activityStack.get(i);
            activity.finish();
            activityStack.remove(i);
        }
    }

    /**
     * 返回栈的大小
     *
     * @return
     */
    public int getSize() {
        return activityStack.size();
    }
}
