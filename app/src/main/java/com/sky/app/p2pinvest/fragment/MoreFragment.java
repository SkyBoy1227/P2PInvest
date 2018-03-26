package com.sky.app.p2pinvest.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.loopj.android.http.RequestParams;
import com.sky.app.p2pinvest.R;
import com.sky.app.p2pinvest.activity.UserRegisterActivity;
import com.sky.app.p2pinvest.common.BaseActivity;
import com.sky.app.p2pinvest.common.BaseFragment;
import com.sky.app.p2pinvest.util.UIUtils;

import butterknife.BindView;

/**
 * Created with Android Studio.
 * 描述: 更多页
 * Date: 2018/3/5
 * Time: 21:06
 *
 * @author 晏琦云
 * @version ${VERSION}
 */
public class MoreFragment extends BaseFragment {
    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_title_settings)
    ImageView ivTitleSettings;
    @BindView(R.id.tv_more_register)
    TextView tvMoreRegister;
    @BindView(R.id.toggle_more)
    ToggleButton toggleMore;
    @BindView(R.id.tv_more_reset)
    TextView tvMoreReset;
    @BindView(R.id.rl_contact)
    RelativeLayout rlContact;
    @BindView(R.id.tv_more_feedback)
    TextView tvMoreFeedback;
    @BindView(R.id.tv_more_share)
    TextView tvMoreShare;
    @BindView(R.id.tv_more_about)
    TextView tvMoreAbout;

    private SharedPreferences sp;

    @Override
    protected RequestParams getParams() {
        return null;
    }

    @Override
    protected String getUrl() {
        return null;
    }

    @Override
    protected void initData(String content) {
        // 初始化SharedPreferences
        sp = this.getActivity().getSharedPreferences("secret_protected", Context.MODE_PRIVATE);
        userRegister();
        getGestureStatus();
        setGesturePassword();
    }

    /**
     * 获取当前设置手势密码的ToggleButton的状态
     */
    private void getGestureStatus() {
        boolean isOpen = sp.getBoolean("isOpen", false);
        toggleMore.setChecked(isOpen);
    }

    /**
     * 设置手势密码
     */
    private void setGesturePassword() {
        toggleMore.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                UIUtils.toast("开启了手势密码", false);
                sp.edit().putBoolean("isOpen", true).apply();
            } else {
                UIUtils.toast("关闭了手势密码", false);
                sp.edit().putBoolean("isOpen", false).apply();
            }
        });
    }

    /**
     * 用户注册
     */
    private void userRegister() {
        tvMoreRegister.setOnClickListener(v -> ((BaseActivity) this.getActivity()).goToActivity(UserRegisterActivity.class, null));
    }

    /**
     * 初始化Title
     */
    @Override
    protected void initTitle() {
        ivTitleBack.setVisibility(View.GONE);
        tvTitle.setText("更多");
        ivTitleSettings.setVisibility(View.GONE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_more;
    }
}
