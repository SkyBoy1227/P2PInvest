package com.sky.app.p2pinvest.common;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sky.app.p2pinvest.util.UIUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created with Android Studio.
 * 描述: 通用的Fragment基类
 * Date: 2018/3/9
 * Time: 16:41
 *
 * @author 晏琦云
 * @version ${VERSION}
 */
public abstract class BaseFragment extends Fragment {
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = UIUtils.getView(getLayoutId());
        unbinder = ButterKnife.bind(this, view);
        initTitle();
        initData();
        return view;
    }

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化Title
     */
    protected abstract void initTitle();

    /**
     * 提供布局
     *
     * @return
     */
    protected abstract int getLayoutId();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
