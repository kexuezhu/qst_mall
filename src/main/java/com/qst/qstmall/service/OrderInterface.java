package com.qst.qstmall.service;

import com.qst.qstmall.domin.Order;

import java.util.ArrayList;

/*
    订单服务    接口类
 */
public interface OrderInterface {

    //声明插入新订单
    void add_order(String order_no, long user_id, int total_price, String address);

    //声明根据订单号获取订单信息
    Order get_order(String order_no);

    //声明根据用户id获取订单集合（分页）
    ArrayList<Order> get_order_page(int pageNum, int pageSize, long user_id);

    //声明根据订单号 更新订单信息
    String update_order(String order_no,Integer order_status,Integer pay_type,Integer pay_status);

    //声明根据订单号 更新支付时间
    void update_order_pay_time(String order_no);

    //声明根据订单号 删除订单
    String delete_order(String order_no);

    //声明获取所有未删除订单信息
    ArrayList<Order> get_all_orders();

    //声明根据订单id修改订单总价和收件人地址
    boolean update_order_price_address(Order order);

    //声明根据订单id修改订单状态
    boolean update_order_status(Order order);

}
