package com.sky.app.p2pinvest.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.sky.app.p2pinvest.R;
import com.sky.app.p2pinvest.common.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created with Android Studio.
 * 描述: 折线图
 * Date: 2018/3/22
 * Time: 17:13
 *
 * @author 晏琦云
 * @version ${VERSION}
 */
public class LineCharActivity extends BaseActivity {
    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_title_settings)
    ImageView ivTitleSettings;
    @BindView(R.id.line_chart)
    LineChart lineChart;

    /**
     * 声明字体库
     */
    private Typeface mTf;

    @Override
    protected void initData() {
        mTf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        // 设置当前的折线图的描述
        Description description = new Description();
        description.setText("霍金逝世事件的关注度");
        lineChart.setDescription(description);
        // 是否绘制网格背景
        lineChart.setDrawGridBackground(false);

        // 获取当前的x轴对象
        XAxis xAxis = lineChart.getXAxis();
        // 设置x轴的显示位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        // 设置x轴的字体
        xAxis.setTypeface(mTf);
        // 是否绘制x轴的网格线
        xAxis.setDrawGridLines(false);
        // 是否绘制x轴的轴线
        xAxis.setDrawAxisLine(true);

        // 获取左边的y轴对象
        YAxis leftAxis = lineChart.getAxisLeft();
        // 设置左边y轴的字体
        leftAxis.setTypeface(mTf);
        // 参数1：左边y轴提供的区间的个数。参数2：是否均匀分布这几个区间。false：均匀。true：不均匀
        leftAxis.setLabelCount(5, true);
        // this replaces setStartAtZero(true)
        leftAxis.setAxisMinimum(0f);

        // 获取右边的y轴对象
        YAxis rightAxis = lineChart.getAxisRight();
        // 将右边的y轴设置为不显示的
        rightAxis.setEnabled(false);

        // 提供折线数据。（通常情况下，折线的数据来自于服务器的数据）
        LineData lineData = generateDataLine();
        lineChart.setData(lineData);

        // 设置x轴方向的动画。执行时间是750.
        // 不需要再执行：invalidate();
        lineChart.animateX(750);
    }

    @Override
    protected void initTitle() {
        ivTitleBack.setVisibility(View.VISIBLE);
        tvTitle.setText("折线图demo");
        ivTitleSettings.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.iv_title_back)
    public void back() {
        this.removeCurrentActivity();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_line_chart;
    }

    /**
     * generates a random ChartData object with just one DataSet
     *
     * @return
     */
    private LineData generateDataLine() {
        // 折线1：
        ArrayList<Entry> e1 = new ArrayList<>();
        // 提供折线中点的数据
        for (int i = 0; i < 12; i++) {
            e1.add(new Entry(i, (int) (Math.random() * 65) + 40));
        }

        LineDataSet d1 = new LineDataSet(e1, "New DataSet (1)");
        // 设置折线的宽度
        d1.setLineWidth(4.5f);
        // 设置小圆圈的尺寸
        d1.setCircleRadius(4.5f);
        // 设置高亮的颜色
        d1.setHighLightColor(Color.rgb(192, 255, 62));
        // 是否显示小圆圈对应的数值
        d1.setDrawValues(true);

        // 折线2：
//        ArrayList<Entry> e2 = new ArrayList<>();
//
//        for (int i = 0; i < 12; i++) {
//            e2.add(new Entry(i, e1.get(i).getY() - 30));
//        }
//
//        LineDataSet d2 = new LineDataSet(e2, "New DataSet " + cnt + ", (2)");
//        d2.setLineWidth(2.5f);
//        d2.setCircleRadius(4.5f);
//        d2.setHighLightColor(Color.rgb(244, 117, 117));
//        d2.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
//        d2.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[0]);
//        d2.setDrawValues(false);

        ArrayList<ILineDataSet> sets = new ArrayList<>();
        sets.add(d1);

        LineData cd = new LineData(sets);
        return cd;
    }
}
