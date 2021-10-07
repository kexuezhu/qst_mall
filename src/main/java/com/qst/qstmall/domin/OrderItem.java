package com.qst.qstmall.domin;

import java.util.Date;

/*
    订单商品    实体类
 */
public class OrderItem {
    //订单关联购物项主键id
    private long order_item_id;
    //订单主键id
    private long order_id;
    //关联商品id
    private long goods_id;
    //下单时商品的名称(订单快照)
    private String goods_name;
    //下单时商品的主图(订单快照)
    private String goods_cover_img;
    //下单时商品的价格(订单快照)
    private int selling_price;
    //数量(订单快照)
    private int goods_count;
    //创建时间
    private Date create_time;

    public long getOrder_item_id() {
        return order_item_id;
    }

    public void setOrder_item_id(long order_item_id) {
        this.order_item_id = order_item_id;
    }

    public long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(long order_id) {
        this.order_id = order_id;
    }

    public long getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(long goods_id) {
        this.goods_id = goods_id;
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

    public int getSelling_price() {
        return selling_price;
    }

    public void setSelling_price(int selling_price) {
        this.selling_price = selling_price;
    }

    public int getGoods_count() {
        return goods_count;
    }

    public void setGoods_count(int goods_count) {
        this.goods_count = goods_count;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }
}
