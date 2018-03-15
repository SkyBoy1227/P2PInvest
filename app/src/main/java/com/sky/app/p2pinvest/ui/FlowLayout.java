package com.sky.app.p2pinvest.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created with Android Studio.
 * 描述: 自定义流式布局
 * Date: 2018/3/15
 * Time: 9:53
 *
 * @author 晏琦云
 * @version ${VERSION}
 */
public class FlowLayout extends ViewGroup {
    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 能够设置当前布局的宽度和高度
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获取设置的宽高的模式和具体的值
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        // 如果用户使用的是至多模式，那么使用如下两个变量计算真实的宽高值。
        int width = 0;
        int height = 0;

        // 每一行的宽度、高度
        int lineWidth = 0;
        int lineHeight = 0;

        // 获取子视图
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);

            // 只有调用了如下的方法，方可计算子视图的测量的宽高
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);

            // 获取子视图的宽高
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();

            // 要想保证可以获取子视图的边距参数对象，必须重写generateLayoutParams().
            MarginLayoutParams mp = (MarginLayoutParams) childView.getLayoutParams();

            if (lineWidth + childWidth + mp.leftMargin + mp.rightMargin <= widthSize) {
                // 不换行
                lineWidth += childWidth + mp.leftMargin + mp.rightMargin;
                lineHeight = Math.max(lineHeight, childHeight + mp.topMargin + mp.bottomMargin);
            } else {
                // 换行
                width = Math.max(width, lineWidth);
                height += lineHeight;

                // 重置
                lineWidth = childWidth + mp.leftMargin + mp.rightMargin;
                lineHeight = childHeight + mp.topMargin + mp.bottomMargin;
            }

            // 最后一个元素
            if (i == childCount - 1) {
                width = Math.max(width, lineWidth);
                height += lineHeight;
            }

        }

        Log.e("TAG", "widthSize = " + widthSize + " , heightSize = " + heightSize);
        Log.e("TAG", "width = " + width + " , height = " + height);

        // 设置当前流式布局的宽高
        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : width, heightMode == MeasureSpec.EXACTLY ? heightSize : height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
