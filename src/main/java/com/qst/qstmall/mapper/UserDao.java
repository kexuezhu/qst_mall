package com.qst.qstmall.mapper;

import com.qst.qstmall.domin.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/*对数据库中tb_qst_mall_user用户表的操控类*/
@Mapper
@Repository
public interface UserDao {
    //登录验证
    //查询用户表中的登录名称和密码与用户输入的账号和密码进行对比
    @Select("select * from tb_qst_mall_user where login_name = #{username} and password_md5= #{password}")
    User login_check(User user);

    //注册验证
    //查询用户表中的登录名称与用户输入的账号是否有重复
    //账户已存在返回账户名
    //账户不存在返回空对象，可以正常注册
    @Select("select * from tb_qst_mall_user where login_name = #{username}")
    User register_check(User user);

    @Insert("insert into tb_qst_mall_user(login_name,password_md5) values(#{username},#{password})")
    void addUser(User user);

    @Select("select * from tb_qst_mall_user where username = #{username}")
    User findOneByUsername(String username);

//    @Delete("delete from tb_qst_mall_user where id = #{id}")
//    public void delById(int id);
}