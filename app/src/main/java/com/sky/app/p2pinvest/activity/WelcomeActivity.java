package com.sky.app.p2pinvest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;

import com.sky.app.p2pinvest.R;
import com.sky.app.p2pinvest.common.ActivityManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created with Android Studio.
 * 描述: 欢迎页面
 * Date: 2018/3/6
 * Time: 11:52
 *
 * @author 晏琦云
 * @version ${VERSION}
 */
public class WelcomeActivity extends AppCompatActivity {

    @BindView(R.id.rl_welcome)
    RelativeLayout rlWelcome;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 去掉窗口标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏顶部的状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        // 将当前的activity添加到ActivityManager中
        ActivityManager.getInstance().addActivity(this);
        // 启动动画
        showAnimation();
    }

    /**
     * 显示动画:
     * 透明度: 0--1 持续3s
     */
    private void showAnimation() {
        // 透明度
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(3000);
        // 设置动画的变化率
        alphaAnimation.setInterpolator(new AccelerateInterpolator());
        // 方式一：
//        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            /**
//             * 当动画结束时，调用如下方法
//             * @param animation
//             */
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
//                startActivity(intent);
//                // 销毁当前页面
//                finish();
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });

        // 方式二：使用Handler
        handler.postDelayed(() -> {
            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
            startActivity(intent);
            // 销毁当前页面
//            finish();
            // 结束activity的显示，并从栈空间中移除
            ActivityManager.getInstance().removeActivity(this);
        }, 3000);
        // 启动动画
        rlWelcome.startAnimation(alphaAnimation);
    }

    @Override
    protected void onDestroy() {
        // 移除消息
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
