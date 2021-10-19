package com.qst.qstmall.domin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
    商品信息 实体类
 */
public class GoodsInfo {
    //商品表主键id
    private long goods_id;
    //商品名
    private String goods_name;
    //商品简介
    private String goods_intro;
    //关联分类id
    private long goods_category_id;
    //商品主图
    private String goods_cover_img;

    //商品轮播图
    private String goods_carousel;
    //商品轮播图集合
    private ArrayList<String> goods_carouselList;

    //商品详情
    private String goods_detail_content;
    //商品价格
    private int original_price;
    //商品库存数量
    private int stock_num;
    //商品标签
    private String tag;
    //商品上架状态 0-上架 1-下架
    private int goods_sell_status;
    //添加者主键id
    private int create_user;
    //商品添加时间
    private String create_time;
    //修改者主键id
    private int update_user;
    //商品修改时间
    private String update_time;

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

    public String getGoods_intro() {
        return goods_intro;
    }

    public void setGoods_intro(String goods_intro) {
        this.goods_intro = goods_intro;
    }

    public long getGoods_category_id() {
        return goods_category_id;
    }

    public void setGoods_category_id(long goods_category_id) {
        this.goods_category_id = goods_category_id;
    }

    public String getGoods_cover_img() {
        return goods_cover_img;
    }

    public void setGoods_cover_img(String goods_cover_img) {
        this.goods_cover_img = goods_cover_img;
    }

    public String getGoods_carousel() {
        return goods_carousel;
    }

    public void setGoods_carousel(String goods_carousel) {
        this.goods_carousel = goods_carousel;
    }

    public String getGoods_detail_content() {
        return goods_detail_content;
    }

    public void setGoods_detail_content(String goods_detail_content) {
        this.goods_detail_content = goods_detail_content;
    }

    public int getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(int original_price) {
        this.original_price = original_price;
    }

    public int getStock_num() {
        return stock_num;
    }

    public void setStock_num(int stock_num) {
        this.stock_num = stock_num;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getGoods_sell_status() {
        return goods_sell_status;
    }

    public void setGoods_sell_status(int goods_sell_status) {
        this.goods_sell_status = goods_sell_status;
    }

    public int getCreate_user() {
        return create_user;
    }

    public void setCreate_user(int create_user) {
        this.create_user = create_user;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getUpdate_user() {
        return update_user;
    }

    public void setUpdate_user(int update_user) {
        this.update_user = update_user;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public ArrayList<String> getGoods_carouselList() {
        return goods_carouselList;
    }

    public void setGoods_carouselList(ArrayList<String> goods_carouselList) {
        this.goods_carouselList = goods_carouselList;
    }
}
