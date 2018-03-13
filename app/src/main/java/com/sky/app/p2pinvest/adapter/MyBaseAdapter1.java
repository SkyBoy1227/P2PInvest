package com.sky.app.p2pinvest.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created with Android Studio.
 * 描述: ${DESCRIPTION}
 * Date: 2018/3/13
 * Time: 16:22
 *
 * @author 晏琦云
 * @version ${VERSION}
 */
public abstract class MyBaseAdapter1<T> extends BaseAdapter {
    protected List<T> list;

    /**
     * 通过构造器初始化集合数据
     *
     * @param list
     */
    public MyBaseAdapter1(List<T> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return myGetView(position, convertView, parent);
    }

    /**
     * 得到View对象
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    protected abstract View myGetView(int position, View convertView, ViewGroup parent);
}
