package com.sky.app.p2pinvest.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sky.app.p2pinvest.R;
import com.sky.app.p2pinvest.bean.Product;
import com.sky.app.p2pinvest.ui.RoundProgress;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created with Android Studio.
 * 描述: ${DESCRIPTION}
 * Date: 2018/3/13
 * Time: 16:24
 *
 * @author 晏琦云
 * @version ${VERSION}
 */
public class ProductAdapter1 extends MyBaseAdapter1<Product> {
    /**
     * 通过构造器初始化集合数据
     *
     * @param list
     */
    public ProductAdapter1(List<Product> list) {
        super(list);
    }

    @Override
    protected View myGetView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.item_product_list, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // 装配数据
        Product product = list.get(position);
        holder.pMemberNum.setText(product.memberNum);
        holder.pMinTouMoney.setText(product.minTouMoney);
        holder.pMoney.setText(product.money);
        holder.pName.setText(product.name);
        holder.pSuodingdays.setText(product.suodingDays);
        holder.pYearRate.setText(product.yearRate);
        holder.pProgress.setProgress(Integer.parseInt(product.progress));
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.p_name)
        TextView pName;
        @BindView(R.id.p_money)
        TextView pMoney;
        @BindView(R.id.p_yearRate)
        TextView pYearRate;
        @BindView(R.id.p_suodingdays)
        TextView pSuodingdays;
        @BindView(R.id.p_minTouMoney)
        TextView pMinTouMoney;
        @BindView(R.id.p_memberNum)
        TextView pMemberNum;
        @BindView(R.id.p_progress)
        RoundProgress pProgress;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
