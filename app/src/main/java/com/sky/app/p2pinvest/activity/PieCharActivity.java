package com.sky.app.p2pinvest.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.sky.app.p2pinvest.R;
import com.sky.app.p2pinvest.common.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created with Android Studio.
 * 描述: 饼状图
 * Date: 2018/3/22
 * Time: 17:16
 *
 * @author 晏琦云
 * @version ${VERSION}
 */
public class PieCharActivity extends BaseActivity {
    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_title_settings)
    ImageView ivTitleSettings;
    @BindView(R.id.pie_chart)
    PieChart pieChart;

    /**
     * 声明字体库
     */
    private Typeface mTf;

    @Override
    protected void initData() {
        mTf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        Description description = new Description();
        description.setText("android厂商2017年手机占有率");
        pieChart.setDescription(description);
        // 最内层的圆的半径
        pieChart.setHoleRadius(52f);
        // 包裹内层圆的半径
        pieChart.setTransparentCircleRadius(57f);
        pieChart.setCenterText("Android\n厂商占比");
        pieChart.setCenterTextTypeface(mTf);
        pieChart.setCenterTextSize(9f);
        // 是否使用百分比。true:各部分的百分比的和是100%。
        pieChart.setUsePercentValues(true);
        pieChart.setExtraOffsets(5, 10, 50, 10);

        PieData pieData = generateDataPie();
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTypeface(mTf);
        pieData.setValueTextSize(11f);
        pieData.setValueTextColor(Color.RED);
        // set data
        pieChart.setData(pieData);

        // 获取右上角的描述结构的对象
        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        // 相邻的entry在y轴上的间距
        l.setYEntrySpace(10f);
        // 第一个entry距离最顶端的间距
        l.setYOffset(20f);

        // do not forget to refresh the chart
        // pieChart.invalidate();
        pieChart.animateXY(900, 900);
    }

    @Override
    protected void initTitle() {
        ivTitleBack.setVisibility(View.VISIBLE);
        tvTitle.setText("饼状图demo");
        ivTitleSettings.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.iv_title_back)
    public void back() {
        this.removeCurrentActivity();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_pie_chart;
    }

    /**
     * generates a random ChartData object with just one DataSet
     *
     * @return
     */
    private PieData generateDataPie() {

        ArrayList<PieEntry> entries = new ArrayList<>();
        String[] labels = {"三星", "华为", "OPPO", "小米"};
        for (int i = 0; i < 4; i++) {
            entries.add(new PieEntry((float) ((Math.random() * 70) + 30), labels[i]));
        }

        PieDataSet d = new PieDataSet(entries, "");

        // 相邻模块间的间距
        d.setSliceSpace(2f);
        d.setColors(ColorTemplate.VORDIPLOM_COLORS);

        PieData cd = new PieData(d);
        return cd;
    }
}
