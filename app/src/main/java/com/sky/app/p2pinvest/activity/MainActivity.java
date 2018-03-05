package com.sky.app.p2pinvest.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sky.app.p2pinvest.R;
import com.sky.app.p2pinvest.fragment.HomeFragment;
import com.sky.app.p2pinvest.fragment.InvestFragment;
import com.sky.app.p2pinvest.fragment.MeFragment;
import com.sky.app.p2pinvest.fragment.MoreFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created with Android Studio.
 * 描述: 主页
 * Date: 2018/3/3
 * Time: 15:36
 *
 * @author 晏琦云
 * @version ${VERSION}
 */
public class MainActivity extends AppCompatActivity {
    @BindView(R.id.fl_main)
    FrameLayout flMain;
    @BindView(R.id.iv_main_home)
    ImageView ivMainHome;
    @BindView(R.id.tv_main_home)
    TextView tvMainHome;
    @BindView(R.id.ll_main_home)
    LinearLayout llMainHome;
    @BindView(R.id.iv_main_invest)
    ImageView ivMainInvest;
    @BindView(R.id.tv_main_invest)
    TextView tvMainInvest;
    @BindView(R.id.ll_main_invest)
    LinearLayout llMainInvest;
    @BindView(R.id.iv_main_me)
    ImageView ivMainMe;
    @BindView(R.id.tv_main_me)
    TextView tvMainMe;
    @BindView(R.id.ll_main_me)
    LinearLayout llMainMe;
    @BindView(R.id.iv_main_more)
    ImageView ivMainMore;
    @BindView(R.id.tv_main_more)
    TextView tvMainMore;
    @BindView(R.id.ll_main_more)
    LinearLayout llMainMore;

    private HomeFragment homeFragment;
    private InvestFragment investFragment;
    private MeFragment meFragment;
    private MoreFragment moreFragment;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        // 默认显示首页
        setSelect(0);
    }

    @OnClick({R.id.ll_main_home, R.id.ll_main_invest, R.id.ll_main_me, R.id.ll_main_more})
    public void showTab(View view) {
        switch (view.getId()) {
            case R.id.ll_main_home:
                // 首页
                setSelect(0);
                break;
            case R.id.ll_main_invest:
                // 投资
                setSelect(1);
                break;
            case R.id.ll_main_me:
                // 我的资产
                setSelect(2);
                break;
            case R.id.ll_main_more:
                // 更多
                setSelect(3);
                break;
            default:
                break;
        }
    }

    /**
     * 提供相应的Fragment的显示
     *
     * @param position
     */
    private void setSelect(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        hideFragments();
        resetTag();
        switch (position) {
            case 0:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.fl_main, homeFragment);
                }
                // 显示当前的Fragment
                transaction.show(homeFragment);
                // 改变选中项的图片和文本颜色的变化
                ivMainHome.setImageResource(R.drawable.bottom02);
                tvMainHome.setTextColor(getResources().getColor(R.color.home_back_selected));
                break;
            case 1:
                if (investFragment == null) {
                    investFragment = new InvestFragment();
                    transaction.add(R.id.fl_main, investFragment);
                }
                // 显示当前的Fragment
                transaction.show(investFragment);
                // 改变选中项的图片和文本颜色的变化
                ivMainInvest.setImageResource(R.drawable.bottom04);
                tvMainInvest.setTextColor(getResources().getColor(R.color.home_back_selected));
                break;
            case 2:
                if (meFragment == null) {
                    meFragment = new MeFragment();
                    transaction.add(R.id.fl_main, meFragment);
                }
                // 显示当前的Fragment
                transaction.show(meFragment);
                // 改变选中项的图片和文本颜色的变化
                ivMainMe.setImageResource(R.drawable.bottom06);
                tvMainMe.setTextColor(getResources().getColor(R.color.home_back_selected01));
                break;
            case 3:
                if (moreFragment == null) {
                    moreFragment = new MoreFragment();
                    transaction.add(R.id.fl_main, moreFragment);
                }
                // 显示当前的Fragment
                transaction.show(moreFragment);
                // 改变选中项的图片和文本颜色的变化
                ivMainMore.setImageResource(R.drawable.bottom08);
                tvMainMore.setTextColor(getResources().getColor(R.color.home_back_selected));
                break;
            default:
                break;
        }
        // 提交事务
        transaction.commit();
    }

    /**
     * 重置ImageView和TextView的显示
     */
    private void resetTag() {
        ivMainHome.setImageResource(R.drawable.bottom01);
        ivMainInvest.setImageResource(R.drawable.bottom03);
        ivMainMe.setImageResource(R.drawable.bottom05);
        ivMainMore.setImageResource(R.drawable.bottom07);

        tvMainHome.setTextColor(getResources().getColor(R.color.home_back_unselected));
        tvMainInvest.setTextColor(getResources().getColor(R.color.home_back_unselected));
        tvMainMe.setTextColor(getResources().getColor(R.color.home_back_unselected));
        tvMainMore.setTextColor(getResources().getColor(R.color.home_back_unselected));
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
}
