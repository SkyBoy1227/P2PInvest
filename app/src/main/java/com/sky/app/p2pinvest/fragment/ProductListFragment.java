package com.sky.app.p2pinvest.fragment;

import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.sky.app.p2pinvest.R;
import com.sky.app.p2pinvest.common.BaseFragment;

import butterknife.BindView;

/**
 * Created with Android Studio.
 * 描述: 全部理财产品页面
 * Date: 2018/3/12
 * Time: 17:03
 *
 * @author 晏琦云
 * @version ${VERSION}
 */
public class ProductListFragment extends BaseFragment {
    @BindView(R.id.tv_product_title)
    TextView tvProductTitle;
    @BindView(R.id.lv_product_list)
    ListView lvProductList;

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
        return R.layout.fragment_product_list;
    }
}
