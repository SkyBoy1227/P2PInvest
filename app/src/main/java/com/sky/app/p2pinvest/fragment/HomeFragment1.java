package com.sky.app.p2pinvest.fragment;

import android.support.v4.app.Fragment;

/**
 * Created with Android Studio.
 * 描述: 主页
 * Date: 2018/3/5
 * Time: 21:01
 *
 * @author 晏琦云
 * @version ${VERSION}
 */
public class HomeFragment1 extends Fragment {
//    @BindView(R.id.iv_title_back)
//    ImageView ivTitleBack;
//    @BindView(R.id.tv_title)
//    TextView tvTitle;
//    @BindView(R.id.iv_title_settings)
//    ImageView ivTitleSettings;
//    Unbinder unbinder;
//    @BindView(R.id.vp_home)
//    ViewPager vpHome;
//    @BindView(R.id.tv_home_product)
//    TextView tvHomeProduct;
//    @BindView(R.id.tv_main_year_rate)
//    TextView tvMainYearRate;
//    @BindView(R.id.cp_main_indicator)
//    CirclePageIndicator cpMainIndicator;
//
//    private Index index;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        // context实例：activity
////        View view = View.inflate(getActivity(), R.layout.fragment_home, null);
//        // context实例：application
//        View view = UIUtils.getView(R.layout.fragment_home);
//        unbinder = ButterKnife.bind(this, view);
//        initTitle();
//        initData();
//        return view;
//    }
//
//    /**
//     * 初始化数据
//     */
//    private void initData() {
//        index = new Index();
//        AsyncHttpClient client = new AsyncHttpClient();
//        // 访问的url
//        String url = AppNetConfig.INDEX;
//        client.post(url, new AsyncHttpResponseHandler() {
//            @Override
//            public void onSuccess(String content) {
//                // 200：响应成功
//                // 解析json数据 GSON / FASTJSON
//                JSONObject jsonObject = JSON.parseObject(content);
//                // 解析json对象数据
//                String proInfo = jsonObject.getString("proInfo");
//                Product product = JSON.parseObject(proInfo, Product.class);
//                // 解析json数组数据
//                String imageArr = jsonObject.getString("imageArr");
//                List<Image> images = JSON.parseArray(imageArr, Image.class);
//                index.proInfo = product;
//                index.imageArr = images;
//
//                // 更新数据
//                tvHomeProduct.setText(product.name);
//                tvMainYearRate.setText(product.yearRate + "%");
//
//                // 设置ViewPager
//                vpHome.setAdapter(new MyAdapter());
//                // 设置小圆圈的显示
//                cpMainIndicator.setViewPager(vpHome);
//            }
//
//            @Override
//            public void onFailure(Throwable error, String content) {
//                // 响应失败
//                Toast.makeText(UIUtils.getContext(), "联网获取数据失败", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    /**
//     * 初始化Title
//     */
//    private void initTitle() {
//        ivTitleBack.setVisibility(View.GONE);
//        tvTitle.setText("首页");
//        ivTitleSettings.setVisibility(View.GONE);
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        unbinder.unbind();
//    }
//
//    class MyAdapter extends PagerAdapter {
//
//        @Override
//        public int getCount() {
//            List<Image> images = index.imageArr;
//            return images == null ? 0 : images.size();
//        }
//
//        @Override
//        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
//            return view == object;
//        }
//
//        @NonNull
//        @Override
//        public Object instantiateItem(@NonNull ViewGroup container, int position) {
//            ImageView imageView = new ImageView(getActivity());
//            // 1.imageView显示图片
//            String imaurl = index.imageArr.get(position).IMAURL;
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            // 使用Picasso联网下载图片
//            Picasso.with(getActivity())
//                    .load(imaurl)
//                    .into(imageView);
//            // 2.imageView添加到容器中
//            container.addView(imageView);
//            return imageView;
//        }
//
//        @Override
//        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//            container.removeView((View) object);
//        }
//    }
}
