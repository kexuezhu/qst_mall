package com.qst.qstmall.service;

import com.qst.qstmall.domin.User;

/*
    登录服务    接口类
 */
public interface LoginInterface {
    //声明登录验证
    User login(User user);
}
