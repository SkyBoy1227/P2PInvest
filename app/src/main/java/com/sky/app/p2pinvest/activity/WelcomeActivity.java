package com.sky.app.p2pinvest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;

import com.sky.app.p2pinvest.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created with Android Studio.
 * 描述: 欢迎也没
 * Date: 2018/3/6
 * Time: 11:52
 *
 * @author 晏琦云
 * @version ${VERSION}
 */
public class WelcomeActivity extends AppCompatActivity {

    @BindView(R.id.rl_welcome)
    RelativeLayout rlWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 去掉窗口标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏顶部的状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        // 启动动画
        showAnimation();
    }

    /**
     * 显示动画:
     * 透明度: 0--1 持续2s
     */
    private void showAnimation() {
        //透明度
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(2000);

        //启动动画
        rlWelcome.startAnimation(alphaAnimation);
    }

}
