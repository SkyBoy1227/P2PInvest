package com.sky.app.p2pinvest.util;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

/**
 * Created with Android Studio.
 * 描述: 提供Drawable的工具类
 * Date: 2018/3/16
 * Time: 16:28
 *
 * @author 晏琦云
 * @version ${VERSION}
 */
public class DrawableUtils {
    /**
     * 得到Drawable
     *
     * @param rgb    填充颜色
     * @param radius 四周远郊半径
     * @return
     */
    public static Drawable getDrawable(int rgb, float radius) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        // 填充颜色
        gradientDrawable.setColor(rgb);
        // shape矩形
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        // 四周圆角半径
        gradientDrawable.setCornerRadius(radius);
        // 边框厚度与颜色
        gradientDrawable.setStroke(UIUtils.dp2px(1), rgb);
        return gradientDrawable;
    }

    /**
     * 得到选择器Drawable
     *
     * @param normalDrawable 正常的drawable
     * @param pressDrawable  按下的Drawable
     * @return
     */
    public static StateListDrawable getSelector(Drawable normalDrawable, Drawable pressDrawable) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        // 给当前的颜色选择器添加选中图片指向状态，未选中图片指向状态
        stateListDrawable.addState(new int[]{android.R.attr.state_enabled, android.R.attr.state_pressed}, pressDrawable);
        stateListDrawable.addState(new int[]{android.R.attr.state_enabled}, normalDrawable);
        // 设置默认状态
        stateListDrawable.addState(new int[]{}, normalDrawable);
        return stateListDrawable;
    }
}
