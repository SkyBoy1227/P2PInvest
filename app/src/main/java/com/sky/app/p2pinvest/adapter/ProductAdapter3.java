package com.sky.app.p2pinvest.adapter;

import android.content.Context;

import com.sky.app.p2pinvest.bean.Product;

import java.util.List;

/**
 * Created with Android Studio.
 * 描述: ${DESCRIPTION}
 * Date: 2018/3/13
 * Time: 17:54
 *
 * @author 晏琦云
 * @version ${VERSION}
 */
public class ProductAdapter3 extends MyBaseAdapter3<Product> {
    private Context context;

    /**
     * 通过构造器初始化集合数据
     *
     * @param list
     */
    public ProductAdapter3(List<Product> list, Context context) {
        super(list);
        this.context = context;
    }

    @Override
    protected BaseHolder<Product> getHolder() {
        return new MyHolder(context);
    }
}
