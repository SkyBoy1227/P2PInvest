package com.sky.app.p2pinvest.common;

/**
 * Created with Android Studio.
 * 描述: 配置网络请求相关的地址
 * Date: 2018/3/6
 * Time: 16:42
 *
 * @author 晏琦云
 * @version ${VERSION}
 */
public class AppNetConfig {

    /**
     * ip地址
     */
    public static final String IPADDRESS = "222.85.156.50";

    /**
     * 服务器基本地址
     */
    public static final String BASE_URL = "http://" + IPADDRESS + ":81/P2PInvest/";

    /**
     * 访问“全部理财”产品
     */
    public static final String PRODUCT = BASE_URL + "product";

    /**
     * 登录
     */
    public static final String LOGIN = BASE_URL + "login";

    /**
     * 访问“homeFragment”
     */
    public static final String INDEX = BASE_URL + "index";

    /**
     * 注册
     */
    public static final String USERREGISTER = BASE_URL + "UserRegister";

    /**
     * 用户反馈
     */
    public static final String FEEDBACK = BASE_URL + "FeedBack";

    /**
     * 更新应用
     */
    public static final String UPDATE = BASE_URL + "update.json";
}
