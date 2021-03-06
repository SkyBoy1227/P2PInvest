package com.sky.app.p2pinvest.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sky.app.p2pinvest.R;
import com.sky.app.p2pinvest.common.BaseActivity;
import com.sky.gesturelock.widget.GestureContentView;
import com.sky.gesturelock.widget.GestureDrawline;
import com.sky.gesturelock.widget.LockIndicator;

import butterknife.BindView;

/**
 * Created with Android Studio.
 * 描述: 设置手势密码页面
 * Date: 2018/3/27
 * Time: 15:57
 *
 * @author 晏琦云
 * @version ${VERSION}
 */
public class GestureEditActivity extends BaseActivity implements View.OnClickListener {
    /**
     * 手机号码
     */
    public static final String PARAM_PHONE_NUMBER = "PARAM_PHONE_NUMBER";
    /**
     * 意图
     */
    public static final String PARAM_INTENT_CODE = "PARAM_INTENT_CODE";
    /**
     * 首次提示绘制手势密码，可以选择跳过
     */
    public static final String PARAM_IS_FIRST_ADVICE = "PARAM_IS_FIRST_ADVICE";

    @BindView(R.id.text_title)
    TextView mTextTitle;
    @BindView(R.id.text_cancel)
    TextView mTextCancel;
    @BindView(R.id.lock_indicator)
    LockIndicator mLockIndicator;
    @BindView(R.id.text_tip)
    TextView mTextTip;
    @BindView(R.id.gesture_container)
    FrameLayout mGestureContainer;
    @BindView(R.id.text_reset)
    TextView mTextReset;

    private GestureContentView mGestureContentView;
    private String mParamSetUpcode = null;
    private String mParamPhoneNumber;
    private boolean mIsFirstInput = true;
    private String mFirstPassword = null;
    private String mConfirmPassword = null;
    private int mParamIntentCode;

    private SharedPreferences mSharedPreferences = null;

    @Override
    protected void initData() {
        setUpViews();
        setUpListeners();
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gesture_edit;
    }

    private void setUpViews() {
        mTextTitle = findViewById(R.id.text_title);
        mTextCancel = findViewById(R.id.text_cancel);
        mTextReset = findViewById(R.id.text_reset);
        mTextReset.setClickable(false);
        mLockIndicator = findViewById(R.id.lock_indicator);
        mTextTip = findViewById(R.id.text_tip);
        mGestureContainer = findViewById(R.id.gesture_container);
        mSharedPreferences = this.getSharedPreferences("secret_protect", Context.MODE_PRIVATE);
        // 初始化一个显示各个点的viewGroup
        mGestureContentView = new GestureContentView(this, false, "", new GestureDrawline.GestureCallBack() {
            @Override
            public void onGestureCodeInput(String inputCode) {

                if (!isInputPassValidate(inputCode)) {
                    mTextTip.setText(Html.fromHtml("<font color='#c70c1e'>最少链接4个点, 请重新输入</font>"));
                    mGestureContentView.clearDrawlineState(0L);

                    return;
                }

                if (mIsFirstInput) {
                    mFirstPassword = inputCode;

                    updateCodeList(inputCode);

                    mGestureContentView.clearDrawlineState(0L);
                    mTextReset.setClickable(true);
                    mTextReset.setText(getString(R.string.reset_gesture_code));
                } else {

                    if (inputCode.equals(mFirstPassword)) {

                        Toast.makeText(GestureEditActivity.this, "设置成功", Toast.LENGTH_SHORT).show();
                        mGestureContentView.clearDrawlineState(0L);
                        GestureEditActivity.this.removeCurrentActivity();
                    } else {
                        mTextTip.setText(Html.fromHtml("<font color='#c70c1e'>与上一次绘制不一致，请重新绘制</font>"));

                        // 左右移动动画
                        Animation shakeAnimation = AnimationUtils.loadAnimation(GestureEditActivity.this, R.anim.shake);
                        mTextTip.startAnimation(shakeAnimation);

                        // 保持绘制的线，1.5秒后清除
                        mGestureContentView.clearDrawlineState(1300L);
                    }
                }

                mIsFirstInput = false;
            }

            @Override
            public void checkedSuccess() {

            }

            @Override
            public void checkedFail() {

            }
        });

        // 设置手势解锁显示到哪个布局里面
        mGestureContentView.setParentView(mGestureContainer);

        updateCodeList("");
    }

    private void setUpListeners() {
        mTextCancel.setOnClickListener(this);
        mTextReset.setOnClickListener(this);
    }

    private void updateCodeList(String inputCode) {

        // 更新选择的图案
        mLockIndicator.setPath(inputCode);
        mSharedPreferences.edit().putString("inputCode", inputCode).commit();
        Log.e("TAG", "inputCode = " + inputCode);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_cancel:
                this.finish();
                break;

            case R.id.text_reset:
                mIsFirstInput = true;
                updateCodeList("");
                mTextTip.setText(getString(R.string.set_gesture_pattern));
                break;
            default:
                break;
        }
    }

    private boolean isInputPassValidate(String inputPassword) {

        if (TextUtils.isEmpty(inputPassword) || inputPassword.length() < 4) {
            return false;
        }

        return true;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        // 防止用户通过返回键退出当前页面
        return keyCode == KeyEvent.KEYCODE_BACK || super.onKeyUp(keyCode, event);
    }
}
