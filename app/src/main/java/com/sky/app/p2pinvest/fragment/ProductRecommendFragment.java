package com.sky.app.p2pinvest.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.sky.app.p2pinvest.R;
import com.sky.app.p2pinvest.common.BaseFragment;
import com.sky.app.p2pinvest.ui.randomLayout.StellarMap;
import com.sky.app.p2pinvest.util.UIUtils;

import java.util.Random;

import butterknife.BindView;

/**
 * Created with Android Studio.
 * 描述: 推荐理财产品页面
 * 如何在布局中添加子视图？
 * 1.直接在布局文件中，以标签的方式添加。---静态
 * 2.在java代码中，动态的添加子视图：
 * --->addView(...):一个一个的添加
 * --->设置adapter的方式，批量装配数据。
 * Date: 2018/3/12
 * Time: 17:03
 *
 * @author 晏琦云
 * @version ${VERSION}
 */
public class ProductRecommendFragment extends BaseFragment {
    @BindView(R.id.stellar_map)
    StellarMap stellarMap;

    /**
     * 提供装配的数据
     */
    private String[] datas = new String[]{"新手福利计划", "财神道90天计划", "硅谷钱包计划", "30天理财计划(加息2%)", "180天理财计划(加息5%)", "月月升理财计划(加息10%)",
            "中情局投资商业经营", "大学老师购买车辆", "屌丝下海经商计划", "美人鱼影视拍摄投资", "Android培训老师自己周转", "养猪场扩大经营",
            "旅游公司扩大规模", "摩托罗拉洗钱计划", "铁路局回款计划", "屌丝迎娶白富美计划"
    };

    // 声明两个子数组

    private String[] oneDatas = new String[datas.length / 2];
    private String[] twoDatas = new String[datas.length - datas.length / 2];

    private Random random = new Random();

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
        // 初始化子数组的数据
        for (int i = 0; i < datas.length; i++) {
            if (i < datas.length / 2) {
                oneDatas[i] = datas[i];
            } else {
                twoDatas[i - datas.length / 2] = datas[i];
            }
        }

        StellarAdapter adapter = new StellarAdapter();
        stellarMap.setAdapter(adapter);
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_product_recommend;
    }

    /**
     * 提供Adapter的实现类
     */
    class StellarAdapter implements StellarMap.Adapter {

        /**
         * 获取组的个数
         *
         * @return
         */
        @Override
        public int getGroupCount() {
            return 2;
        }

        /**
         * 返回每组中显示的数据的个数
         *
         * @param group
         * @return
         */
        @Override
        public int getCount(int group) {
            if (group == 0) {
                return datas.length / 2;
            } else {
                return datas.length - datas.length / 2;
            }
        }

        /**
         * 返回具体的view.
         *
         * @param group
         * @param position    不同的组别，position都是从0开始。
         * @param convertView
         * @return
         */
        @Override
        public View getView(int group, int position, View convertView) {
            TextView tv = new TextView(getActivity());
            // 设置属性
            // 设置文本的内容
            if (group == 0) {
                tv.setText(oneDatas[position]);
            } else {
                tv.setText(twoDatas[position]);
            }
            // 设置字体的大小
            tv.setTextSize(UIUtils.dp2px(5) + UIUtils.dp2px(random.nextInt(5)));
            // 设置字体的颜色
            // 00 ~ ff ; 00 ~ 255
            int red = random.nextInt(211);
            int green = random.nextInt(211);
            int blue = random.nextInt(211);
            tv.setTextColor(Color.rgb(red, green, blue));
            return tv;
        }

        /**
         * 返回下一组显示平移动画的组别。查看源码发现，此方法从未被调用。所以可以不重写
         *
         * @param group
         * @param degree
         * @return
         */
        @Override
        public int getNextGroupOnPan(int group, float degree) {
            return 0;
        }

        /**
         * 返回下一组显示缩放动画的组别。
         *
         * @param group
         * @param isZoomIn
         * @return
         */
        @Override
        public int getNextGroupOnZoom(int group, boolean isZoomIn) {
            if (group == 0) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}
