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
import com.sky.app.p2pinvest.bean.User;
import com.sky.app.p2pinvest.common.AppNetConfig;
import com.sky.app.p2pinvest.common.BaseActivity;
import com.sky.app.p2pinvest.util.MD5Utils;
import com.sky.app.p2pinvest.util.UIUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created with Android Studio.
 * 描述: 登录界面
 * Date: 2018/3/17
 * Time: 14:00
 *
 * @author 晏琦云
 * @version ${VERSION}
 */
public class LoginActivity extends BaseActivity {
    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_title_settings)
    ImageView ivTitleSettings;
    @BindView(R.id.et_login_number)
    EditText etLoginNumber;
    @BindView(R.id.et_login_password)
    EditText etLoginPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;

    @Override
    protected void initData() {

    }

    @Override
    protected void initTitle() {
        ivTitleBack.setVisibility(View.VISIBLE);
        tvTitle.setText("用户登录");
        ivTitleSettings.setVisibility(View.INVISIBLE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    /**
     * 登录按钮的点击事件
     */
    @OnClick(R.id.btn_login)
    public void login() {
        String number = etLoginNumber.getText().toString().trim();
        String password = etLoginPassword.getText().toString().trim();
        if (!TextUtils.isEmpty(number) && !TextUtils.isEmpty(password)) {
            String url = AppNetConfig.LOGIN;
            RequestParams params = new RequestParams();
            params.put("phone", number);
            params.put("password", MD5Utils.MD5(password));
            client.post(url, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(String content) {
                    // 解析json
                    JSONObject jsonObject = JSON.parseObject(content);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        // 解析json数据，生成User对象
                        String data = jsonObject.getString("data");
                        User user = JSON.parseObject(data, User.class);
                        // 保存用户信息
                        saveUser(user);
                        // 重新加载界面
                        removeAll();
                        goToActivity(MainActivity.class, null);
                    } else {
                        UIUtils.toast("用户名不存在或密码不正确！", false);
                    }
                }

                @Override
                public void onFailure(Throwable error, String content) {
                    UIUtils.toast("联网失败！", false);
                }
            });
        } else {
            UIUtils.toast("用户名或密码不能为空！", false);
        }
    }

}
