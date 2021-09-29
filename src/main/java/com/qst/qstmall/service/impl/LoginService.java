package com.qst.qstmall.service.impl;

import com.qst.qstmall.domin.User;
import com.qst.qstmall.mapper.UserMapper;
import com.qst.qstmall.service.LoginInterface;
import com.qst.qstmall.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
    登录服务    实现类
 */
@Service
public class LoginService implements LoginInterface {
    @Autowired
    private UserMapper userMapper;

    //重写登录验证
    @Override
    public User login(User user) {
        //创建一个新的User对象用于接收服务器返回的User对象
        User userServer = userMapper.register_check(user);//只返回账号
        if (userServer == null) {//账号不存在
            //返回空对象
            return null;
        } else {
            //对密码进行md5加密
            try {
                user.setPassword_md5(Md5Util.encodeByMd5(user.getPassword_md5()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            //验证密码
            if (userMapper.login_check(user) == null) {//密码错误，登录失败
                //返回用户账号
                return userServer;
            } else {//账号密码正确，登录成功
                //返回用户账号密码
                return user;
            }
        }
    }
}
