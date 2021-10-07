package com.qst.qstmall.controller;


import com.qst.qstmall.domin.Order;
import com.qst.qstmall.domin.OrderItem;
import com.qst.qstmall.domin.ShoppingCartItem;
import com.qst.qstmall.domin.User;
import com.qst.qstmall.mapper.OrderItemMapper;
import com.qst.qstmall.service.impl.OrderItemService;
import com.qst.qstmall.service.impl.OrderService;
import com.qst.qstmall.service.impl.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class OrderController {
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;

    //跳转到订单详情页
    @RequestMapping("/order-detail.html")
    public ModelAndView orderDetailHtml(HttpServletRequest request){


        ModelAndView modelAndView = new ModelAndView("mall/order-detail");
        return modelAndView;
    }


    //保存订单，并跳转到我的订单页面
    @RequestMapping("/saveOrder")
    public String saveOrder(String cart_item_id, HttpServletRequest request, HttpServletResponse response) {
        /*将包含所有购物项id的字符串转换为list集合*/
        List<Long> cart_item_ids = Arrays.asList(cart_item_id.split(",")).stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
        //获取服务器中的session
        HttpSession session = request.getSession();
        //获取当前用户id
        User user = (User) session.getAttribute("user");
        long user_id = user.getUser_id();
        String address = user.getAddress();
        //创建订单商品集合 缓存对象
        ArrayList<OrderItem> orderItems = new ArrayList<>();
        //创建商品总价格对象
        int total_price = 0;
        //遍历商品项id
        for (Long cartItemId : cart_item_ids) {
            ShoppingCartItem itemShoppingCart = shoppingCartService.getItemShoppingCart(cartItemId);//通过购物项id获取购物车商品信息
            total_price += itemShoppingCart.getTotal_price();//将各个商品价格相加
            shoppingCartService.deleteShoppingCart(cartItemId);//提交订单后，根据购物项id删除订单中对应的购物车信息
            //创建订单商品 缓存对象
            OrderItem orderItem = new OrderItem();
            orderItem.setGoods_id(itemShoppingCart.getGoods_id());//将获取的商品id添加到订单商品缓存对象中
            orderItem.setGoods_name(itemShoppingCart.getGoods_name());//将获取的商品名称添加到订单商品缓存对象中
            orderItem.setGoods_cover_img(itemShoppingCart.getGoods_cover_img());//将获取的商品主图添加到订单商品缓存对象中
            orderItem.setSelling_price(itemShoppingCart.getOriginal_price());//将获取的商品价格添加到订单商品缓存对象中
            orderItem.setGoods_count(itemShoppingCart.getGoods_count());//将获取的商品数量添加到订单商品缓存对象中
            orderItems.add(orderItem);//将封装好的订单商品缓存对象添加到订单商品集合缓存对象中
        }

        shoppingCartService.session_myShopping_count(request, response);//获取当前用户最新购物车商品数量并写入session

        //获取当前时间，当做订单号
        Date date = new Date();
        String order_no = date.getTime() + "";

        //添加新订单
        orderService.add_order(order_no, user_id, total_price, address);
        //根据订单号 获取新订单的订单id
        long order_id = orderService.get_order_id(order_no);
        //遍历订单商品集合缓存对象
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrder_id(order_id);//将获取的订单id添加到订单商品缓存对象中
            //添加订单商品详情
            orderItemService.add_orderItem(orderItem);//将封装好的订单商品添加到数据库中
        }

        return "redirect:/user/my-orders.html";//跳转到我的订单详情页
    }
}
