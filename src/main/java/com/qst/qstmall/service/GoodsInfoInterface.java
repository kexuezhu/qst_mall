package com.qst.qstmall.service;

import com.qst.qstmall.domin.GoodsInfo;
import com.qst.qstmall.domin.IndexConfig;

import java.util.ArrayList;

/*
    获取商品信息服务    接口类
 */
public interface GoodsInfoInterface {
    //声明根据商品主键获取对应商品信息方法
    GoodsInfo getGoodsInfo(long goods_id);

    //声明根据首页配置获取对应商品信息  （不需要分页）
    ArrayList<GoodsInfo> getGoodsInfo_index(int config_type);

    //声明根据首页配置获取对应商品信息  （需要分页）分页信息
    ArrayList<GoodsInfo> getGoodsInfo_indexPage(int config_type, int pageNum, int pageSize);

    //声明根据关联分类id查询对应商品信息
    ArrayList<GoodsInfo> getGoodsInfo_goods_category_id(long goods_category_id);

    //声明根据关键词模糊查询对应的商品信息
    ArrayList<GoodsInfo> getGoodsInfo_goods_name(String keyword);

    //声明获取所有商品信息
    ArrayList<GoodsInfo> get_allGoodsInfo();

    //声明根据商品id修改商品上架状态
    boolean update_goods_sell_status(long goods_id,int goods_sell_status,int update_user);

    //声明添加商品方法
    void add_goodsInfo(GoodsInfo goodsInfo);

    //声明根据商品id修改商品信息
    boolean update_goodsInfo(GoodsInfo goodsInfo);



}
