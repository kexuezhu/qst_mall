package com.qst.qstmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.qst.qstmall.domin.Order;
import com.qst.qstmall.domin.OrderItem;
import com.qst.qstmall.mapper.OrderMapper;
import com.qst.qstmall.service.OrderInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/*
    订单服务    实现类
 */
@Service
public class OrderService implements OrderInterface {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemService orderItemService;

    @Override
    //重写插入新订单
    public void add_order(String order_no, long user_id, int total_price, String address){
        orderMapper.insert_order(order_no,user_id,total_price,address);//添加新订单
    }

    @Override
    //声明根据订单号获取订单id
    public long get_order_id(String order_no) {
        long order_id = orderMapper.select_order_id(order_no);
        return order_id;
    }

    @Override
    //重写根据用户id获取订单集合（分页）
    public ArrayList<Order> get_order(int pageNum, int pageSize, long user_id){
        //分页处理
        PageHelper.startPage(pageNum,pageSize);
        //获取当前用户的全部订单信息
        ArrayList<Order> orders= orderMapper.select_order(user_id);

        //遍历订单信息
        for (Order order : orders) {
            //根据订单id获取对应订单商品信息
            ArrayList<OrderItem> get_orderItems = orderItemService.get_orderItems(order.getOrder_id());
            //将获取的订单商品信息添加到订单信息中
            order.setOrderItems(get_orderItems);
        }

        return orders;
    }

    @Override
    //重写根据用户id获取订单总数
    public int get_order_count(long user_id){
        //获取当前用户的全部订单信息
        ArrayList<Order> orders= orderMapper.select_order(user_id);

        int count = orders.size();
        return count;
    }

}
