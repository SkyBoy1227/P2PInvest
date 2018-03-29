package com.sky.app.p2pinvest.bean;

/**
 * Created with Android Studio.
 * 描述: 请求服务器更新本地apk文件对应的实体类
 * Date: 2018/3/29
 * Time: 10:41
 *
 * @author 晏琦云
 * @version ${VERSION}
 */
public class UpdateInfo {
    /**
     * 服务器的最新版本值
     */
    private String version;

    /**
     * 最新版本的路径
     */
    private String apkUrl;

    /**
     * 版本更新细节
     */
    private String desc;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
