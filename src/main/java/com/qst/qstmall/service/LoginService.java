package com.qst.qstmall.service;

import com.qst.qstmall.domin.User;
import com.qst.qstmall.mapper.UserDao;
import com.qst.qstmall.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements LoginInterface {
    @Autowired
    private UserDao userDao;

    //重写登录验证
    @Override
    public User login(User user) {
        //创建一个新的User对象用于接收服务器返回的User对象
        User userServer = userDao.register_check(user); //返回的对象中只有账号
        if (userServer == null) {//账号不存在
            //返回空对象
            return null;
        } else {
            //对密码进行md5加密
            try {
                user.setPassword(Md5Util.encodeByMd5(user.getPassword()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            //验证密码
            if (userDao.login_check(user) == null) {//密码错误，登录失败
                //返回用户账号
                return userServer;
            } else {//账号密码正确，登录成功
                //返回用户账号密码
                return user;
            }
        }
    }
}
