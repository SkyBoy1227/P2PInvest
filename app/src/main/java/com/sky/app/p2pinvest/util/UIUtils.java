package com.sky.app.p2pinvest.util;

import android.content.Context;
import android.os.Handler;
import android.os.Process;
import android.view.View;
import android.widget.Toast;

import com.sky.app.p2pinvest.common.MyApplication;

/**
 * Created with Android Studio.
 * 描述: 专门提供为处理一些UI相关的问题而创建的工具类，
 * 提供资源获取的通用方法，避免每次都写重复的代码获取结果。
 * Date: 2018/3/6
 * Time: 16:09
 *
 * @author 晏琦云
 * @version ${VERSION}
 */
public class UIUtils {
    public static Context getContext() {
        return MyApplication.context;
    }

    public static Handler getHandler() {
        return MyApplication.handler;
    }

    /**
     * 返回指定colorId对应的颜色值
     *
     * @param colorId
     * @return
     */
    public static int getColor(int colorId) {
        return getContext().getResources().getColor(colorId);
    }

    /**
     * 加载指定viewId的视图对象，并返回
     *
     * @param viewId
     * @return
     */
    public static View getView(int viewId) {

        return View.inflate(getContext(), viewId, null);
    }

    public static String[] getStringArr(int strArrId) {
        return getContext().getResources().getStringArray(strArrId);
    }

    /**
     * 将dp转化为px
     */
    public static int dp2px(int dp) {
        // 获取手机密度
        float density = getContext().getResources().getDisplayMetrics().density;
        // 实现四舍五入
        return (int) (dp * density + 0.5f);
    }

    public static int px2dp(int px) {
        // 获取手机密度
        float density = getContext().getResources().getDisplayMetrics().density;
        // 实现四舍五入
        return (int) (px / density + 0.5f);
    }

    /**
     * 保证runnable中的操作在主线程中执行
     *
     * @param runnable
     */
    public static void runOnUiThread(Runnable runnable) {
        if (isMainThread()) {
            runnable.run();
        } else {
            UIUtils.getHandler().post(runnable);
        }
    }

    /**
     * 判断当前线程是否是主线程
     *
     * @return
     */
    private static boolean isMainThread() {
        // 当前线程的id
        int currentThreadId = Process.myTid();
        return currentThreadId == MyApplication.mainThreadId;
    }

    /**
     * 吐司
     *
     * @param message
     * @param isLengthLong
     */
    public static void toast(String message, boolean isLengthLong) {
        Toast.makeText(getContext(), message, isLengthLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
    }
}
