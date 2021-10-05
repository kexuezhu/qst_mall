package com.qst.qstmall.controller;


import com.qst.qstmall.domin.ShoppingCartItem;
import com.qst.qstmall.domin.User;
import com.qst.qstmall.service.impl.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;

@RestController
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
            return new ModelAndView("error/error_400");
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
        if (s == "add") {//购物车商品信息添加成功
            try {
                //返回添加成功信息
                response.getWriter().write(s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (s == "update") {//购物车商品信息更新成功
            try {
                //返回更新成功信息
                response.getWriter().write(s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (s == "exceed_max") {//商品数量超过最大值，购物车商品信息更新失败
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
        if (s != "success") {//删除失败
            return null;//向客户端返回空
        }
        //删除成功，修改我的商品数量
        //获取当前用户最新购物车商品数量并写入session
        shoppingCartService.session_myShopping_count(request,response);

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
            if(s == "success"){//删除成功
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
        String s = shoppingCartService.getItemShoppingCart(cart_item_id, goods_count);
        if(s != "success"){//更新失败
            return null;//返回空
        }
        //更新成功
        shoppingCartService.session_myShopping_count(request,response);//获取当前用户最新购物车商品数量并写入session

        return "200";//向客户端返回修改成功信息 "200"

    }


}
