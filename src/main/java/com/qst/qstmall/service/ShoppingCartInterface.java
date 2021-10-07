package com.qst.qstmall.service;

import com.qst.qstmall.domin.ShoppingCartItem;
import com.qst.qstmall.domin.User;

import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/*
    购物车服务   接口类
 */
public interface ShoppingCartInterface {
    //声明获取当前用户最新购物车商品数量并写入session
    void session_myShopping_count(HttpServletRequest request, HttpServletResponse response);

    //声明通过用户id和商品id获取对应购物车商品信息
    ShoppingCartItem getShoppingCart(ShoppingCartItem shoppingCartItem);

    //声明插入购物车商品信息
    String addShoppingCart(ShoppingCartItem shoppingCartItem);

    //声明根据用户id获取此用户所有购物车商品信息
    ArrayList<ShoppingCartItem> getUserShoppingCart(long user_id);

    //声明根据购物项id，修改删除表示字段字段(0-未删除 1-已删除)
    String deleteShoppingCart(long cart_item_id);

    //声明根据购物项id，查询对应购物车商品信息
    ShoppingCartItem getItemShoppingCart(long cart_item_id);

    //声明根据购物项id，修改对应购物车商品信息
    String updateItemShoppingCart(long cart_item_id, int goods_count);

}
