package com.sky.app.p2pinvest.adapter;

import android.content.Context;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created with Android Studio.
 * 描述: ${DESCRIPTION}
 * Date: 2018/3/13
 * Time: 17:26
 *
 * @author 晏琦云
 * @version ${VERSION}
 */
public abstract class BaseHolder<T> {
    private View rootView;
    private T data;

    public BaseHolder(Context context) {
        rootView = initView(context);
        rootView.setTag(this);
        ButterKnife.bind(this, rootView);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
        refreshData();
    }

    /**
     * 装配数据
     */
    protected abstract void refreshData();

    public View getRootView() {
        return rootView;
    }

    /**
     * 提供具体的item layout的布局
     *
     * @param context
     * @return
     */
    protected abstract View initView(Context context);
}
