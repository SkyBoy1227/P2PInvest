package com.sky.app.p2pinvest.fragment;

import android.content.Context;
import android.os.SystemClock;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.sky.app.p2pinvest.R;
import com.sky.app.p2pinvest.bean.Image;
import com.sky.app.p2pinvest.bean.Index;
import com.sky.app.p2pinvest.bean.Product;
import com.sky.app.p2pinvest.common.AppNetConfig;
import com.sky.app.p2pinvest.common.BaseFragment;
import com.sky.app.p2pinvest.common.MyApplication;
import com.sky.app.p2pinvest.ui.RoundProgress;
import com.sky.app.p2pinvest.util.UIUtils;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Created with Android Studio.
 * 描述: 主页
 * Date: 2018/3/5
 * Time: 21:01
 *
 * @author 晏琦云
 * @version ${VERSION}
 */
public class HomeFragment extends BaseFragment {
    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_title_settings)
    ImageView ivTitleSettings;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.tv_home_product)
    TextView tvHomeProduct;
    @BindView(R.id.rp_home)
    RoundProgress rpHome;
    @BindView(R.id.tv_main_year_rate)
    TextView tvMainYearRate;

    private Index index;

    /**
     * 初始化数据
     * @param content
     */
    @Override
    protected void initData(String content) {
        index = new Index();
        AsyncHttpClient client = new AsyncHttpClient();
        // 访问的url
        String url = AppNetConfig.INDEX;
        client.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String content) {
                // 200：响应成功
                // 解析json数据 GSON / FASTJSON
                JSONObject jsonObject = JSON.parseObject(content);
                // 解析json对象数据
                String proInfo = jsonObject.getString("proInfo");
                Product product = JSON.parseObject(proInfo, Product.class);
                // 解析json数组数据
                String imageArr = jsonObject.getString("imageArr");
                List<Image> images = JSON.parseArray(imageArr, Image.class);
                index.proInfo = product;
                index.imageArr = images;

                // 更新数据
                tvHomeProduct.setText(product.name);
                tvMainYearRate.setText(product.yearRate + "%");
                // 当前总进度
                int progress = Integer.parseInt(product.progress);
                MyApplication.singleThreadPool.execute(() -> {
                    rpHome.setMax(100);
                    for (int i = 0; i < progress; i++) {
                        rpHome.setProgress(i + 1);
                        rpHome.postInvalidate();
                        SystemClock.sleep(20);
                    }
                });

                // 设置banner样式
                banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
                // 设置图片加载器
                banner.setImageLoader(new GlideImageLoader());
                // 设置图片集合
                ArrayList<String> imageUrls = new ArrayList<>(index.imageArr.size());
                for (int i = 0; i < index.imageArr.size(); i++) {
                    imageUrls.add(index.imageArr.get(i).IMAURL);
                }
                banner.setImages(imageUrls);
                // 设置banner动画效果
                banner.setBannerAnimation(Transformer.DepthPage);
                // 设置标题集合（当banner样式有显示title时）
                List<String> titles = Arrays.asList("分享砍学费", "人脉总动员", "想不到你是这样的APP", "购物节，爱不单行");
                banner.setBannerTitles(titles);
                // 设置自动轮播，默认为true
                banner.isAutoPlay(true);
                // 设置轮播时间
                banner.setDelayTime(1500);
                // 设置指示器位置（当banner模式中有指示器时）
                banner.setIndicatorGravity(BannerConfig.CENTER);
                // banner设置方法全部调用完毕时最后调用
                banner.start();
            }

            @Override
            public void onFailure(Throwable error, String content) {
                // 响应失败
                Toast.makeText(UIUtils.getContext(), "联网获取数据失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 初始化Title
     */
    @Override
    protected void initTitle() {
        ivTitleBack.setVisibility(View.GONE);
        tvTitle.setText("首页");
        ivTitleSettings.setVisibility(View.GONE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    class GlideImageLoader extends ImageLoader {

        private static final long serialVersionUID = -7477483748590727208L;

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            /**
             注意：
             1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
             2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
             传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
             切记不要胡乱强转！
             */

            // Picasso 加载图片简单用法
            Picasso.with(context).load((String) path).into(imageView);
        }
    }
}
