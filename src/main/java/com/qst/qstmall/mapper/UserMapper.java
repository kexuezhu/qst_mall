package com.qst.qstmall.mapper;

import com.qst.qstmall.domin.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/*
    数据库中tb_qst_mall_user用户表的操控类
*/
@Mapper
@Repository
public interface UserMapper {
    //登录验证
    //查询用户表中的登录名称和密码与用户输入的账号和密码进行对比
    //返回用户所有信息
    @Select("select * from tb_qst_mall_user where login_name = #{login_name} and password_md5= #{password_md5} AND is_deleted=0 AND locked_flag=0")
    User login_check(User user);

    //注册验证
    //查询用户表中的登录名称与用户输入的账号是否有重复
    //账户已存在返回账户名
    //账户不存在返回空对象，可以正常注册
    @Select("select login_name from tb_qst_mall_user where login_name = #{login_name} AND is_deleted=0")
    User register_check(User user);

    //将用户的注册信息插入用户表中
    @Insert("INSERT INTO tb_qst_mall_user(nick_name,login_name,password_md5) VALUES(#{nick_name},#{login_name},#{password_md5})")
    void addUser(User user);

    //根据用户id获取用户所有信息
    @Select("SELECT * FROM tb_qst_mall_user WHERE user_id=#{user_id} AND is_deleted=0")
    User selectUserInfo(long user_id);

    //根据用户id修改用户信息
    @Update("UPDATE tb_qst_mall_user SET nick_name=#{nick_name},introduce_sign=#{introduce_sign},address=#{address} WHERE user_id=#{user_id} AND is_deleted=0")
    int updateUserInfo(User user);

    //查询所有用户信息
    @Select("SELECT * FROM tb_qst_mall_user")
    ArrayList<User> select_all_user();

    //根据用户id修改用户锁定状态
    @Update("UPDATE tb_qst_mall_user SET locked_flag=#{locked_flag} WHERE user_id=#{user_id}")
    int update_locked_flag(User user);


}