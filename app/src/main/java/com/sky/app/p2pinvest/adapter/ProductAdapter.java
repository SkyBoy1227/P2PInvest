package com.sky.app.p2pinvest.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sky.app.p2pinvest.R;
import com.sky.app.p2pinvest.bean.Product;
import com.sky.app.p2pinvest.ui.RoundProgress;
import com.sky.app.p2pinvest.util.UIUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created with Android Studio.
 * 描述: 全部理财Adapter
 * Date: 2018/3/13
 * Time: 15:14
 *
 * @author 晏琦云
 * @version ${VERSION}
 */
public class ProductAdapter extends BaseAdapter {
    private List<Product> productList;

    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return productList == null ? 0 : productList.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 将具体的集合数据装配到具体的item layout中。
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.e("TAG", "parent = " + parent.getClass().toString());
        Log.e("TAG", "parent.getContext() = " + parent.getContext());

        int itemType = getItemViewType(position);
        if (itemType == 0) {
            TextView textView = new TextView(parent.getContext());
            textView.setText("与子同舟，奈何覆舟");
            textView.setTextColor(UIUtils.getColor(R.color.title_text));
            textView.setTextSize(UIUtils.dp2px(20));
            return textView;
        }

        if (position > 3) {
            position--;
        }

        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.item_product_list, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // 装配数据
        Product product = productList.get(position);
        holder.pMemberNum.setText(product.memberNum);
        holder.pMinTouMoney.setText(product.minTouMoney);
        holder.pMoney.setText(product.money);
        holder.pName.setText(product.name);
        holder.pSuodingdays.setText(product.suodingDays);
        holder.pYearRate.setText(product.yearRate);
        holder.pProgress.setProgress(Integer.parseInt(product.progress));
        return convertView;
    }

    /**
     * 不同的position位置上，显示的具体的item的type值
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (position == 3) {
            return 0;
        } else {
            return 1;
        }
    }

    /**
     * 返回不同类型的item的个数
     *
     * @return
     */
    @Override
    public int getViewTypeCount() {
        return 2;
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
