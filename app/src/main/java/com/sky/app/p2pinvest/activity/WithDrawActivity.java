package com.sky.app.p2pinvest.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sky.app.p2pinvest.R;
import com.sky.app.p2pinvest.common.BaseActivity;
import com.sky.app.p2pinvest.util.UIUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created with Android Studio.
 * 描述: 提现页面
 * Date: 2018/3/22
 * Time: 15:26
 *
 * @author 晏琦云
 * @version ${VERSION}
 */
public class WithDrawActivity extends BaseActivity {
    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_title_settings)
    ImageView ivTitleSettings;
    @BindView(R.id.et_input_money)
    EditText etInputMoney;
    @BindView(R.id.btn_withdraw)
    Button btnWithdraw;

    @Override
    protected void initData() {
        // 设置当前的体现的button是不可操作的
        btnWithdraw.setClickable(false);
        etInputMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String money = s.toString().trim();
                if (TextUtils.isEmpty(money)) {
                    // 设置button为不可操作的
                    btnWithdraw.setClickable(false);
                    // 设置背景颜色
                    btnWithdraw.setBackgroundResource(R.drawable.btn_02);
                } else {
                    // 设置button为可操作的
                    btnWithdraw.setClickable(true);
                    // 设置背景颜色
                    btnWithdraw.setBackgroundResource(R.drawable.btn_01);
                }
            }
        });
    }

    @Override
    protected void initTitle() {
        ivTitleBack.setVisibility(View.VISIBLE);
        tvTitle.setText("提现");
        ivTitleSettings.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.btn_withdraw)
    public void withdraw() {
        // 将要提现的具体数额发送给后台，由后台连接第三方支付平台，完成金额的提现操作。（略）
        // 提示用户信息：
        UIUtils.toast("您的提现申请已被成功受理。审核通过后，24小时内，你的钱自然会到账", false);
        UIUtils.getHandler().postDelayed(this::removeCurrentActivity, 2000);
    }

    @OnClick(R.id.iv_title_back)
    public void back() {
        this.removeCurrentActivity();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_withdraw;
    }

    @Override
    protected void onDestroy() {
        UIUtils.getHandler().removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
