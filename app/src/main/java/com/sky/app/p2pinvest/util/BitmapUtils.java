package com.sky.app.p2pinvest.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

/**
 * Created with Android Studio.
 * 描述: Bitmap处理工具类
 * Date: 2018/3/19
 * Time: 11:34
 *
 * @author 晏琦云
 * @version ${VERSION}
 */
public class BitmapUtils {
    public static Bitmap circleBitmap(Bitmap source) {
        // 获取Bitmap的宽度
        int width = source.getWidth();
        // 以Bitmap的宽度值作为新的bitmap的宽高值。
        Bitmap bitmap = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
        // 以此bitmap为基准，创建一个画布
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        // 在画布上画一个圆
        canvas.drawCircle(width / 2, width / 2, width / 2, paint);

        //设置图片相交情况下的处理方式
        //setXfermode：设置当绘制的图像出现相交情况时候的处理方式的,它包含的常用模式有：
        //PorterDuff.Mode.SRC_IN 取两层图像交集部分,只显示上层图像
        //PorterDuff.Mode.DST_IN 取两层图像交集部分,只显示下层图像
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        // 在画布上绘制bitmap
        canvas.drawBitmap(source, 0, 0, paint);

        return bitmap;
    }
}
