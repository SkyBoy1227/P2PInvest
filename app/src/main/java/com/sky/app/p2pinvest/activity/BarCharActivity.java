package com.sky.app.p2pinvest.activity;

import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.sky.app.p2pinvest.R;
import com.sky.app.p2pinvest.common.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created with Android Studio.
 * 描述: 柱状图
 * Date: 2018/3/22
 * Time: 17:15
 *
 * @author 晏琦云
 * @version ${VERSION}
 */
public class BarCharActivity extends BaseActivity {
    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_title_settings)
    ImageView ivTitleSettings;
    @BindView(R.id.line_chart)
    BarChart lineChart;

    /**
     * 声明字体库
     */
    private Typeface mTf;

    @Override
    protected void initData() {
        mTf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        Description description = new Description();
        description.setText("华为正与各方谈判，计划推出区块链智能手机");
        lineChart.setDrawGridBackground(false);
        // 是否绘制柱状图的背景
        lineChart.setDrawBarShadow(false);

        // 获取x轴对象
        XAxis xAxis = lineChart.getXAxis();
        // 设置x轴的显示位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        // 设置x轴的字体
        xAxis.setTypeface(mTf);
        // 是否绘制x轴的网格线
        xAxis.setDrawGridLines(false);
        // 是否绘制x轴的轴线
        xAxis.setDrawAxisLine(true);

        // 获取左边y轴对象
        YAxis leftAxis = lineChart.getAxisLeft();
        // 设置y轴的字体
        leftAxis.setTypeface(mTf);
        // 参数1：左边y轴提供的区间的个数。 参数2：是否均匀分布这几个区间。 false：均匀。 true：不均匀
        leftAxis.setLabelCount(5, false);
        // 设置最大值距离顶部的距离
        leftAxis.setSpaceTop(20f);
        // this replaces setStartAtZero(true)
        leftAxis.setAxisMinimum(0f);

        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setEnabled(false);

        // 提供柱状图的数据
        BarData barData = generateDataBar();
        barData.setValueTypeface(mTf);

        // set data
        lineChart.setData(barData);
        lineChart.setFitBars(true);

        // 设置y轴方向的动画
        lineChart.animateY(700);
    }

    @Override
    protected void initTitle() {
        ivTitleBack.setVisibility(View.VISIBLE);
        tvTitle.setText("柱状图demo");
        ivTitleSettings.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.iv_title_back)
    public void back() {
        this.removeCurrentActivity();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_bar_chart;
    }

    /**
     * generates a random ChartData object with just one DataSet
     *
     * @return
     */
    private BarData generateDataBar() {

        ArrayList<BarEntry> entries = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            entries.add(new BarEntry(i, (int) (Math.random() * 70) + 30));
        }

        BarDataSet d = new BarDataSet(entries, "New DataSet 1");
        // 设置相邻的柱状图之间的距离
        d.setColors(ColorTemplate.VORDIPLOM_COLORS);
        // 设置高亮的透明度
        d.setHighLightAlpha(255);

        BarData cd = new BarData(d);
        cd.setBarWidth(0.9f);
        return cd;
    }
}
