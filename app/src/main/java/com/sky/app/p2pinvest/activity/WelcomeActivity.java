package com.sky.app.p2pinvest.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.sky.app.p2pinvest.BuildConfig;
import com.sky.app.p2pinvest.R;
import com.sky.app.p2pinvest.bean.UpdateInfo;
import com.sky.app.p2pinvest.common.ActivityManager;
import com.sky.app.p2pinvest.common.AppNetConfig;
import com.sky.app.p2pinvest.common.MyApplication;
import com.sky.app.p2pinvest.util.UIUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created with Android Studio.
 * 描述: 欢迎页面
 * Date: 2018/3/6
 * Time: 11:52
 *
 * @author 晏琦云
 * @version ${VERSION}
 */
public class WelcomeActivity extends AppCompatActivity {

    /**
     * 跳转到主页面
     */
    private static final int GO_MAIN = 1;
    private static final int DOWNLOAD_VERSION_SUCCESS = 2;
    private static final int DOWNLOAD_SUCCESS = 3;
    private static final int DOWNLOAD_FAILURE = 4;

    @BindView(R.id.rl_welcome)
    RelativeLayout rlWelcome;
    @BindView(R.id.tv_version)
    TextView tvVersion;

    /**
     * 开始时间
     */
    private long startTime;

    /**
     * 更新应用的实体
     */
    private UpdateInfo updateInfo;

    /**
     * 下载好的更新APK文件
     */
    private File apkFile;

    /**
     * 当前应用的版本信息
     */
    private String version;

    private ProgressDialog progressDialog;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GO_MAIN:
                    startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                    // 结束activity的显示，并从栈空间中移除
                    ActivityManager.getInstance().removeActivity(WelcomeActivity.this);
                    break;
                case DOWNLOAD_VERSION_SUCCESS:
                    // 比较服务器获取的最新的版本跟本应用的版本是否一致
                    if (version.equals(updateInfo.getVersion())) {
                        UIUtils.toast("当前应用已经是最新版本！", false);
                        goMain();
                    } else {
                        new AlertDialog.Builder(WelcomeActivity.this)
                                .setTitle("下载最新版本")
                                .setMessage(updateInfo.getDesc())
                                .setPositiveButton("立即下载", (dialog, which) ->
                                        // 下载服务器保存的应用数据
                                        downloadApk())
                                .setNegativeButton("暂不下载", (dialog, which) -> goMain())
                                .setCancelable(false)
                                .show();
                    }
                    break;
                case DOWNLOAD_SUCCESS:
                    progressDialog.dismiss();
                    UIUtils.toast("下载文件成功！", false);
                    installApk();
                    // 结束activity的显示，并从栈空间中移除
                    ActivityManager.getInstance().removeActivity(WelcomeActivity.this);
                    break;
                case DOWNLOAD_FAILURE:
                    UIUtils.toast("下载文件失败！", false);
                    goMain();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 去掉窗口标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏顶部的状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        // 将当前的activity添加到ActivityManager中
        ActivityManager.getInstance().addActivity(this);
        // 获取当前应用的版本信息
        version = getVersion();
        // 更新页面显示的版本信息
        tvVersion.setText(version);
        // 启动动画
        showAnimation();
        updateApkFile();
    }

    /**
     * 联网更新应用
     */
    private void updateApkFile() {
        // 获取系统当前时间
        startTime = System.currentTimeMillis();
        // 判断手机是否可以联网
        boolean connected = isConnected();
        if (!connected) {
            // 没有移动网络
            UIUtils.toast("当前没有移动数据网络", false);
            goMain();
        } else {
            // 有移动网络
            // 联网获取服务器的最新版本数据
            AsyncHttpClient client = new AsyncHttpClient();
            String url = AppNetConfig.UPDATE;
            client.post(url, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(String content) {
                    // 解析json数据
                    updateInfo = JSON.parseObject(content, UpdateInfo.class);
                    handler.sendEmptyMessage(DOWNLOAD_VERSION_SUCCESS);
                }

                @Override
                public void onFailure(Throwable error, String content) {
                    UIUtils.toast("联网请求数据失败！", false);
                    goMain();
                }
            });
        }
    }

    private void downloadApk() {
        // 初始化水平进度条的dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("更新进度");
        progressDialog.setCancelable(false);
        progressDialog.show();
        // 初始化数据要保存的位置
        File filesDir;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            filesDir = this.getExternalFilesDir(null);
        } else {
            filesDir = this.getFilesDir();
        }
        apkFile = new File(filesDir, "update.apk");
        // 启动一个分线程联网下载数据：
        MyApplication.singleThreadPool.execute(() -> {
            HttpURLConnection connection = null;
            BufferedInputStream bis = null;
            FileOutputStream fos = null;
            try {
                URL url = new URL(updateInfo.getApkUrl());
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setReadTimeout(5000);
                connection.setConnectTimeout(5000);
                connection.connect();

                if (connection.getResponseCode() == 200) {
                    // 设置dialog的最大值
                    progressDialog.setMax(connection.getContentLength());
                    bis = new BufferedInputStream(connection.getInputStream());
                    fos = new FileOutputStream(apkFile);
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = bis.read(buffer)) != -1) {
                        progressDialog.incrementProgressBy(len);
                        // 更新dialog的进度
                        fos.write(buffer, 0, len);
                    }
                    handler.sendEmptyMessage(DOWNLOAD_SUCCESS);
                } else {
                    handler.sendEmptyMessage(DOWNLOAD_FAILURE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * 安装APK文件
     */
    private void installApk() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        // 判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileProvider", apkFile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        startActivity(intent);
    }

    /**
     * 当前版本号
     *
     * @return
     */
    private String getVersion() {
        String version = "未知版本";
        PackageManager manager = getPackageManager();
        try {
            PackageInfo packageInfo = manager.getPackageInfo(getPackageName(), 0);
            version = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // 如果找不到对应的应用包信息, 就返回"未知版本"
            e.printStackTrace();
        }
        return version;
    }

    /**
     * 跳转到主页面
     */
    private void goMain() {
        long endTime = System.currentTimeMillis();
        long delayTime = 3000 - (endTime - startTime);
        if (delayTime < 0) {
            delayTime = 0;
        }
        handler.sendEmptyMessageDelayed(GO_MAIN, delayTime);
    }

    /**
     * 判断手机是否联网
     * ConnectivityManager
     *
     * @return
     */
    private boolean isConnected() {
        boolean connected = false;
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null) {
            connected = networkInfo.isConnected();
        }
        return connected;
    }

    /**
     * 显示动画:
     * 透明度: 0--1 持续3s
     */
    private void showAnimation() {
        // 透明度
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(3000);
        // 设置动画的变化率
        alphaAnimation.setInterpolator(new AccelerateInterpolator());
        // 方式一：
//        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            /**
//             * 当动画结束时，调用如下方法
//             * @param animation
//             */
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
//                startActivity(intent);
//                // 销毁当前页面
//                finish();
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });

        // 方式二：使用Handler
//        handler.postDelayed(() -> {
//            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
//            startActivity(intent);
//            // 销毁当前页面
////            finish();
//            // 结束activity的显示，并从栈空间中移除
//            ActivityManager.getInstance().removeActivity(this);
//        }, 3000);
        // 启动动画
        rlWelcome.startAnimation(alphaAnimation);
    }

    @Override
    protected void onDestroy() {
        // 移除消息
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
