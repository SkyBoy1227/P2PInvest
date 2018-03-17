package com.sky.app.p2pinvest.common;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.sky.app.p2pinvest.bean.User;

import butterknife.ButterKnife;

/**
 * Created with Android Studio.
 * 描述: 通用的Activity
 * Date: 2018/3/17
 * Time: 13:37
 *
 * @author 晏琦云
 * @version ${VERSION}
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        // 将当前的activity添加到ActivityManager中
        ActivityManager.getInstance().addActivity(this);
        initTitle();
        initData();
    }


    /**
     * 启动新的activity
     *
     * @param activity
     * @param bundle
     */
    public void goToActivity(Class activity, Bundle bundle) {
        Intent intent = new Intent(this, activity);
        // 携带数据
        if (bundle != null && bundle.size() != 0) {
            intent.putExtra("data", bundle);
        }
        startActivity(intent);
    }


    /**
     * 销毁当前的Activity
     */
    public void removeCurrentActivity() {
        ActivityManager.getInstance().removeCurrentActivity();
    }


    /**
     * 销毁所有的activity
     */
    public void removeAll() {
        ActivityManager.getInstance().removeAllActivity();
    }

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化标题
     */
    protected abstract void initTitle();

    /**
     * 得到Layout的布局id
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 保存用户信息
     *
     * @param user
     */
    public void saveUser(User user) {
        SharedPreferences sp = this.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("name", user.getName());
        editor.putString("imageurl", user.getImageurl());
        editor.putBoolean("iscredit", user.isIscredit());
        editor.putString("phone", user.getPhone());
        // 提交事务
        // 必须提交，否则保存不成功
        editor.apply();
    }

    /**
     * 读取用户信息
     *
     * @return
     */
    public User readUser() {
        SharedPreferences sp = this.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        User user = new User();
        user.setName(sp.getString("name", ""));
        user.setImageurl(sp.getString("imageurl", ""));
        user.setIscredit(sp.getBoolean("iscredit", false));
        user.setPhone(sp.getString("phone", ""));
        return user;
    }
}
