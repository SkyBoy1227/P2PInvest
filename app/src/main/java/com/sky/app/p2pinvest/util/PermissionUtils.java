package com.sky.app.p2pinvest.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with Android Studio.
 * 描述: 权限管理工具
 * Date: 2018/3/19
 * Time: 17:34
 *
 * @author 晏琦云
 * @version ${VERSION}
 */
public class PermissionUtils {
    public static final String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CALL_PHONE
    };

    /**
     * 获取需要动态申请的权限数组
     *
     * @param context
     * @return
     */
    public static String[] checkPermissions(Context context) {
        List<String> permissionsNeed = new ArrayList<>();
        for (String permission : PERMISSIONS) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (context.checkSelfPermission(permission) == PackageManager.PERMISSION_DENIED) {
                    permissionsNeed.add(permission);
                }
            }
        }
        return permissionsNeed.toArray(new String[permissionsNeed.size()]);
    }
}
