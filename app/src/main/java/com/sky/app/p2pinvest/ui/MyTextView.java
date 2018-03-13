package com.sky.app.p2pinvest.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created with Android Studio.
 * 描述: 自定义TextView，实现跑马灯效果
 * Date: 2018/3/13
 * Time: 11:47
 *
 * @author 晏琦云
 * @version ${VERSION}
 */
public class MyTextView extends AppCompatTextView {
    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean isFocused() {
        // 获取焦点
        return true;
    }
}
