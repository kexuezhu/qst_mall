package com.qst.qstmall.service.impl;

import com.qst.qstmall.domin.Order;
import com.qst.qstmall.domin.OrderItem;
import com.qst.qstmall.mapper.OrderItemMapper;
import com.qst.qstmall.service.OrderItemInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/*
    订单商品信息服务  实现类
 */
@Service
public class OrderItemService implements OrderItemInterface {
    @Autowired
    private OrderItemMapper orderItemMapper;

    @Override
    //声明根据订单id获取订单商品信息
    public ArrayList<OrderItem> get_orderItems(long order_id){
        ArrayList<OrderItem> orderItems = orderItemMapper.select_orderItem(order_id);
        return orderItems;
    }


    @Override
    //重写添加订单商品信息
    public void add_orderItem(OrderItem orderItem) {
        orderItemMapper.add_orderItem(orderItem);
    }
}
