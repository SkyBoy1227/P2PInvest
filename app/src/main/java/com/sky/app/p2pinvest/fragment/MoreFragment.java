package com.sky.app.p2pinvest.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sky.app.p2pinvest.R;
import com.sky.app.p2pinvest.activity.GestureEditActivity;
import com.sky.app.p2pinvest.activity.UserRegisterActivity;
import com.sky.app.p2pinvest.common.AppNetConfig;
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
    @BindView(R.id.rl_more_contact)
    RelativeLayout rlMoreContact;
    @BindView(R.id.tv_more_feedback)
    TextView tvMoreFeedback;
    @BindView(R.id.tv_more_share)
    TextView tvMoreShare;
    @BindView(R.id.tv_more_about)
    TextView tvMoreAbout;
    @BindView(R.id.tv_more_phone)
    TextView tvMorePhone;

    private SharedPreferences sp;

    /**
     * 反馈的部门
     */
    private String department = "不确定";

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
        contactService();
        commitFeedback();
    }

    /**
     * 提交反馈信息
     */
    private void commitFeedback() {
        tvMoreFeedback.setOnClickListener(v -> {
            // 提供一个View
            View view = View.inflate(this.getActivity(), R.layout.view_feedback, null);
            RadioGroup radioGroup = view.findViewById(R.id.rg_feedback);
            EditText editText = view.findViewById(R.id.et_feedback_content);
            radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                RadioButton radioButton = group.findViewById(checkedId);
                // 获取反馈的部门
                department = radioButton.getText().toString();
            });
            new AlertDialog.Builder(this.getActivity())
                    .setView(view)
                    .setPositiveButton("发送", (dialog, which) -> {
                        // 获取反馈的信息
                        String content = editText.getText().toString();
                        // 联网请求服务器，发送反馈信息
                        AsyncHttpClient client = new AsyncHttpClient();
                        String url = AppNetConfig.FEEDBACK;
                        RequestParams params = new RequestParams();
                        params.put("department", department);
                        params.put("content", content);
                        client.post(url, params, new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(String content) {
                                UIUtils.toast("发送反馈信息成功", false);
                            }

                            @Override
                            public void onFailure(Throwable error, String content) {
                                UIUtils.toast("发送反馈信息失败！", false);
                            }
                        });
                    })
                    .setNegativeButton("取消", null)
                    .show();
        });
    }

    /**
     * 联系客服
     */
    private void contactService() {
        rlMoreContact.setOnClickListener(v -> {
            String phone = tvMorePhone.getText().toString();
            new AlertDialog.Builder(this.getActivity())
                    .setTitle("联系客服")
                    .setMessage("是否现在联系客服")
                    .setPositiveButton("确定", (dialog, which) -> {
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:" + phone));
                        startActivity(intent);
                    })
                    .setNegativeButton("取消", null)
                    .show();
        });
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
