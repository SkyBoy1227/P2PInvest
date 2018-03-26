package com.sky.app.p2pinvest.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sky.app.p2pinvest.R;
import com.sky.app.p2pinvest.common.AppNetConfig;
import com.sky.app.p2pinvest.common.BaseActivity;
import com.sky.app.p2pinvest.util.MD5Utils;
import com.sky.app.p2pinvest.util.UIUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created with Android Studio.
 * 描述: 注册界面
 * Date: 2018/3/26
 * Time: 11:49
 *
 * @author 晏琦云
 * @version ${VERSION}
 */
public class UserRegisterActivity extends BaseActivity {
    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_title_settings)
    ImageView ivTitleSettings;
    @BindView(R.id.et_register_number)
    EditText etRegisterNumber;
    @BindView(R.id.et_register_name)
    EditText etRegisterName;
    @BindView(R.id.et_register_pwd)
    EditText etRegisterPwd;
    @BindView(R.id.et_register_pwdAgain)
    EditText etRegisterPwdAgain;
    @BindView(R.id.btn_register)
    Button btnRegister;

    @Override
    protected void initData() {
        // 设置“注册”button的点击事件
        btnRegister.setOnClickListener(v -> {
            // 1.获取用户注册的信息
            String number = etRegisterNumber.getText().toString().trim();
            String name = etRegisterName.getText().toString().trim();
            String pwd = etRegisterPwd.getText().toString().trim();
            String pwdAgain = etRegisterPwdAgain.getText().toString().trim();
            // 2.所填写的信息不能为空
            if (TextUtils.isEmpty(number) || TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd) || TextUtils.isEmpty(pwdAgain)) {
                UIUtils.toast("填写的信息不能为空！", false);
            } else if (!pwd.equals(pwdAgain)) {
                // 3.两次密码必须一致
                UIUtils.toast("两次密码输入不一致！", false);
                etRegisterPwd.setText("");
                etRegisterPwdAgain.setText("");
            } else {
                // 4.联网发送用户注册信息
                String url = AppNetConfig.USERREGISTER;
                RequestParams params = new RequestParams();
                params.put("name", name);
                params.put("password", MD5Utils.MD5(pwd));
                params.put("phone", number);
                client.post(url, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(String content) {
                        JSONObject jsonObject = JSON.parseObject(content);
                        boolean isExist = jsonObject.getBoolean("isExist");
                        if (isExist) {
                            UIUtils.toast("该用户已经注册过", false);
                        } else {
                            UIUtils.toast("注册成功", false);
                        }
                    }

                    @Override
                    public void onFailure(Throwable error, String content) {
                        UIUtils.toast("联网请求失败！", false);
                    }
                });
            }
        });
    }

    @OnClick(R.id.iv_title_back)
    public void back() {
        this.removeCurrentActivity();
    }

    @Override
    protected void initTitle() {
        ivTitleBack.setVisibility(View.VISIBLE);
        tvTitle.setText("用户注册");
        ivTitleSettings.setVisibility(View.INVISIBLE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_register;
    }
}
