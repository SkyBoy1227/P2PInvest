package com.sky.app.p2pinvest.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.sky.app.p2pinvest.R;
import com.sky.app.p2pinvest.activity.LoginActivity;
import com.sky.app.p2pinvest.bean.User;
import com.sky.app.p2pinvest.common.BaseActivity;
import com.sky.app.p2pinvest.common.BaseFragment;
import com.sky.app.p2pinvest.util.BitmapUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import butterknife.BindView;

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
        // 1.读取本地保存的用户信息
        User user = ((BaseActivity) this.getActivity()).readUser();
        // 2.获取对象信息，并设置给相应的视图显示。
        tvMeName.setText(user.getName());
        Picasso.with(this.getActivity())
                .load(user.getImageurl())
                .placeholder(R.drawable.my_user_default)
                .error(R.drawable.my_user_default)
                .transform(new Transformation() {
                    @Override
                    public Bitmap transform(Bitmap source) {
                        Bitmap bitmap = BitmapUtils.circleBitmap(source);
                        source.recycle();
                        return bitmap;
                    }

                    @Override
                    public String key() {
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
        ivTitleBack.setVisibility(View.GONE);
        tvTitle.setText("我的资产");
        ivTitleSettings.setVisibility(View.GONE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }
}
