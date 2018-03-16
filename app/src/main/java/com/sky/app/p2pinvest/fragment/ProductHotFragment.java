package com.sky.app.p2pinvest.fragment;

import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.sky.app.p2pinvest.R;
import com.sky.app.p2pinvest.common.BaseFragment;
import com.sky.app.p2pinvest.ui.FlowLayout;
import com.sky.app.p2pinvest.util.UIUtils;

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

    private String[] datas = new String[]{"新手福利计划", "财神道90天计划", "硅谷计划", "30天理财计划", "180天理财计划", "月月升", "中情局投资商业经营", "大学老师购买车辆", "屌丝下海经商计划", "美人鱼影视拍摄投资", "Android培训老师自己周转", "养猪场扩大经营",
            "旅游公司扩大规模", "摩托罗拉洗钱计划", "铁路局回款计划", "屌丝迎娶白富美计划"
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
            Log.e("TAG", "textSize = " + UIUtils.dp2px(10));

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
