package com.sky.app.p2pinvest.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sky.app.p2pinvest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created with Android Studio.
 * 描述: 主页
 * Date: 2018/3/5
 * Time: 21:01
 *
 * @author 晏琦云
 * @version ${VERSION}
 */
public class HomeFragment extends Fragment {
    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_title_settings)
    ImageView ivTitleSettings;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_home, null);
        unbinder = ButterKnife.bind(this, view);
        initTitle();
        return view;
    }

    /**
     * 初始化Title
     */
    private void initTitle() {
        ivTitleBack.setVisibility(View.GONE);
        tvTitle.setText("首页");
        ivTitleSettings.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
