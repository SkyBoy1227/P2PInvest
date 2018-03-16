package com.sky.app.p2pinvest.util;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

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
}
