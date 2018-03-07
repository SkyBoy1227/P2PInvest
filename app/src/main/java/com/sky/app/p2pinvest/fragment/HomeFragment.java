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
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.sky.app.p2pinvest.R;
import com.sky.app.p2pinvest.common.AppNetConfig;
import com.sky.app.p2pinvest.util.UIUtils;

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
        // context实例：activity
//        View view = View.inflate(getActivity(), R.layout.fragment_home, null);
        // context实例：application
        View view = UIUtils.getView(R.layout.fragment_home);
        unbinder = ButterKnife.bind(this, view);
        initTitle();
        initData();
        return view;
    }

    /**
     * 初始化数据
     */
    private void initData() {
        AsyncHttpClient client = new AsyncHttpClient();
        // 访问的url
        String url = AppNetConfig.INDEX;
        client.post(url,new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(String content) {
                // 200：响应成功
                // 解析json数据
            }

            @Override
            public void onFailure(Throwable error, String content) {
                // 响应失败
                Toast.makeText(UIUtils.getContext(), "联网获取数据失败", Toast.LENGTH_SHORT).show();
            }
        });
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
