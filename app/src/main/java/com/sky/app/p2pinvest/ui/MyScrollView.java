package com.sky.app.p2pinvest.ui;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

import com.sky.app.p2pinvest.util.UIUtils;

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
     * 上一次x轴方向操作的坐标位置
     */
    private int lastX;

    /**
     * 手指按下时x轴坐标位置
     */
    private int downX;

    /**
     * 手指按下时y轴坐标位置
     */
    private int downY;

    /**
     * 用于记录临界状态的左、上、右、下
     */
    private Rect normal = new Rect();

    /**
     * 是否结束动画
     */
    private boolean isFinishAnimation = true;

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

    /**
     * 拦截:实现父视图对子视图的拦截
     * 是否拦截成功，取决于方法的返回值。
     *
     * @param ev
     * @return 返回值true:拦截成功。反之，拦截失败
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean isIntercept = false;
        int eventX = (int) ev.getX();
        int eventY = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = downX = eventX;
                lastY = downY = eventY;
                break;
            case MotionEvent.ACTION_MOVE:
                // 获取水平和垂直方向的移动距离
                int absX = Math.abs(eventX - downX);
                int absY = Math.abs(eventY - downY);
                if (absY >= absX && absY >= UIUtils.dp2px(10)) {
                    // 执行拦截
                    isIntercept = true;
                }
                lastX = eventX;
                lastY = eventY;
                break;
            default:
                break;
        }
        return isIntercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (childView != null && isFinishAnimation) {
            // 获取当前的Y轴坐标
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
                    if (isNeedAnimation()) {
                        // 使用平移动画
                        int translateY = childView.getBottom() - normal.bottom;
                        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 0, -translateY);
                        translateAnimation.setDuration(200);
                        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                                isFinishAnimation = false;
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                isFinishAnimation = true;
                                // 清除动画
                                childView.clearAnimation();
                                // 重新布局
                                childView.layout(normal.left, normal.top, normal.right, normal.bottom);
                                // 清除normal的数据
                                normal.setEmpty();
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                        // 启动动画
                        childView.startAnimation(translateAnimation);
                    }
                    break;
                default:
                    break;
            }
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 是否需要平移动画
     *
     * @return
     */
    private boolean isNeedAnimation() {
        return !normal.isEmpty();
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
        Log.e("TAG", "childViewMeasuredHeight = " + childViewMeasuredHeight);
        Log.e("TAG", "scrollViewMeasuredHeight = " + scrollViewMeasuredHeight);
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
