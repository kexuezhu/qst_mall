package com.qst.qstmall.mapper;

import com.qst.qstmall.domin.OrderItem;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;


/*
    数据库中tb_qst_mall_order_item订单商品表的操控类
 */
@Mapper
@Repository
public interface OrderItemMapper {
    //根据订单id查询订单商品信息
    @Select("SELECT * FROM tb_qst_mall_order_item WHERE order_id=#{order_id}")
    ArrayList<OrderItem> select_orderItem(long order_id);

    //添加订单商品信息
    @Insert("INSERT tb_qst_mall_order_item(order_id,goods_id,goods_name,goods_cover_img,selling_price,goods_count) VALUES(#{order_id},#{goods_id},#{goods_name},#{goods_cover_img},#{selling_price},#{goods_count})")
    void add_orderItem(OrderItem orderItem);


}
