package com.sky.app.p2pinvest.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sky.app.p2pinvest.R;
import com.sky.app.p2pinvest.common.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created with Android Studio.
 * 描述: 关于页面
 * Date: 2018/3/28
 * Time: 16:50
 *
 * @author 晏琦云
 * @version ${VERSION}
 */
public class AboutInvestActivity extends BaseActivity {
    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_title_settings)
    ImageView ivTitleSettings;

    @Override
    protected void initData() {

    }

    @Override
    protected void initTitle() {
        ivTitleBack.setVisibility(View.VISIBLE);
        tvTitle.setText("关于P2P金融");
        ivTitleSettings.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.iv_title_back)
    public void back() {
        this.removeCurrentActivity();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_invest;
    }
}
