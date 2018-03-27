package com.sky.app.p2pinvest.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.loopj.android.http.RequestParams;
import com.sky.app.p2pinvest.R;
import com.sky.app.p2pinvest.activity.GestureEditActivity;
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
        sp = this.getActivity().getSharedPreferences("secret_protect", Context.MODE_PRIVATE);
        userRegister();
        getGestureStatus();
        setGesturePassword();
        resetGesturePassword();
    }

    /**
     * 重置手势密码
     */
    private void resetGesturePassword() {
        tvMoreReset.setOnClickListener(v -> {
            boolean checked = toggleMore.isChecked();
            if (checked) {
                ((BaseActivity) this.getActivity()).goToActivity(GestureEditActivity.class, null);
            } else {
                UIUtils.toast("手势密码操作已关闭，请开启后再设置", false);
            }
        });
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
//                UIUtils.toast("开启了手势密码", false);
//                sp.edit().putBoolean("isOpen", true).apply();
                String inputCode = sp.getString("inputCode", "");
                if (TextUtils.isEmpty(inputCode)) {
                    new AlertDialog.Builder(MoreFragment.this.getActivity())
                            .setTitle("设置手势密码")
                            .setMessage("是否现在设置手势密码")
                            .setPositiveButton("确定", (dialog, which) -> {
                                sp.edit().putBoolean("isOpen", true).apply();
//                                toggleMore.setChecked(true);
                                // 开启新的activity:
                                ((BaseActivity) MoreFragment.this.getActivity()).goToActivity(GestureEditActivity.class, null);
                            })
                            .setNegativeButton("取消", (dialog, which) -> {
                                sp.edit().putBoolean("isOpen", false).apply();
                                toggleMore.setChecked(false);
                            })
                            .show();
                } else {
                    UIUtils.toast("开启了手势密码", false);
                    sp.edit().putBoolean("isOpen", true).apply();
//                    toggleMore.setChecked(true);
                }
            } else {
                UIUtils.toast("关闭了手势密码", false);
                sp.edit().putBoolean("isOpen", false).apply();
//                toggleMore.setChecked(false);
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
