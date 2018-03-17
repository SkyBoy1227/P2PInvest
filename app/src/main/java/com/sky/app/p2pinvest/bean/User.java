package com.sky.app.p2pinvest.bean;

/**
 * Created with Android Studio.
 * 描述: 用户信息
 * Date: 2018/3/17
 * Time: 15:35
 *
 * @author 晏琦云
 * @version ${VERSION}
 */
public class User {

    /**
     * 编号
     */
    private int id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 头像地址
     */
    private String imageurl;

    /**
     * 是否公安部认证
     */
    private boolean iscredit;

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public boolean isIscredit() {
        return iscredit;
    }

    public void setIscredit(boolean iscredit) {
        this.iscredit = iscredit;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", imageurl='" + imageurl + '\'' +
                ", iscredit=" + iscredit +
                '}';
    }
}
