package com.qst.qstmall.service.impl;

import com.qst.qstmall.domin.GoodsInfo;
import com.qst.qstmall.domin.ShoppingCartItem;
import com.qst.qstmall.domin.User;
import com.qst.qstmall.mapper.ShoppingCartItemMapper;
import com.qst.qstmall.service.ShoppingCartInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/*
    购物车服务   实现类
 */
@Service
public class ShoppingCartService implements ShoppingCartInterface {
    @Autowired
    private ShoppingCartItemMapper shoppingCartItemMapper;
    @Autowired
    private GoodsInfoService goodsInfoService;
    @Autowired
    private ShoppingCartService shoppingCartService;


    @Override
    //声明获取当前用户最新购物车商品数量并写入session
    public void session_myShopping_count(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        //获取该用户的所有购物车商品信息
        ArrayList<ShoppingCartItem> userShoppingCarts = shoppingCartService.getUserShoppingCart(user.getUser_id());
        int myShopping_count = 0;   //我的商品数量
        for (ShoppingCartItem userShoppingCart : userShoppingCarts) {
            myShopping_count += userShoppingCart.getGoods_count();  //添加到我的商品数量
        }
        session.setAttribute("myShopping_count", myShopping_count);  //将我的商品数量写入服务器session中

        //期望客户端关闭后，session也能相同
        Cookie cookie = new Cookie("JSESSIONID", session.getId());
        cookie.setMaxAge(60 * 60);  //设置cookie存活时间为1个小时
        response.addCookie(cookie); //将cookie写入浏览器
    }

    @Override
    //重写通过用户id和商品id获取对应购物车商品信息
    public ShoppingCartItem getShoppingCart(ShoppingCartItem shoppingCartItem) {
        //获取查询购物车商品表返回的数据
        ShoppingCartItem shoppingCart = shoppingCartItemMapper.select_shoppingCart(shoppingCartItem);
        if (shoppingCart != null) {//查询到的数据不为空，返回此数据
            return shoppingCart;
        } else {//查询到的数据为空，返回空
            return null;
        }
    }

    @Override
    //重写插入购物车商品信息
    public String addShoppingCart(ShoppingCartItem shoppingCartItem) {
        //获取购物车商品表中的信息
        ShoppingCartItem shoppingCart = shoppingCartItemMapper.select_shoppingCart(shoppingCartItem);
        //判断购物车商品表中是否已存在同种信息
        if (shoppingCart == null) {//不存在同种信息
            //将此购物车商品信息插入到表中
            shoppingCartItemMapper.insert_shoppingCart(shoppingCartItem);
            return "add";
        } else {//存在同种信息
            //获取购物车中最新商品数量
            int goods_count = shoppingCartItem.getGoods_count() + shoppingCart.getGoods_count();
            if (goods_count <= 5) {//最新商品数量小于等于5
                //修改表中内容
                shoppingCartItem.setGoods_count(goods_count);
                shoppingCartItemMapper.update_shoppingCart(shoppingCart.getCart_item_id(), goods_count);
                return "update";
            } else {//最新商品数量超过最大值5
                return "exceed_max";
            }

        }

    }

    @Override
    //重写根据用户id获取此用户所有购物车商品信息
    public ArrayList<ShoppingCartItem> getUserShoppingCart(long user_id) {
        //通过用户id获取对应商品信息集合
        ArrayList<ShoppingCartItem> shoppingCartItems = shoppingCartItemMapper.select_userShoppingCart(user_id);
        if (shoppingCartItems != null) {//获取集合不为空
            //为购物车商品信息添加商品主图
            for (ShoppingCartItem shoppingCartItem : shoppingCartItems) {//遍历购物车商品信息集合
                GoodsInfo goodsInfo = goodsInfoService.getGoodsInfo(shoppingCartItem.getGoods_id());//根据商品id获取商品信息
                String goods_name = goodsInfo.getGoods_name();//获取商品名
                String goods_cover_img = goodsInfo.getGoods_cover_img();//获取商品主图
                int original_price = goodsInfo.getOriginal_price();//获取商品价格（单价）
                int goods_count = shoppingCartItem.getGoods_count();//获取商品数量
                int total_price = original_price * goods_count;//计算总价

                shoppingCartItem.setGoods_name(goods_name);//将商品名添加到购物车商品信息对象中
                shoppingCartItem.setGoods_cover_img(goods_cover_img);//将商品主图添加到购物车商品信息对象中
                shoppingCartItem.setOriginal_price(original_price);//将商品单价添加到购物车商品信息对象中
                shoppingCartItem.setTotal_price(total_price);//将商品总价添加到购物车商品对象中

            }
            return shoppingCartItems;
        }

        return null;
    }

    @Override
    //重写根据购物项id，修改删除标识字段(0-未删除 1-已删除)
    public String deleteShoppingCart(long cart_item_id) {
        //判断此商品项是否已删除
        if (shoppingCartItemMapper.select_is_deleted(cart_item_id)) {//查询结果为真，已删除
            return "error";//返回删除失败信息
        } else {//查询结果为假，未删除
            shoppingCartItemMapper.delete_shoppingCart(cart_item_id);//将删除标识字段修改为 1-已删除
            return "success";//返回删除成功信息
        }

    }

    @Override
    //声明根据购物项id，查询对应购物车商品信息
    public ShoppingCartItem getItemShoppingCart(long cart_item_id) {
        //根据购物项id获取对应购物车商品信息
        ShoppingCartItem shoppingCartItem = shoppingCartItemMapper.select_itemId(cart_item_id);
        if (shoppingCartItem == null) {//查询为空，则此购物车商品信息不存在或已删除
            return null;//返回空
        }
        //获取集合不为空
        //为购物车商品信息完善前端信息
        GoodsInfo goodsInfo = goodsInfoService.getGoodsInfo(shoppingCartItem.getGoods_id());//根据商品id获取商品信息
        String goods_name = goodsInfo.getGoods_name();//获取商品名
        String goods_cover_img = goodsInfo.getGoods_cover_img();//获取商品主图
        int original_price = goodsInfo.getOriginal_price();//获取商品价格（单价）
        int goods_count = shoppingCartItem.getGoods_count();//获取商品数量
        int total_price = original_price * goods_count;//计算总价

        shoppingCartItem.setGoods_name(goods_name);//将商品名添加到购物车商品信息对象中
        shoppingCartItem.setGoods_cover_img(goods_cover_img);//将商品主图添加到购物车商品信息对象中
        shoppingCartItem.setOriginal_price(original_price);//将商品单价添加到购物车商品信息对象中
        shoppingCartItem.setTotal_price(total_price);//将商品总价添加到购物车商品对象中

        return shoppingCartItem;//返回查询到的购物车商品信息
    }


    @Override
    //重写根据购物项id，查询并修改对应购物车商品信息
    public String updateItemShoppingCart(long cart_item_id, int goods_count) {
        ShoppingCartItem itemShoppingCart = this.getItemShoppingCart(cart_item_id);
        if (itemShoppingCart == null) {//查询失败
            return "error";//返回修改失败信息
        }
        //查询成功，修改此购物车商品信息
        shoppingCartItemMapper.update_shoppingCart(cart_item_id, goods_count);
        return "success";//返回修改成功信息
    }


}
