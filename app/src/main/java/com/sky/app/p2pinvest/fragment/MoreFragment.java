package com.sky.app.p2pinvest.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
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
import com.sky.app.p2pinvest.activity.AboutInvestActivity;
import com.sky.app.p2pinvest.activity.GestureEditActivity;
import com.sky.app.p2pinvest.activity.UserRegisterActivity;
import com.sky.app.p2pinvest.common.AppNetConfig;
import com.sky.app.p2pinvest.common.BaseActivity;
import com.sky.app.p2pinvest.common.BaseFragment;
import com.sky.app.p2pinvest.util.UIUtils;

import butterknife.BindView;
import cn.sharesdk.onekeyshare.OnekeyShare;

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
        share();
        aboutInvest();
    }

    /**
     * 关于P2P金融
     */
    private void aboutInvest() {
        tvMoreAbout.setOnClickListener(v -> ((BaseActivity) this.getActivity()).goToActivity(AboutInvestActivity.class, null));
    }

    /**
     * 用户分享
     */
    private void share() {
        tvMoreShare.setOnClickListener(v -> showShare());
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle(getString(R.string.app_name));
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl("www.atguigu.com");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("世界上最遥远的距离，是我在if里你在else里，似乎一直相伴又永远分离；\n" +
                "     世界上最痴心的等待，是我当case你是switch，或许永远都选不上自己；\n" +
                "     世界上最真情的相依，是你在try我在catch。无论你发神马脾气，我都默默承受，静静处理。到那时，再来期待我们的finally。");
        // 分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        // oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("www.atguigu.com");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("word天哪，太精辟了");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(this.getActivity());
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
                        if (ActivityCompat.checkSelfPermission(MoreFragment.this.getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            UIUtils.toast("请您同意通话权限后再使用该功能", false);
                            return;
                        }
                        MoreFragment.this.getActivity().startActivity(intent);
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
