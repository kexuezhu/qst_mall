package com.qst.qstmall.controller;


import com.qst.qstmall.domin.ShoppingCartItem;
import com.qst.qstmall.domin.User;
import com.qst.qstmall.service.impl.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    //跳转到购物车页面
    @GetMapping("/cart.html")
    public ModelAndView cartHtml(HttpServletRequest request) {
        //获取服务器session对象
        HttpSession session = request.getSession();
        //获取session中的user对象
        User user = (User) session.getAttribute("user");
        if (user == null){
            ModelAndView modelAndView = new ModelAndView("mall/login");
            return modelAndView;
        }
        //通过user_id获取对应的购物车商品信息集合
        ArrayList<ShoppingCartItem> userShoppingCarts = shoppingCartService.getUserShoppingCart(user.getUser_id());

        ModelAndView modelAndView = new ModelAndView("mall/cart");
        modelAndView.addObject("userShoppingCarts", userShoppingCarts);//添加用户购物车商品信息集合

        return modelAndView;
    }


    //加入购物车
    @GetMapping("/add-shop-cart")
    public void addCart(ShoppingCartItem shoppingCartItem, HttpServletRequest request, HttpServletResponse response) {
        //将商品信息加入到购物车商品表中
        String s = shoppingCartService.addShoppingCart(shoppingCartItem);
        if (s.equals("add")) {//购物车商品信息添加成功
            try {
                //返回添加成功信息
                response.getWriter().write(s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (s.equals("update")) {//购物车商品信息更新成功
            try {
                //返回更新成功信息
                response.getWriter().write(s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (s.equals("exceed_max")) {//商品数量超过最大值，购物车商品信息更新失败
            try {
                //返回更新失败信息
                response.getWriter().write(s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //获取当前用户最新购物车商品数量并写入session
        shoppingCartService.session_myShopping_count(request,response);

    }

    //删除购物项
    @DeleteMapping("/delete-shop-cart")
    public String deleteCart(long cart_item_id, HttpServletRequest request, HttpServletResponse response) {
        String s = shoppingCartService.deleteShoppingCart(cart_item_id);
        if (!s.equals("success")) {//删除失败
            return null;//向客户端返回空
        }
        //删除成功，修改我的商品数量
        shoppingCartService.session_myShopping_count(request,response);//获取当前用户最新购物车商品数量并写入session

        //向客户端返回删除成功信息 "200"
        return "200";

    }

    //清空购物车
    @DeleteMapping("/delete-shop-cart-all")
    public String deleteCartAll(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();//获取服务器中的session对象
        User user = (User)session.getAttribute("user");//获取session中的user对象
        ArrayList<ShoppingCartItem> userShoppingCarts = shoppingCartService.getUserShoppingCart(user.getUser_id());//获取该用户的所有未被标识删除的购物车商品信息
        int i = 0;//用于判断是否成功将所有购物项删除
        for (ShoppingCartItem userShoppingCart : userShoppingCarts) {
            String s = shoppingCartService.deleteShoppingCart(userShoppingCart.getCart_item_id());//根据购物项id删除对应购物车商品信息
            if(s.equals("success")){//删除成功
                i++;
            }
        }
        if(i == userShoppingCarts.size()){//所有购物项均删除成功
            //获取当前用户最新购物车商品数量并写入session
            shoppingCartService.session_myShopping_count(request,response);

            return "200";//向客户端返回删除成功信息 "200"
        }

        return "error";

    }

    //更新购物车
    @GetMapping("/update-shop-cart")
    public String updateCart(long cart_item_id, int goods_count,HttpServletRequest request,HttpServletResponse response){
        String s = shoppingCartService.updateItemShoppingCart(cart_item_id, goods_count);
        if(!s.equals("success")){//更新失败
            return null;//返回空
        }
        //更新成功
        shoppingCartService.session_myShopping_count(request,response);//获取当前用户最新购物车商品数量并写入session

        return "200";//向客户端返回修改成功信息 "200"

    }

    //处理购物车页面（cart.html）结算请求
    @GetMapping("/settle-shop-cart")
    public ModelAndView settleCart(String cart_item_id,HttpServletRequest request,HttpServletResponse response){
        /*将包含所有购物项id的字符串转换为list集合*/
        List<Long> cart_item_ids= Arrays.asList(cart_item_id.split(",")).stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
        //创建购物车商品信息集合缓存对象
        ArrayList<ShoppingCartItem> shoppingCartItems = new ArrayList<>();
        //创建商品总价格对象
        int total_price = 0;
        //遍历商品项id
        for (Long cartItemId : cart_item_ids) {
            ShoppingCartItem itemShoppingCart = shoppingCartService.getItemShoppingCart(cartItemId);//通过购物项id获取购物车商品信息
            total_price += itemShoppingCart.getTotal_price();//将各个商品价格相加
            shoppingCartItems.add(itemShoppingCart);//将获取的购物车商品信息添加到缓存对象中
        }

        ModelAndView modelAndView = new ModelAndView("mall/order-settle");
        modelAndView.addObject("mySettleShoppingCartItems",shoppingCartItems);//添加需要结算的所有商品信息
        modelAndView.addObject("priceTotal",total_price);//添加商品总价格
        return modelAndView;
    }

}
