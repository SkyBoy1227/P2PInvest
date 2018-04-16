package com.sky.app.p2pinvest.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.sky.app.p2pinvest.R;
import com.sky.app.p2pinvest.activity.BarCharActivity;
import com.sky.app.p2pinvest.activity.GestureVerifyActivity;
import com.sky.app.p2pinvest.activity.LineCharActivity;
import com.sky.app.p2pinvest.activity.LoginActivity;
import com.sky.app.p2pinvest.activity.PieCharActivity;
import com.sky.app.p2pinvest.activity.RechargeActivity;
import com.sky.app.p2pinvest.activity.UserInfoActivity;
import com.sky.app.p2pinvest.activity.WithDrawActivity;
import com.sky.app.p2pinvest.bean.User;
import com.sky.app.p2pinvest.common.BaseActivity;
import com.sky.app.p2pinvest.common.BaseFragment;
import com.sky.app.p2pinvest.util.BitmapUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created with Android Studio.
 * 描述: 我的资产页
 * Date: 2018/3/5
 * Time: 21:05
 *
 * @author 晏琦云
 * @version ${VERSION}
 */
public class MeFragment extends BaseFragment {
    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_title_settings)
    ImageView ivTitleSettings;
    @BindView(R.id.iv_me_icon)
    ImageView ivMeIcon;
    @BindView(R.id.rl_me_icon)
    RelativeLayout rlMeIcon;
    @BindView(R.id.tv_me_name)
    TextView tvMeName;
    @BindView(R.id.recharge)
    ImageView recharge;
    @BindView(R.id.withdraw)
    ImageView withdraw;
    @BindView(R.id.tv_touzi)
    TextView tvTouzi;
    @BindView(R.id.tv_touzi_zhiguan)
    TextView tvTouziZhiguan;
    @BindView(R.id.tv_zichan)
    TextView tvZichan;

    @Override
    protected RequestParams getParams() {
        return null;
    }

    @Override
    protected String getUrl() {
        return null;
    }

    @Override
    protected void initData(String content) {
        isLogin();
    }

    /**
     * 判断用户是否已经登录
     */
    private void isLogin() {
        // 查看本地是否有用户的登录信息
        SharedPreferences sp = this.getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String name = sp.getString("name", "");
        if (TextUtils.isEmpty(name)) {
            // 本地没有保存过用户信息，给出提示：登录
            doLogin();
        } else {
            // 已经登录过，则直接加载用户的信息并显示
            doUser();
        }
    }

    /**
     * 加载用户信息并显示
     */
    private void doUser() {
        // 判断一下，是否开启了手势密码。如果开启：先输入手势密码
        SharedPreferences sp = this.getActivity().getSharedPreferences("secret_protect", Context.MODE_PRIVATE);
        boolean isOpen = sp.getBoolean("isOpen", false);
        if (isOpen) {
            Bundle bundle = new Bundle();
            User user = ((BaseActivity) getActivity()).readUser();
            bundle.putString(GestureVerifyActivity.PARAM_PHONE_NUMBER, user.getPhone());
            ((BaseActivity) getActivity()).goToActivity(GestureVerifyActivity.class, bundle);
        }
        // 1.读取本地保存的用户信息
        User user = ((BaseActivity) this.getActivity()).readUser();
        // 2.获取对象信息，并设置给相应的视图显示。
        tvMeName.setText(user.getName());
        // 判断本地是否已经保存头像的图片，如果有，则不再执行联网操作
        boolean isExist = readImage();
        if (isExist) {
            return;
        }
        // 使用Picasso联网获取图片
        Picasso.with(this.getActivity())
                .load(user.getImageurl())
                .placeholder(R.drawable.my_user_default)
                .error(R.drawable.my_user_default)
                .transform(new Transformation() {
                    /**
                     *
                     * @param source 下载以后的内存中的bitmap对象
                     * @return
                     */
                    @Override
                    public Bitmap transform(Bitmap source) {
                        // 压缩处理
                        Bitmap bitmap = BitmapUtils.zoom(source, ivMeIcon.getWidth(), ivMeIcon.getHeight());
                        // 圆形处理
                        bitmap = BitmapUtils.circleBitmap(bitmap);
                        // 回收bitmap资源
                        source.recycle();
                        return bitmap;
                    }

                    @Override
                    public String key() {
                        // 需要保证返回值不能为null。否则报错
                        return "";
                    }
                })
                .into(ivMeIcon);
    }

    /**
     * 登录
     */
    private void doLogin() {
        new AlertDialog.Builder(this.getActivity())
                .setTitle("提示")
                .setMessage("您还没有登录哦！么么~")
                .setPositiveButton("确定", (dialog, which) -> {
//                    UIUtils.toast("进入登录界面", false);
                    ((BaseActivity) MeFragment.this.getActivity()).goToActivity(LoginActivity.class, null);
                })
                .setCancelable(false)
                .show();
    }

    /**
     * 初始化Title
     */
    @Override
    protected void initTitle() {
        ivTitleBack.setVisibility(View.INVISIBLE);
        tvTitle.setText("我的资产");
        ivTitleSettings.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.iv_title_settings)
    public void setting() {
        // 启动用户信息界面的Activity
        ((BaseActivity) this.getActivity()).goToActivity(UserInfoActivity.class, null);
    }

    /**
     * 设置“充值"操作
     */
    @OnClick(R.id.recharge)
    public void recharge() {
        ((BaseActivity) this.getActivity()).goToActivity(RechargeActivity.class, null);
    }

    /**
     * 设置“提现”操作
     */
    @OnClick(R.id.withdraw)
    public void withdraw() {
        ((BaseActivity) this.getActivity()).goToActivity(WithDrawActivity.class, null);
    }

    @OnClick(R.id.tv_touzi)
    public void startLineChart() {
        ((BaseActivity) this.getActivity()).goToActivity(LineCharActivity.class, null);
    }

    @OnClick(R.id.tv_touzi_zhiguan)
    public void startBarChart() {
        ((BaseActivity) this.getActivity()).goToActivity(BarCharActivity.class, null);
    }

    @OnClick(R.id.tv_zichan)
    public void startPieChart() {
        ((BaseActivity) this.getActivity()).goToActivity(PieCharActivity.class, null);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    public void onResume() {
        super.onResume();
        // 读取本地保存的图片
        readImage();
    }

    /**
     * 判断是否存在本地头像，如果存在，则设置到用户头像
     *
     * @return
     */
    private boolean readImage() {
        File filesDir;
        // 判断sd卡是否挂载
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            // 路径1：storage/sdcard/Android/data/包名/files
            filesDir = this.getActivity().getExternalFilesDir(null);
        } else {
            // 路径：data/data/包名/files
            filesDir = this.getActivity().getFilesDir();
        }
        File file = new File(filesDir, "icon.png");
        if (file.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            ivMeIcon.setImageBitmap(bitmap);
            return true;
        }
        return false;
    }
}
