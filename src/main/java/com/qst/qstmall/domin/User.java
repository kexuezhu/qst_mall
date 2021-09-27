package com.qst.qstmall.domin;

import java.util.Date;

/*用户实体类*/
public class User {

    //用户名
    private String username;
    //密码
    private String password;
    //用户id
    private long userId;
    //用户昵称
    private String nickName;
    //个性签名
    private String introduceSign;
    //收货地址
    private String address;
    //注销标识字段(0-正常 1-已注销)
    private boolean isDelete;
    //锁定标识字段(0-未锁定 1-已锁定)
    private boolean lockedFlag;
    //注册时间
    private Date date;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getIntroduceSign() {
        return introduceSign;
    }

    public void setIntroduceSign(String introduceSign) {
        this.introduceSign = introduceSign;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public boolean isLockedFlag() {
        return lockedFlag;
    }

    public void setLockedFlag(boolean lockedFlag) {
        this.lockedFlag = lockedFlag;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
