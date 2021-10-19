package com.qst.qstmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.qst.qstmall.domin.Order;
import com.qst.qstmall.domin.OrderItem;
import com.qst.qstmall.mapper.OrderMapper;
import com.qst.qstmall.service.OrderInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

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
    //声明根据订单号获取订单信息
    public Order get_order(String order_no) {
        //根据订单号获取订单信息
        Order order = orderMapper.select_for_order_no(order_no);
        //根据订单id获取对应订单商品信息
        ArrayList<OrderItem> get_orderItems = orderItemService.get_orderItems(order.getOrder_id());
        //将获取的订单商品信息添加到订单信息中
        order.setOrderItems(get_orderItems);

        return order;
    }

    @Override
    //重写根据用户id获取订单集合（分页）
    public ArrayList<Order> get_order_page(int pageNum, int pageSize, long user_id){
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
    //声明根据订单号 更新订单信息
    public String update_order(String order_no,Integer order_status,Integer pay_type,Integer pay_status) {
        //修改订单状态（-1 已取消）
        int i = orderMapper.update_order(order_no,order_status,pay_type,pay_status);
        if(i == 0){//更新订单返回为0，则更新失败
            return "error";//返回取消订单失败信息
        }
        //更新订单返回不为0，则更新成功
        return "success";//返回取消订单成功信息
    }

    @Override
    //声明根据订单号 更新支付时间
    public void update_order_pay_time(String order_no){
        Order order = new Order();
        Date date = new Date();
        order.setPay_time(date);
        Date pay_time = order.getPay_time();
        //更新支付时间
        orderMapper.update_order_pay_time(order_no,pay_time);
    }


    @Override
    //声明根据订单号 删除订单
    public String delete_order(String order_no) {
        int i = orderMapper.delete_order(order_no);
        if(i == 0){//删除订单返回为0，则删除失败
            return "error";//返回删除订单失败信息
        }
        //删除订单返回不为0，则删除成功
        return "success";//返回删除订单成功信息
    }

    @Override
    //重写获取所有未删除订单信息
    public ArrayList<Order> get_all_orders(){
        ArrayList<Order> orders = orderMapper.select_all_orders();//获取所有未删除订单集合
        return orders;//返回所有订单集合
    }

    @Override
    //声明根据订单id修改订单总价和收件人地址
    public boolean update_order_price_address(Order order){
        int i = orderMapper.update_order_price_address(order);
        if(i == 0){//修改失败
            return false;//返回假
        }
        //修改成功
        return true;//返回真
    }

    @Override
    //声明根据订单id修改订单状态
    public boolean update_order_status(Order order){
        int i = orderMapper.update_order_status(order);
        if(i == 0){//修改失败
            return false;//返回假
        }
        //修改成功
        return true;//返回真
    }


}
