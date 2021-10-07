package com.qst.qstmall.service;

import com.qst.qstmall.domin.OrderItem;

import java.util.ArrayList;

/*
    订单商品信息服务  接口类
 */
public interface OrderItemInterface {
    //声明根据订单id获取订单商品信息
    ArrayList<OrderItem> get_orderItems(long order_id);

    //声明添加订单商品信息
    void add_orderItem(OrderItem orderItem);
}
