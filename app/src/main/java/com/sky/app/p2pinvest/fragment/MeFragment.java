package com.sky.app.p2pinvest.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.sky.app.p2pinvest.R;
import com.sky.app.p2pinvest.common.BaseFragment;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created with Android Studio.
 * 描述: 我的资产页
 * Date: 2018/3/5
 * Time: 21:05
 *
 * @author 晏琦云
 * @version ${VERSION}
 */
public class MeFragment extends BaseFragment {
    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_title_settings)
    ImageView ivTitleSettings;
    @BindView(R.id.iv_me_icon)
    ImageView ivMeIcon;
    @BindView(R.id.rl_me_icon)
    RelativeLayout rlMeIcon;
    @BindView(R.id.tv_me_name)
    TextView tvMeName;
    @BindView(R.id.recharge)
    ImageView recharge;
    @BindView(R.id.withdraw)
    ImageView withdraw;
    @BindView(R.id.tv_touzi)
    TextView tvTouzi;
    @BindView(R.id.tv_touzi_zhiguan)
    TextView tvTouziZhiguan;
    @BindView(R.id.tv_zichan)
    TextView tvZichan;
    Unbinder unbinder;

    @Override
    protected RequestParams getParams() {
        return null;
    }

    @Override
    protected String getUrl() {
        return null;
    }

    @Override
    protected void initData(String content) {

    }

    /**
     * 初始化Title
     */
    @Override
    protected void initTitle() {
        ivTitleBack.setVisibility(View.GONE);
        tvTitle.setText("我的资产");
        ivTitleSettings.setVisibility(View.GONE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }
}
