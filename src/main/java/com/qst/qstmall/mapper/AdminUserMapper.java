package com.qst.qstmall.mapper;

import com.qst.qstmall.domin.AdminUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/*
    数据库中tb_qst_mall_admin_user管理员表的操控类
 */
@Mapper
@Repository
public interface AdminUserMapper {
    //登录验证
    //查询管理员表中的管理员登录名称和密码与输入的账号和密码进行对比
    //返回用户所有信息
    @Select("select * from tb_qst_mall_admin_user where login_user_name = #{login_user_name} and login_password= #{login_password} AND locked=0")
    AdminUser login_check(AdminUser adminUser);

    //修改登录名称和昵称
    @Update("UPDATE tb_qst_mall_admin_user SET login_user_name=#{login_user_name},nick_name=#{nick_name} WHERE admin_user_id=#{admin_user_id}")
    int update_user_name(AdminUser adminUser);

    //修改登录密码
    @Update("UPDATE tb_qst_mall_admin_user SET login_password=#{login_password},nick_name=#{nick_name} WHERE admin_user_id=#{admin_user_id}")
    int update_user_password(AdminUser adminUser);

}
