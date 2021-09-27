package com.qst.qstmall.service;

import com.qst.qstmall.domin.User;

public interface LoginInterface {
    //登录验证
    User login(User user);
}
