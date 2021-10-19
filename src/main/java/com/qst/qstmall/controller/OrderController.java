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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
    public ModelAndView orderDetailHtml(HttpServletRequest request) {
        //获取请求参数中的订单号
        String order_no = request.getParameter("order_no");
        //根据订单号获取订单信息
        Order order = orderService.get_order(order_no);

        ModelAndView modelAndView = new ModelAndView("mall/order-detail");
        modelAndView.addObject("order", order);//添加订单信息
        return modelAndView;
    }

    //跳转到选择支付方式页面
    @RequestMapping("/pay-select.html")
    public ModelAndView patSelectHtml(HttpServletRequest request) {
        //获取请求参数中的订单号
        String order_no = request.getParameter("order_no");
        //根据订单号获取订单信息
        Order order = orderService.get_order(order_no);

        ModelAndView modelAndView = new ModelAndView("mall/pay-select");
        modelAndView.addObject("order", order);//添加订单信息
        return modelAndView;
    }

    //跳转到支付页面
    @RequestMapping("/payPage")
    public ModelAndView payPage(HttpServletRequest request) {
        //获取请求参数中的订单号
        String order_no = request.getParameter("order_no");
        //获取请求参数中的支付方式
        int pay_type = Integer.valueOf(request.getParameter("pay_type"));
        //根据订单号获取订单信息
        Order order = orderService.get_order(order_no);

        if(pay_type == 1){//支付宝支付
            ModelAndView modelAndView = new ModelAndView("mall/alipay");
            modelAndView.addObject("order", order);//添加订单信息
            return modelAndView;
        }
        if(pay_type == 2){//微信支付
            ModelAndView modelAndView = new ModelAndView("mall/wxpay");
            modelAndView.addObject("order", order);//添加订单信息
            return modelAndView;
        }

        //选择支付页面错误
        ModelAndView modelAndView = new ModelAndView("error/error_404");
        return modelAndView;
    }

    //保存订单，并跳转到选择支付方式页面
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
        long order_id = orderService.get_order(order_no).getOrder_id();
        //遍历订单商品集合缓存对象
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrder_id(order_id);//将获取的订单id添加到订单商品缓存对象中
            //添加订单商品详情
            orderItemService.add_orderItem(orderItem);//将封装好的订单商品添加到数据库中
        }

        return "forward:/pay-select.html?order_no="+order_no;//转发到选择支付方式页面
    }

    //处理取消订单请求
    @RequestMapping("/order-cancel")
    @ResponseBody
    public String orderCancel(HttpServletRequest request) {
        //获取请求参数中的订单号
        String order_no = request.getParameter("order_no");
        /*取消订单
         *更新订单状态（-1 已取消）
         */
        String s = orderService.update_order(order_no,-1,null,null);
        if(s == "success"){//取消订单成功
            return "200";//向客户端返回取消订单成功信息
        }
        //取消订单失败
        return null;
    }

    //处理确认收货请求
    @RequestMapping("/order-confirm")
    @ResponseBody
    public String orderConfirm(HttpServletRequest request) {
        //获取请求参数中的订单号
        String order_no = request.getParameter("order_no");
        /*确认收货
         *更新订单状态（4 交易成功）
         */
        String s = orderService.update_order(order_no,4,null,null);
        if(s == "success"){//确认收货成功
            return "200";//向客户端返回确认收货成功信息
        }
        //确认收货失败
        return null;
    }

    //处理删除订单请求
    @RequestMapping("/order-delete")
    @ResponseBody
    public String orderDelete(HttpServletRequest request) {
        //获取请求参数中的订单号
        String order_no = request.getParameter("order_no");
        /*删除订单
         *修改删除标识字段（1-已删除）
         */
        String s = orderService.delete_order(order_no);
        if(s == "success"){//删除订单成功
            return "200";//向客户端返回删除订单成功信息
        }
        //删除订单失败
        return null;
    }

    //处理支付完成请求
    @RequestMapping("/paySuccess")
    @ResponseBody
    public String paySuccess(int pay_type,String order_no,HttpServletRequest request) {
        /* 支付完成
         * 修改支付状态字段（1-支付成功）
         * 修改订单状态（1-已支付）
         * 修改支付方式（1.支付宝支付 2.微信支付）
         */
        orderService.update_order(order_no,null,null,1);//修改支付状态
        orderService.update_order(order_no,1,null,null);//修改订单状态
        String s = orderService.update_order(order_no,null,pay_type,null);//修改支付方式
        if(s == "success"){//支付完成请求成功
            orderService.update_order_pay_time(order_no);//修改支付时间
            return "200";//向客户端返回支付成功信息
        }
        //支付失败
        return null;
    }


}
