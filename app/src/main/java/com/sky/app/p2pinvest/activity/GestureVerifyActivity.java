package com.sky.app.p2pinvest.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sky.app.p2pinvest.R;
import com.sky.app.p2pinvest.common.BaseActivity;
import com.sky.gesturelock.widget.GestureContentView;
import com.sky.gesturelock.widget.GestureDrawline;

import butterknife.BindView;

/**
 * Created with Android Studio.
 * 描述: 验证手势密码页面
 * Date: 2018/3/27
 * Time: 15:57
 *
 * @author 晏琦云
 * @version ${VERSION}
 */
public class GestureVerifyActivity extends BaseActivity implements View.OnClickListener {
    /**
     * 手机号码
     */
    public static final String PARAM_PHONE_NUMBER = "PARAM_PHONE_NUMBER";
    /**
     * 意图
     */
    public static final String PARAM_INTENT_CODE = "PARAM_INTENT_CODE";

    @BindView(R.id.top_layout)
    RelativeLayout mTopLayout;
    @BindView(R.id.text_title)
    TextView mTextTitle;
    @BindView(R.id.text_cancel)
    TextView mTextCancel;
    @BindView(R.id.user_logo)
    ImageView mImgUserLogo;
    @BindView(R.id.text_phone_number)
    TextView mTextPhoneNumber;
    @BindView(R.id.text_tip)
    TextView mTextTip;
    @BindView(R.id.gesture_container)
    FrameLayout mGestureContainer;
    @BindView(R.id.text_forget_gesture)
    TextView mTextForget;
    @BindView(R.id.text_other_account)
    TextView mTextOther;

    private GestureContentView mGestureContentView;
    private String mParamPhoneNumber;
    private long mExitTime = 0;
    private int mParamIntentCode;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void initData() {
        obtainExtraData();

        setUpViews();

        setUpListeners();
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gesture_verify;
    }

    private void obtainExtraData() {
        Bundle bundle = getIntent().getBundleExtra("data");
        mParamPhoneNumber = bundle.getString(PARAM_PHONE_NUMBER);
        mParamIntentCode = bundle.getInt(PARAM_INTENT_CODE, 0);
        mSharedPreferences = this.getSharedPreferences("secret_protect", Context.MODE_PRIVATE);
    }

    private void setUpViews() {
        mTextPhoneNumber.setText(getProtectedMobile(mParamPhoneNumber));
        String inputCode = mSharedPreferences.getString("inputCode", "1235789");
        // 初始化一个显示各个点的viewGroup
        mGestureContentView = new GestureContentView(this, true, inputCode,
                new GestureDrawline.GestureCallBack() {

                    @Override
                    public void onGestureCodeInput(String inputCode) {

                    }

                    @Override
                    public void checkedSuccess() {

                        mGestureContentView.clearDrawlineState(0L);

                        Toast.makeText(GestureVerifyActivity.this, "密码正确", Toast.LENGTH_SHORT).show();

                        GestureVerifyActivity.this.removeCurrentActivity();
                    }

                    @Override
                    public void checkedFail() {

                        mGestureContentView.clearDrawlineState(1300L);
                        mTextTip.setVisibility(View.VISIBLE);
                        mTextTip.setText(Html.fromHtml("<font color='#c70c1e'>密码错误</font>"));

                        // 左右移动动画
                        Animation shakeAnimation = AnimationUtils.loadAnimation(GestureVerifyActivity.this, R.anim.shake);
                        mTextTip.startAnimation(shakeAnimation);
                    }
                });

        // 设置手势解锁显示到哪个布局里面
        mGestureContentView.setParentView(mGestureContainer);
    }

    private void setUpListeners() {
        mTextCancel.setOnClickListener(this);
        mTextForget.setOnClickListener(this);
        mTextOther.setOnClickListener(this);
    }

    private String getProtectedMobile(String phoneNumber) {

        if (TextUtils.isEmpty(phoneNumber) || phoneNumber.length() < 11) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        builder.append(phoneNumber.subSequence(0, 3));
        builder.append("****");
        builder.append(phoneNumber.subSequence(7, 11));

        return builder.toString();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.text_cancel:
                this.finish();
                break;

            default:
                break;
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        // 防止用户通过返回键退出当前页面
        return keyCode == KeyEvent.KEYCODE_BACK || super.onKeyUp(keyCode, event);
    }
}
