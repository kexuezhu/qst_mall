package com.qst.qstmall.service;

import com.qst.qstmall.domin.User;

import java.util.ArrayList;

/*
    用户个人服务    接口类
 */
public interface PersonalInterface {
    //声明登录验证
    User login(User user);

    //声明注册验证
    User register (User user);

    //声明获取个人信息方法
    User getUserInfo(long user_id);

    //声明修改个人信息方法
    String updateUserInfo(User user);

    //声明获取所有用户信息方法
    ArrayList<User> get_all_user();

    //声明修改用户锁定状态
    boolean update_locked_flag(User user);

}
