package com.qst.qstmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qst.qstmall.domin.GoodsInfo;
import com.qst.qstmall.domin.IndexConfig;
import com.qst.qstmall.mapper.GoodsInfoMapper;
import com.qst.qstmall.service.GoodsInfoInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/*
    获取商品信息服务    实现类
 */
@Service
public class GoodsInfoService implements GoodsInfoInterface {
    @Autowired
    private GoodsInfoMapper goodsInfoMapper;

    @Override
    //重写根据商品主键获取对应商品信息方法
    public GoodsInfo getGoodsInfo(long goods_id) {
        GoodsInfo goodsInfo = goodsInfoMapper.select_goodsInfo(goods_id);
        //创建轮播图集合缓存对象
        ArrayList<String> goods_carouselList = new ArrayList<>();
        //获取商品信息中的轮播图字符串，并以 "," 分隔成字符数组
        String[] split = goodsInfo.getGoods_carousel().split(",");
        //遍历字符数组
        for (String s : split) {
            //将字符串添加到缓存对象中
            goods_carouselList.add(s);
        }
        //将缓存对象赋值到商品信息对象的轮播图集合中
        goodsInfo.setGoods_carouselList(goods_carouselList);
        return goodsInfo;
    }

    @Override
    //重写根据首页配置获取对应商品信息  （不需要分页）
    public ArrayList<GoodsInfo> getGoodsInfo_index(int config_type) {
        ArrayList<GoodsInfo> goodsInfos = goodsInfoMapper.select_goodsInfo_index(config_type);
        return goodsInfos;
    }

    @Override
    //重写根据首页配置获取对应商品信息  （需要分页）分页信息
    public ArrayList<GoodsInfo> getGoodsInfo_indexPage(int config_type, int pageNum, int pageSize) {
        //使用分页插件,核心代码就这一行
        PageHelper.startPage(pageNum, pageSize);

        ArrayList<GoodsInfo> goodsInfos = goodsInfoMapper.select_goodsInfo_index(config_type);
        return goodsInfos;
    }

    @Override
    //重写根据关联分类id查询对应商品信息
    public ArrayList<GoodsInfo> getGoodsInfo_goods_category_id(long goods_category_id){
        //判断数据库中是否有对应的商品信息
        if(goodsInfoMapper.select_goodsInfo_goods_category_id(goods_category_id) != null){//返回的商品信息不为空
            return goodsInfoMapper.select_goodsInfo_goods_category_id(goods_category_id);
        }
        return null;
    }

    @Override
    //重写根据关键词模糊查询对应的商品信息
    public ArrayList<GoodsInfo> getGoodsInfo_goods_name(String keyword){
        ArrayList<GoodsInfo> goodsInfos = goodsInfoMapper.select_goodsInfo_goods_name(keyword);
        return goodsInfos;
    }


}
