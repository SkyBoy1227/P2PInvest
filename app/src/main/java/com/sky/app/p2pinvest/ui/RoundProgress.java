package com.sky.app.p2pinvest.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.sky.app.p2pinvest.R;
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
    //设置绘制的圆环及文本的属性

//    /**
//     * 圆环的颜色
//     */
//    private int roundColor = Color.GRAY;
//
//    /**
//     * 圆弧的颜色
//     */
//    private int roundProgressColor = Color.RED;
//
//    /**
//     * 文本的颜色
//     */
//    private int textColor = Color.BLUE;
//
//    /**
//     * 圆环的宽度
//     */
//    private float roundWidth = UIUtils.dp2px(10);
//
//    /**
//     * 文本的字体大小
//     */
//    private float textSize = UIUtils.dp2px(20);
//
//    /**
//     * 圆环的最大值
//     */
//    private int max = 100;
//
//    /**
//     * 圆环的进度
//     */
//    private int progress = 60;

    // 使用自定义属性来初始化如下的变量

    /**
     * 圆环的颜色
     */
    private int roundColor;

    /**
     * 圆弧的颜色
     */
    private int roundProgressColor;

    /**
     * 文本的颜色
     */
    private int textColor;

    /**
     * 圆环的宽度
     */
    private float roundWidth;

    /**
     * 文本的字体大小
     */
    private float textSize;

    /**
     * 圆环的最大值
     */
    private int max;

    /**
     * 圆环的进度
     */
    private int progress;

    /**
     * 当前视图的宽度 = 高度
     */
    private int width;

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

        // 获取自定义的属性
        // 1.获取TypeArray的对象(调用两个参数的方法）
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundProgress);

        // 2.取出所有的自定义属性
        roundColor = typedArray.getColor(R.styleable.RoundProgress_roundColor, Color.GRAY);
        roundProgressColor = typedArray.getColor(R.styleable.RoundProgress_roundProgressColor, Color.RED);
        textColor = typedArray.getColor(R.styleable.RoundProgress_textColor, Color.GREEN);
        roundWidth = typedArray.getDimension(R.styleable.RoundProgress_roundWidth, UIUtils.dp2px(10));
        textSize = typedArray.getDimension(R.styleable.RoundProgress_textSize, UIUtils.dp2px(20));
        max = typedArray.getInteger(R.styleable.RoundProgress_max, 100);
        progress = typedArray.getInteger(R.styleable.RoundProgress_progress, 50);

        // 3.回收处理
        typedArray.recycle();
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
        RectF rectF = new RectF(roundWidth / 2, roundWidth / 2, width - roundWidth / 2, width - roundWidth / 2);
        paint.setColor(roundProgressColor);
        canvas.drawArc(rectF, 0, progress * 360 / max, false, paint);

        // 3.绘制文本
        String text = progress * 100 / max + "%";
        // 设置画笔
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        paint.setStrokeWidth(0);
        // 创建了一个矩形，此时矩形没有具体的宽度和高度
        Rect rect = new Rect();
        // 此时的矩形的宽度和高度即为正好包裹文本的矩形的宽高
        paint.getTextBounds(text, 0, text.length(), rect);
        // 获取左下角顶点的坐标
        float x = width / 2 - rect.width() / 2;
        float y = width / 2 + rect.height() / 2;
        canvas.drawText(text, x, y, paint);
    }
}
