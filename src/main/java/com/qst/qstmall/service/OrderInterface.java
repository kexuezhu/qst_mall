package com.qst.qstmall.service;

import com.qst.qstmall.domin.Order;

import java.util.ArrayList;

/*
    订单服务    接口类
 */
public interface OrderInterface {
    //声明根据订单号获取订单id
    long get_order_id(String order_no);

    //声明插入新订单
    void add_order(String order_no, long user_id, int total_price, String address);

    //声明根据用户id获取订单集合（分页）
    ArrayList<Order> get_order(int pageNum, int pageSize, long user_id);

    //声明根据用户id获取订单总数
    public int get_order_count(long user_id);


}
