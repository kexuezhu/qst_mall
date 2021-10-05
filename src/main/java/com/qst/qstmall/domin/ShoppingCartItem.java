package com.qst.qstmall.domin;

import java.util.Date;

/*
    购物车商品实体类
 */
public class ShoppingCartItem {
    //购物项主键id
    private long cart_item_id;
    //用户主键id
    private long user_id;
    //关联商品id
    private long goods_id;
    //数量（最大为5）
    private int goods_count;
    //删除标识字段(0-未删除 1-已删除)
    private boolean is_deleted;
    //创建时间
    private Date create_time;
    //最新修改时间
    private Date update_time;

    //商品名称
    private String goods_name;
    //商品主图
    private String goods_cover_img;
    //商品价格（单价）
    private int original_price;
    //商品价格（总价）
    private int total_price;


    public long getCart_item_id() {
        return cart_item_id;
    }

    public void setCart_item_id(long cart_item_id) {
        this.cart_item_id = cart_item_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(long goods_id) {
        this.goods_id = goods_id;
    }

    public int getGoods_count() {
        return goods_count;
    }

    public void setGoods_count(int goods_count) {
        this.goods_count = goods_count;
    }

    public boolean isIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(boolean is_deleted) {
        this.is_deleted = is_deleted;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_cover_img() {
        return goods_cover_img;
    }

    public void setGoods_cover_img(String goods_cover_img) {
        this.goods_cover_img = goods_cover_img;
    }

    public int getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(int original_price) {
        this.original_price = original_price;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }
}
