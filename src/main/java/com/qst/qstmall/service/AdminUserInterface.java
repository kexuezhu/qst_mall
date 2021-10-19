package com.qst.qstmall.service;

import com.qst.qstmall.domin.AdminUser;

/*
    管理员服务   接口类
 */
public interface AdminUserInterface {
    //声明登录验证
    AdminUser login(AdminUser adminUser);

    //声明修改管理员登录名和昵称
    boolean update_user_name(AdminUser adminUser);

    //声明修改管理员登录密码
    boolean update_user_password(AdminUser adminUser);

}
