package com.qst.qstmall.service.impl;

import com.qst.qstmall.domin.AdminUser;
import com.qst.qstmall.mapper.AdminUserMapper;
import com.qst.qstmall.service.AdminUserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
    管理员服务   实现类
 */
@Service
public class AdminUserService implements AdminUserInterface {
    @Autowired
    private AdminUserMapper adminUserMapper;

    @Override
    //重写登录验证
    public AdminUser login(AdminUser adminUser) {
        //登录验证
        AdminUser get_adminUser = adminUserMapper.login_check(adminUser);//获取数据库中的用户信息
        if (get_adminUser == null) {//账号或密码错误，登录失败
            //返回空
            return null;
        } else {//账号密码正确，登录成功
            //返回用户全部信息
            return get_adminUser;
        }
    }

    @Override
    //重写修改管理员登录名和昵称
    public boolean update_user_name(AdminUser adminUser){
        int i = adminUserMapper.update_user_name(adminUser);
        if(i!= 0){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    //声明修改管理员登录密码
    public boolean update_user_password(AdminUser adminUser){
        int i = adminUserMapper.update_user_password(adminUser);
        if(i!= 0){
            return true;
        }
        else{
            return false;
        }
    }



}
