package com.sky.app.p2pinvest.ui;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created with Android Studio.
 * 描述: ${DESCRIPTION}
 * Date: 2018/3/9
 * Time: 11:14
 *
 * @author 晏琦云
 * @version ${VERSION}
 */
public class MyScrollView extends ScrollView {

    private View childView;

    /**
     * 上一次y轴方向操作的坐标位置
     */
    private int lastY;

    /**
     * 用于记录临界状态的左、上、右、下
     */
    private Rect normal = new Rect();

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        // 获取子视图
        if (getChildCount() > 0) {
            childView = getChildAt(0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (childView != null) {
            int eventY = (int) ev.getY();
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    // 记录
                    lastY = eventY;
                    break;
                case MotionEvent.ACTION_MOVE:
                    // 微小的偏移量
                    int dy = eventY - lastY;
                    if (isNeedMove()) {
                        if (normal.isEmpty()) {
                            // 记录了childView的临界状态的左、上、右、下
                            normal.set(childView.getLeft(), childView.getTop(), childView.getRight(), childView.getBottom());
                        }
                        // 重新布局
                        childView.layout(childView.getLeft(), childView.getTop() + dy / 2, childView.getRight(), childView.getBottom() + dy / 2);
                    }
                    // 重新赋值
                    lastY = eventY;
                    break;
                case MotionEvent.ACTION_UP:
                    break;
                default:
                    break;
            }
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 是否需要移动
     *
     * @return
     */
    private boolean isNeedMove() {
        // 获取用户在y轴方向上的偏移量 （上 + 下 -）
        int scrollY = this.getScrollY();
        // 获取子视图的高度
        int childViewMeasuredHeight = childView.getMeasuredHeight();
        // 获取布局的高度
        int scrollViewMeasuredHeight = this.getMeasuredHeight();
        // dy >= 0
        int dy = childViewMeasuredHeight - scrollViewMeasuredHeight;
        if (scrollY <= 0 || scrollY >= dy) {
            // 按照我们自定义的MyScrollView的方式处理
            return true;
        }
        // 其他处在临界范围内的，返回false。即表示，仍按照ScrollView的方式处理
        return false;
    }
}
