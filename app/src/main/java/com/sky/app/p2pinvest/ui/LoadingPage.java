package com.sky.app.p2pinvest.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.sky.app.p2pinvest.R;
import com.sky.app.p2pinvest.util.UIUtils;

/**
 * Created with Android Studio.
 * 描述: 通用的加载页面
 * Date: 2018/3/11
 * Time: 13:21
 *
 * @author 晏琦云
 * @version ${VERSION}
 */
public abstract class LoadingPage extends FrameLayout {

    // 定义4种不同的显示状态
    /**
     * 正在加载
     */
    private static final int STATE_LOADING = 1;

    /**
     * 加载失败
     */
    private static final int STATE_ERROR = 2;

    /**
     * 加载成功，但数据为空
     */
    private static final int STATE_EMPTY = 3;

    /**
     * 加载成功
     */
    private static final int STATE_SUCCESS = 4;

    /**
     * 默认情况下，当前状态为正在加载
     */
    private int stateCurrent = STATE_LOADING;

    // 提供4种不同的界面

    private View loadingView;
    private View errorView;
    private View emptyView;
    private View successView;

    /**
     * 提供布局显示的参数
     */
    private ViewGroup.LayoutParams params;

    public LoadingPage(@NonNull Context context) {
        this(context, null);
    }

    public LoadingPage(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingPage(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        // 实例化view
        params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        if (loadingView == null) {
            // 加载布局
            loadingView = UIUtils.getView(R.layout.page_loading);
            // 添加到当前的frameLayout中
            addView(loadingView, params);
        }
        if (errorView == null) {
            // 加载布局
            errorView = UIUtils.getView(R.layout.page_error);
            // 添加到当前的frameLayout中
            addView(errorView, params);
        }
        if (emptyView == null) {
            // 加载布局
            emptyView = UIUtils.getView(R.layout.page_empty);
            // 添加到当前的frameLayout中
            addView(emptyView, params);
        }
        showSafePage();
    }

    /**
     * 保证如下的操作在主线程中执行的：更新界面
     */
    private void showSafePage() {
        UIUtils.runOnUiThread(this::showPage);
    }

    /**
     * 显示界面
     */
    private void showPage() {
        // 根据当前state_current的值，决定显示哪个view
        loadingView.setVisibility(stateCurrent == STATE_LOADING ? VISIBLE : INVISIBLE);
        errorView.setVisibility(stateCurrent == STATE_ERROR ? VISIBLE : INVISIBLE);
        emptyView.setVisibility(stateCurrent == STATE_EMPTY ? VISIBLE : INVISIBLE);
        if (successView == null) {
            successView = UIUtils.getView(layoutId());
            addView(successView, params);
        }
        successView.setVisibility(stateCurrent == STATE_SUCCESS ? VISIBLE : INVISIBLE);
    }

    protected abstract int layoutId();
}
