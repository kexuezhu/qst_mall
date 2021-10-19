package com.qst.qstmall.domin;

/*
   管理员 实体类
 */
public class AdminUser {
    //管理员id
    private int admin_user_id;
    //管理员登录名称
    private String login_user_name;
    //管理员登录密码
    private String login_password;
    //管理员显示昵称
    private String nick_name;
    //是否锁定 0未锁定 1已锁定无法登陆
    private int locked;


    public int getAdmin_user_id() {
        return admin_user_id;
    }

    public void setAdmin_user_id(int admin_user_id) {
        this.admin_user_id = admin_user_id;
    }

    public String getLogin_user_name() {
        return login_user_name;
    }

    public void setLogin_user_name(String login_user_name) {
        this.login_user_name = login_user_name;
    }

    public String getLogin_password() {
        return login_password;
    }

    public void setLogin_password(String login_password) {
        this.login_password = login_password;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public int getLocked() {
        return locked;
    }

    public void setLocked(int locked) {
        this.locked = locked;
    }
}
