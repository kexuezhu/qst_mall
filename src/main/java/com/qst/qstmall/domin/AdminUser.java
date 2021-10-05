package com.qst.qstmall.domin;

/*
   管理员 实体类
 */
public class AdminUser {
    //管理员id
    private long adminUserId;
    //管理员登录名称
    private String adminUsername;
    //管理员登录密码
    private String password;
    //管理员显示昵称
    private String nickName;
    //是否锁定 0未锁定 1已锁定无法登陆
    private int lock;

    public long getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(long adminUserId) {
        this.adminUserId = adminUserId;
    }

    public String getAdminUsername() {
        return adminUsername;
    }

    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getLock() {
        return lock;
    }

    public void setLock(int lock) {
        this.lock = lock;
    }
}
