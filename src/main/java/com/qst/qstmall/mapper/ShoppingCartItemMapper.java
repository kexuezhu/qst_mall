package com.qst.qstmall.mapper;

import com.qst.qstmall.domin.ShoppingCartItem;
import com.qst.qstmall.domin.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import javax.net.ssl.SNIHostName;
import java.util.ArrayList;

/*
    数据库中tb_qst_mall_shopping_cart_item购物车商品表操作类
 */
@Mapper
@Repository
public interface ShoppingCartItemMapper {
    //根据用户id，商品id，查询购物车商品信息
    @Select("SELECT * FROM tb_qst_mall_shopping_cart_item WHERE user_id=#{user_id} AND goods_id=#{goods_id} AND is_deleted=0")
    ShoppingCartItem select_shoppingCart(ShoppingCartItem shoppingCartItem);

    //插入购物车商品信息
    @Insert("INSERT INTO tb_qst_mall_shopping_cart_item(user_id, goods_id, goods_count) VALUES(#{user_id}, #{goods_id}, #{goods_count})")
    void insert_shoppingCart(ShoppingCartItem shoppingCartItem);

    //修改购物车商品信息
    @Update("UPDATE tb_qst_mall_shopping_cart_item SET goods_count=#{goods_count} WHERE cart_item_id=#{cart_item_id} AND is_deleted=0")
    void update_shoppingCart(long cart_item_id, int goods_count);

    //根据用户id，查询此用户所有的购物车商品信息
    @Select("SELECT * FROM tb_qst_mall_shopping_cart_item WHERE user_id=#{user_id} AND is_deleted=0")
    ArrayList<ShoppingCartItem> select_userShoppingCart(long user_id);

    //根据购物项id，查询此购物车商品信息
    @Select("SELECT * FROM tb_qst_mall_shopping_cart_item WHERE cart_item_id=#{cart_item_id} And is_deleted=0")
    ShoppingCartItem select_itemId(long cart_item_id);

    //根据购物项id，查询删除标识字段(0-未删除 1-已删除)
    @Select("SELECT is_deleted FROM tb_qst_mall_shopping_cart_item WHERE cart_item_id=#{cart_item_id}")
    boolean select_is_deleted(long cart_item_id);

    //根据购物项id，修改删除标识字段(0-未删除 1-已删除)
    @Update("UPDATE tb_qst_mall_shopping_cart_item SET is_deleted=1 WHERE cart_item_id=#{cart_item_id}")
    void delete_shoppingCart(long cart_item_id);

}
