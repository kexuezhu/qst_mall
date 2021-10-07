package com.qst.qstmall.mapper;

import com.qst.qstmall.domin.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;


/*
    数据库中tb_qst_mall_order订单表的操控类
 */
@Mapper
@Repository
public interface OrderMapper {
    //根据用户id查询订单
    @Select("SELECT * FROM tb_qst_mall_order WHERE user_id=#{user_id} AND is_deleted=0")
    ArrayList<Order> select_order(long user_id);

    //根据订单号查询订单id
    @Select("SELECT order_id FROM tb_qst_mall_order WHERE order_no=#{order_no}")
    long select_order_id(String order_no);

    //插入新订单
    @Insert("INSERT INTO tb_qst_mall_order(order_no, user_id, total_price, user_address) VALUES(#{order_no}, #{user_id}, #{total_price}, #{address})")
    void insert_order(String order_no, long user_id, int total_price, String address);

}
