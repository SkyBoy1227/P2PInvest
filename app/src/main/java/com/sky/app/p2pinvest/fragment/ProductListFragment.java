package com.sky.app.p2pinvest.fragment;

import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.RequestParams;
import com.sky.app.p2pinvest.R;
import com.sky.app.p2pinvest.bean.Product;
import com.sky.app.p2pinvest.common.AppNetConfig;
import com.sky.app.p2pinvest.common.BaseFragment;

import java.util.List;

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

    private List<Product> productList;

    @Override
    protected RequestParams getParams() {
        return null;
    }

    @Override
    protected String getUrl() {
        return AppNetConfig.PRODUCT;
    }

    @Override
    protected void initData(String content) {
        // 方式一：使得当前的textView获取焦点
//        tvProductTitle.setFocusable(true);
//        tvProductTitle.setFocusableInTouchMode(true);
//        tvProductTitle.requestFocus();
        // 方式二：提供TextView的子类，重写isFocused(),返回true即可。
        JSONObject jsonObject = JSON.parseObject(content);
        boolean success = jsonObject.getBoolean("success");
        if (success) {
            String data = jsonObject.getString("data");
            productList = JSON.parseArray(data, Product.class);
        }
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_product_list;
    }
}
