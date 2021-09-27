package com.qst.qstmall.service;

import com.qst.qstmall.domin.User;
import com.qst.qstmall.mapper.UserDao;
import com.qst.qstmall.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterService implements RegisterInterface{
    @Autowired
    private UserDao userDao;

    //重写注册验证
    @Override
    public User register(User user) {
        //创建一个新的User对象用于接收服务器返回的User对象
        User userServer = userDao.register_check(user); //返回的对象中只有账号
        if(userServer == null){    //账号不存在，可以进行注册
            //对密码进行md5加密
            try {
                user.setPassword(Md5Util.encodeByMd5(user.getPassword()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            //向数据库中插入注册的用户信息
            userDao.addUser(user);
            //返回此用户对象账号
            return userServer;
        }
        else{
            //账号已存在，注册失败，返回空对象
            return null;
        }
    }
}
