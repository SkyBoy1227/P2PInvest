package com.sky.app.p2pinvest.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.sky.app.p2pinvest.R;
import com.sky.app.p2pinvest.bean.Product;
import com.sky.app.p2pinvest.ui.RoundProgress;

import butterknife.BindView;

/**
 * Created with Android Studio.
 * 描述: ${DESCRIPTION}
 * Date: 2018/3/13
 * Time: 17:57
 *
 * @author 晏琦云
 * @version ${VERSION}
 */
public class MyHolder extends BaseHolder<Product> {
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

    public MyHolder(Context context) {
        super(context);
    }

    @Override
    protected void refreshData() {
        // 装配数据
        Product data = getData();
        pMemberNum.setText(data.memberNum);
        pMinTouMoney.setText(data.minTouMoney);
        pMoney.setText(data.money);
        pName.setText(data.name);
        pSuodingdays.setText(data.suodingDays);
        pYearRate.setText(data.yearRate);
        pProgress.setProgress(Integer.parseInt(data.progress));
    }

    @Override
    protected View initView(Context context) {
        return View.inflate(context, R.layout.item_product_list, null);
    }
}
