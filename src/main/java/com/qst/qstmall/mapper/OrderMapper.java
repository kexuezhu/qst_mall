package com.qst.qstmall.mapper;

import com.qst.qstmall.domin.Order;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;


/*
    数据库中tb_qst_mall_order订单表的操控类
 */
@Mapper
@Repository
public interface OrderMapper {
    //根据用户id查询订单
    @Select("SELECT * FROM tb_qst_mall_order WHERE user_id=#{user_id} AND is_deleted=0")
    ArrayList<Order> select_order(long user_id);

    //根据订单号查询订单信息（我的订单页面使用，不显示已取消的订单）
    @Select("SELECT * FROM tb_qst_mall_order WHERE order_no=#{order_no} AND is_deleted=0")
    Order select_for_order_no(String order_no);

    //插入新订单
    @Insert("INSERT INTO tb_qst_mall_order(order_no, user_id, total_price, user_address) VALUES(#{order_no}, #{user_id}, #{total_price}, #{address})")
    void insert_order(String order_no, long user_id, int total_price, String address);

    //根据订单号更新订单信息
    @Update("<script>"
            + "UPDATE tb_qst_mall_order "
            + "<if test='order_status != null'>"
            + "  SET order_status=#{order_status} WHERE order_no=#{order_no} AND is_deleted=0"
            + "</if>"
            + "<if test='pay_type != null'>"
            + "  SET pay_type=#{pay_type} WHERE order_no=#{order_no} AND is_deleted=0  "
            + "</if>"
            + "<if test='pay_status != null'>"
            + "  SET pay_status=#{pay_status} WHERE order_no=#{order_no} AND is_deleted=0  "
            + "</if>"
            + "</script>")
    int update_order(@RequestParam("order_no") String order_no,@Param("order_status") Integer order_status,@Param("pay_type") Integer pay_type,@Param("pay_status") Integer pay_status);

    //根据订单号更新支付时间
    @Update("UPDATE tb_qst_mall_order SET pay_time=#{pay_time} WHERE order_no=#{order_no} AND is_deleted=0")
    int update_order_pay_time(String order_no, Date pay_time);

    //删除订单
    @Update("UPDATE tb_qst_mall_order SET is_deleted=1 WHERE order_no=#{order_no} AND is_deleted=0")
    int delete_order(String order_no);

    //查询所有未删除订单信息
    @Select("SELECT * FROM tb_qst_mall_order WHERE is_deleted=0")
    ArrayList<Order> select_all_orders();

    //根据订单id修改订单总价和收件人地址
    @Update("UPDATE tb_qst_mall_order SET total_price=#{total_price},user_address=#{user_address} WHERE order_id=#{order_id}")
    int update_order_price_address(Order order);

    //根据订单id修改订单状态
    @Update("UPDATE tb_qst_mall_order SET order_status=#{order_status} WHERE order_id=#{order_id}")
    int update_order_status(Order order);


}
