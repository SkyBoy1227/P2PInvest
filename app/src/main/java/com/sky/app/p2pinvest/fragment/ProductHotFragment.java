package com.sky.app.p2pinvest.fragment;

import android.graphics.Color;
import android.os.Build;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.sky.app.p2pinvest.R;
import com.sky.app.p2pinvest.common.BaseFragment;
import com.sky.app.p2pinvest.ui.FlowLayout;
import com.sky.app.p2pinvest.util.DrawableUtils;
import com.sky.app.p2pinvest.util.UIUtils;

import java.util.Random;

import butterknife.BindView;

/**
 * Created with Android Studio.
 * 描述: 热门理财产品页面
 * Date: 2018/3/12
 * Time: 17:03
 *
 * @author 晏琦云
 * @version ${VERSION}
 */
public class ProductHotFragment extends BaseFragment {
    @BindView(R.id.flow_hot)
    FlowLayout flowHot;

    private String[] datas = new String[]{"如果你给我的", "和你给别人的一样", "那我就不要了", "不乱于心，不困于情",
            "向来缘浅，奈何情深", "生如夏花之绚烂", "死如秋叶之静美", "时光静好，与君语",
            "细水流年，与君同", "繁华落尽，与君老", "宠辱不惊，看庭前花开花落", "去留无意，望天上云卷云舒"
    };

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
        for (int i = 0; i < datas.length; i++) {
            TextView tv = new TextView(getContext());

            // 设置属性
            tv.setText(datas[i]);
            ViewGroup.MarginLayoutParams mp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            mp.leftMargin = UIUtils.dp2px(10);
            mp.topMargin = UIUtils.dp2px(10);
            mp.rightMargin = UIUtils.dp2px(10);
            mp.bottomMargin = UIUtils.dp2px(10);
            // 设置外边距
            tv.setLayoutParams(mp);

            int padding = UIUtils.dp2px(5);
            // 设置内边距
            tv.setPadding(padding, padding, padding, padding);
            tv.setTextSize(15);

            Random random = new Random();
            int red = random.nextInt(211);
            int green = random.nextInt(211);
            int blue = random.nextInt(211);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                // 设置单一背景
//                tv.setBackground(DrawableUtils.getDrawable(Color.rgb(red, green, blue), UIUtils.dp2px(5)));
                // 设置具有选择器功能的背景
                tv.setBackground(DrawableUtils.getSelector(DrawableUtils.getDrawable(Color.rgb(red, green, blue), UIUtils.dp2px(5)), DrawableUtils.getDrawable(Color.WHITE, UIUtils.dp2px(5))));
            }
            // 设置textView是可点击的.如果设置了点击事件，则TextView就是可点击的。
//            tv.setClickable(true);
            tv.setOnClickListener(v -> UIUtils.toast(tv.getText().toString(), false));
            flowHot.addView(tv);
        }
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_product_hot;
    }
}
