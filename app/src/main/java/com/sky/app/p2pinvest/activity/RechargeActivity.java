package com.sky.app.p2pinvest.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
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
 * 描述: 充值页面
 * Date: 2018/3/21
 * Time: 16:03
 *
 * @author 晏琦云
 * @version ${VERSION}
 */
public class RechargeActivity extends BaseActivity {
    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_title_settings)
    ImageView ivTitleSettings;
    @BindView(R.id.et_recharge)
    EditText etRecharge;
    @BindView(R.id.tv_balance)
    TextView tvBalance;
    @BindView(R.id.btn_recharge)
    Button btnRecharge;

    @Override
    protected void initData() {
        // 默认情况下，button是不可操作的
        btnRecharge.setClickable(false);
        etRecharge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.e("TAG", "beforeTextChanged: ");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e("TAG", "onTextChanged: ");
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.e("TAG", "afterTextChanged: ");
                if (TextUtils.isEmpty(s.toString())) {
                    // 设置button为不可操作的
                    btnRecharge.setClickable(false);
                    // 设置背景颜色
                    btnRecharge.setBackgroundResource(R.drawable.btn_02);
                } else {
                    // 设置button为可操作的
                    btnRecharge.setClickable(true);
                    // 设置背景颜色
                    btnRecharge.setBackgroundResource(R.drawable.btn_01);
                }
            }
        });
    }

    @Override
    protected void initTitle() {
        ivTitleBack.setVisibility(View.VISIBLE);
        tvTitle.setText("充值");
        ivTitleSettings.setVisibility(View.VISIBLE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recharge;
    }

    @OnClick(R.id.iv_title_back)
    public void back() {
        this.removeCurrentActivity();
    }

    @OnClick(R.id.btn_recharge)
    public void recharge() {
        UIUtils.toast("充值", false);
    }
}
