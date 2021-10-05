package com.qst.qstmall.domin;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.ArrayList;
import java.util.Date;

/*
    用户 实体类
*/
public class User {

    //用户id
    private long user_id;
    //用户名
    private String login_name;
    //密码
    private String password_md5;
    //用户昵称
    private String nick_name;
    //个性签名
    private String introduce_sign;
    //收货地址
    private String address;
    //注销标识字段(0-正常 1-已注销)
    private boolean is_delete;
    //锁定标识字段(0-未锁定 1-已锁定)
    private boolean locked_flag;
    //注册时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date create_time;

    //我的购物车（根据商品id确定购物车内的商品）
    private ArrayList<Long> myShoppingCart;


    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", login_name='" + login_name + '\'' +
                ", password_md5='" + password_md5 + '\'' +
                ", nick_name='" + nick_name + '\'' +
                ", introduce_sign='" + introduce_sign + '\'' +
                ", address='" + address + '\'' +
                ", is_delete=" + is_delete +
                ", locked_flag=" + locked_flag +
                ", create_time=" + create_time +
                '}';
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getLogin_name() {
        return login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    public String getPassword_md5() {
        return password_md5;
    }

    public void setPassword_md5(String password_md5) {
        this.password_md5 = password_md5;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getIntroduce_sign() {
        return introduce_sign;
    }

    public void setIntroduce_sign(String introduce_sign) {
        this.introduce_sign = introduce_sign;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isIs_delete() {
        return is_delete;
    }

    public void setIs_delete(boolean is_delete) {
        this.is_delete = is_delete;
    }

    public boolean isLocked_flag() {
        return locked_flag;
    }

    public void setLocked_flag(boolean locked_flag) {
        this.locked_flag = locked_flag;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public ArrayList<Long> getMyShoppingCart() {
        return myShoppingCart;
    }

    public void setMyShoppingCart(ArrayList<Long> myShoppingCart) {
        this.myShoppingCart = myShoppingCart;
    }
}
