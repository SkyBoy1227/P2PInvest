package com.sky.app.p2pinvest.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.sky.app.p2pinvest.R;
import com.sky.app.p2pinvest.bean.Product;

import java.util.List;

/**
 * Created with Android Studio.
 * 描述: ${DESCRIPTION}
 * Date: 2018/3/13
 * Time: 16:53
 *
 * @author 晏琦云
 * @version ${VERSION}
 */
public class ProductAdapter2 extends MyBaseAdapter2<Product> {
    /**
     * 通过构造器初始化集合数据
     *
     * @param list
     */
    public ProductAdapter2(List<Product> list) {
        super(list);
    }

    @Override
    protected void setData(View convertView, Product product) {
        ((TextView) convertView.findViewById(R.id.p_name)).setText(product.name);
        // ...
        Log.e("TAG", "setData()");
    }

    @Override
    protected View initView(Context context) {
        return View.inflate(context, R.layout.item_product_list, null);
    }
}
