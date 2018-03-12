package com.sky.app.p2pinvest.fragment;

import com.loopj.android.http.RequestParams;
import com.sky.app.p2pinvest.R;
import com.sky.app.p2pinvest.common.BaseFragment;

/**
 * Created with Android Studio.
 * 描述: 推荐理财产品页面
 * Date: 2018/3/12
 * Time: 17:03
 *
 * @author 晏琦云
 * @version ${VERSION}
 */
public class ProductRecommendFragment extends BaseFragment {
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

    @Override
    protected void initTitle() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_product_recommend;
    }
}
