package com.sky.app.p2pinvest.activity;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.sky.app.p2pinvest.R;
import com.sky.app.p2pinvest.common.BaseActivity;
import com.sky.app.p2pinvest.fragment.HomeFragment;
import com.sky.app.p2pinvest.fragment.InvestFragment;
import com.sky.app.p2pinvest.fragment.MeFragment;
import com.sky.app.p2pinvest.fragment.MoreFragment;
import com.sky.app.p2pinvest.util.PermissionUtils;
import com.sky.app.p2pinvest.util.UIUtils;

import butterknife.BindView;

/**
 * Created with Android Studio.
 * 描述: 主页
 * Date: 2018/3/3
 * Time: 15:36
 *
 * @author 晏琦云
 * @version ${VERSION}
 */
public class MainActivity extends BaseActivity {

    /**
     * 重置返回
     */
    private static final int WHAT_RESET_BACK = 1;

    /**
     * 动态申请权限请求码
     */
    private static final int REQUEST_CODE = 100;

    @BindView(R.id.rb_main_home)
    RadioButton rbMainHome;
    @BindView(R.id.rb_main_invest)
    RadioButton rbMainInvest;
    @BindView(R.id.rb_main_me)
    RadioButton rbMainMe;
    @BindView(R.id.rb_main_more)
    RadioButton rbMainMore;
    @BindView(R.id.rg_main_bottom)
    RadioGroup rgMainBottom;


    private HomeFragment homeFragment;
    private InvestFragment investFragment;
    private MeFragment meFragment;
    private MoreFragment moreFragment;
    private FragmentTransaction transaction;

    /**
     * 返回的标志
     */
    private boolean flag = true;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case WHAT_RESET_BACK:
                    flag = true;
                    break;
                default:
                    break;
            }
        }
    };

    private void handlePermission() {
        String[] permissions = PermissionUtils.checkPermissions(this);
        if (permissions.length != 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(permissions, REQUEST_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            for (int result : grantResults) {
                if (result == PackageManager.PERMISSION_DENIED) {
                    UIUtils.toast("您需要同意全部权限才能正常使用本软件", false);
                    return;
                }
            }
        }
    }

    @Override
    protected void initData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            handlePermission();
        }
        rgMainBottom.setOnCheckedChangeListener((group, checkedId) -> {
            FragmentManager fragmentManager = getSupportFragmentManager();
            transaction = fragmentManager.beginTransaction();
            hideFragments();
            switch (checkedId) {
                case R.id.rb_main_home:
                    // 首页
                    if (homeFragment == null) {
                        // 创建对象以后，并不会马上调用生命周期方法，而是在commit()方法之后，方才调用
                        homeFragment = new HomeFragment();
                        transaction.add(R.id.fl_main, homeFragment);
                    }
                    // 显示当前的Fragment
                    transaction.show(homeFragment);
                    // 错误的调用位置
//                    homeFragment.requestInternet();
                    break;
                case R.id.rb_main_invest:
                    // 投资
                    if (investFragment == null) {
                        investFragment = new InvestFragment();
                        transaction.add(R.id.fl_main, investFragment);
                    }
                    // 显示当前的Fragment
                    transaction.show(investFragment);
                    break;
                case R.id.rb_main_me:
                    // 我的资产
                    if (meFragment == null) {
                        meFragment = new MeFragment();
                        transaction.add(R.id.fl_main, meFragment);
                    }
                    // 显示当前的Fragment
                    transaction.show(meFragment);
                    break;
                case R.id.rb_main_more:
                    // 更多
                    if (moreFragment == null) {
                        moreFragment = new MoreFragment();
                        transaction.add(R.id.fl_main, moreFragment);
                    }
                    // 显示当前的Fragment
                    transaction.show(moreFragment);
                    break;
                default:
                    break;
            }
            // 提交事务
            transaction.commit();
        });
        // 默认显示首页
        rgMainBottom.check(R.id.rb_main_home);
        // 模拟异常
//        try {
//            String str = null;
//            if (str.equals("abc")) {
//                Log.e("TAG", "abc");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    /**
     * 隐藏所有的fragment显示
     */
    private void hideFragments() {
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (investFragment != null) {
            transaction.hide(investFragment);
        }
        if (meFragment != null) {
            transaction.hide(meFragment);
        }
        if (moreFragment != null) {
            transaction.hide(moreFragment);
        }
    }

    /**
     * 重写onKeyUp()，实现连续两次点击方可退出当前应用
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && flag) {
            Toast.makeText(this, "再按一次，退出当前应用", Toast.LENGTH_SHORT).show();
            flag = false;
            handler.sendEmptyMessageDelayed(WHAT_RESET_BACK, 2000);
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    /**
     * 为了避免出现内存泄漏，需要在onDestroy()中，移除所有未被执行的消息
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 方式一：移除指定id的所有的消息
//        handler.removeMessages(WHAT_RESET_BACK);
        // 方式二：移除所有未被执行的消息
        handler.removeCallbacksAndMessages(null);
    }
}
