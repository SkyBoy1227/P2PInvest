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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
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
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (position) {
            case 0:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.fl_main, homeFragment);
                }
                // 显示当前的Fragment
                transaction.show(homeFragment);
                break;
            case 1:
                if (investFragment == null) {
                    investFragment = new InvestFragment();
                    transaction.add(R.id.fl_main, investFragment);
                }
                // 显示当前的Fragment
                transaction.show(investFragment);
                break;
            case 2:
                if (meFragment == null) {
                    meFragment = new MeFragment();
                    transaction.add(R.id.fl_main, meFragment);
                }
                // 显示当前的Fragment
                transaction.show(meFragment);
                break;
            case 3:
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
        transaction.commit();
    }
}
