package com.sky.app.p2pinvest.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created with Android Studio.
 * 描述: MD5加密工具类
 * Date: 2018/3/17
 * Time: 15:54
 *
 * @author 晏琦云
 * @version ${VERSION}
 */
public class MD5Utils {
    /**
     * 获得加密后的MD5字符串
     *
     * @param sourceStr
     * @return
     */
    public static String MD5(String sourceStr) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        return result;
    }
}
