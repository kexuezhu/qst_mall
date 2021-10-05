package com.qst.qstmall.service.impl;

import com.qst.qstmall.domin.User;
import com.qst.qstmall.mapper.UserMapper;
import com.qst.qstmall.service.RegisterInterface;
import com.qst.qstmall.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/*
    注册服务    实现类
 */
@Service
public class RegisterService implements RegisterInterface {
    @Autowired
    private UserMapper userMapper;

    @Override
    //重写注册验证
    public User register(User user) {
        if(userMapper.register_check(user) == null){    //账号不存在，可以进行注册
            //对密码进行md5加密
            try {
                user.setPassword_md5(Md5Util.encodeByMd5(user.getPassword_md5()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            //用户昵称默认是用户名
            user.setNick_name(user.getLogin_name());
            //向数据库中插入注册的用户信息
            userMapper.addUser(user);
            //返回此用户对象
            return user;
        }
        else{
            //账号已存在，注册失败，返回空对象
            return null;
        }
    }
}
