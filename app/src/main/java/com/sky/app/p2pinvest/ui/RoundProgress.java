package com.sky.app.p2pinvest.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.sky.app.p2pinvest.util.UIUtils;

/**
 * Created with Android Studio.
 * 描述: 自定义圆环进度条
 * Date: 2018/3/8
 * Time: 16:29
 *
 * @author 晏琦云
 * @version ${VERSION}
 */
public class RoundProgress extends View {
    /**
     * 当前视图的宽度 = 高度
     */
    private int width;

    //设置绘制的圆环及文本的属性

    /**
     * 圆环的颜色
     */
    private int roundColor = Color.GRAY;

    /**
     * 圆弧的颜色
     */
    private int roundProgressColor = Color.RED;

    /**
     * 文本的颜色
     */
    private int textColor = Color.BLUE;

    /**
     * 圆环的宽度
     */
    private float roundWidth = UIUtils.dp2px(10);

    /**
     * 文本的字体大小
     */
    private int textSize = UIUtils.dp2px(20);

    /**
     * 圆环的最大值
     */
    private int max = 100;

    /**
     * 圆环的进度
     */
    private int progress = 50;

    /**
     * 画笔
     */
    private Paint paint;

    public RoundProgress(Context context) {
        this(context, null);
    }

    public RoundProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 初始化
        paint = new Paint();
        // 抗锯齿
        paint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.width = getMeasuredWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 1.绘制圆环
        // 获取圆心坐标
        int cx = width / 2;
        int cy = width / 2;
        float radius = width / 2 - roundWidth / 2;
        // 设置画笔颜色
        paint.setColor(roundColor);
        // 设置画笔的样式
        paint.setStyle(Paint.Style.STROKE);
        // 设置画笔的宽度
        paint.setStrokeWidth(roundWidth);
        canvas.drawCircle(cx, cy, radius, paint);
        // 2.绘制圆弧

        // 3.绘制文本
    }
}
