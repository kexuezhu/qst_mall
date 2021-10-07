package com.qst.qstmall.service.impl;

import com.qst.qstmall.domin.User;
import com.qst.qstmall.mapper.UserMapper;
import com.qst.qstmall.service.PersonalInterface;
import com.qst.qstmall.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
    用户个人服务  实现类
 */
@Service
public class PersonalService implements PersonalInterface {
    @Autowired
    private UserMapper userMapper;

    @Override
    //重写登录验证
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
            //获取数据库中的用户信息
            User get_user = userMapper.login_check(user);
            //验证密码
            if (get_user == null) {//密码错误，登录失败
                //返回用户账号
                return userServer;
            } else {//账号密码正确，登录成功
                //返回用户全部信息
                return get_user;
            }
        }
    }

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

    @Override
    //重写获取个人信息方法
    public User getUserInfo(long user_id){
        User user = userMapper.selectUserInfo(user_id);//根据用户id获取用户信息
        if(user == null){//获取的用户为空
            return null;//返回空
        }
        //获取的用户不为空
        return user;//返回此用户
    }


    @Override
    //重写修改个人信息方法
    public String updateUserInfo(User user) {
        //获取修改的行数
        int i = userMapper.updateUserInfo(user);
        if(i == 0){//修改的行数为0，修改失败
            return "error";//返回错误信息
        }
        //修改的行数不为0，修改成功
        return "success";//返回成功信息
    }
}
