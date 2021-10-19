package com.qst.qstmall.controller;


import com.qst.qstmall.domin.Order;
import com.qst.qstmall.service.impl.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@Controller
@RequestMapping("/admin")
public class QstMallOrdersController {
    @Autowired
    private OrderService orderService;


    //跳转到订单管理页面
    @RequestMapping("/orders")
    public ModelAndView qst_mall_orderHtml(){
        ArrayList<Order> orders = orderService.get_all_orders();//获取所有未删除订单集合

        ModelAndView modelAndView = new ModelAndView("admin/qst_mall_order");
        modelAndView.addObject("path","orders");//添加当前页面标识
        modelAndView.addObject("orders",orders);//添加订单集合
        return modelAndView;
    }

    //处理管理员修改订单信息请求
    @PostMapping("/orders/update")
    @ResponseBody
    public String order_update(Order order){
        boolean b = orderService.update_order_price_address(order);
        if(b){//修改成功
            return "success";//返回修改成功信息
        }
        //修改失败
        return "error";//返回修改失败信息
    }

    //处理管理员修改订单状态请求
    @PostMapping("/orders/update_order_status")
    @ResponseBody
    public String order_update_order_status(Order order){
        boolean b = orderService.update_order_status(order);
        if(b){//修改成功
            return "success";//返回修改成功信息
        }
        //修改失败
        return "error";//返回修改失败信息
    }


}
