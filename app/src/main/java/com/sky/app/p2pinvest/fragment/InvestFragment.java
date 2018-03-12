package com.sky.app.p2pinvest.fragment;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.sky.app.p2pinvest.R;
import com.sky.app.p2pinvest.common.BaseFragment;
import com.viewpagerindicator.TabPageIndicator;

import butterknife.BindView;

/**
 * Created with Android Studio.
 * 描述: 投资页
 * Date: 2018/3/5
 * Time: 21:05
 *
 * @author 晏琦云
 * @version ${VERSION}
 */
public class InvestFragment extends BaseFragment {
    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_title_settings)
    ImageView ivTitleSettings;
    @BindView(R.id.tabpage_invest)
    TabPageIndicator tabpageInvest;
    @BindView(R.id.vp_invest)
    ViewPager vpInvest;

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
        // 1.加载三个不同的Fragment：ProductListFragment,ProductRecommondFragment,ProductHotFragment

        // 2.ViewPager设置三个Fragment的显示
    }

    /**
     * 初始化Title
     */
    @Override
    protected void initTitle() {
        ivTitleBack.setVisibility(View.GONE);
        tvTitle.setText("投资");
        ivTitleSettings.setVisibility(View.GONE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_invest;
    }
}
