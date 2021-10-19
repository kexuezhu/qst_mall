package com.qst.qstmall.service;

import com.qst.qstmall.domin.GoodsCategoryLevelFirst;
import com.qst.qstmall.domin.GoodsCategoryLevelSecond;
import com.qst.qstmall.domin.GoodsCategoryLevelThird;

import java.util.ArrayList;
import java.util.List;

/*
    获取商品分类服务    接口类
 */
public interface CategoryLevelInterface {
    //声明获取商品1级分类
    List<GoodsCategoryLevelFirst> levelFirst();

    //声明获取商品2级分类
    List<GoodsCategoryLevelSecond> levelSecond();

    //声明获取商品3级分类
    List<GoodsCategoryLevelThird> levelThird();

    //声明获取商品所有分类
    ArrayList<GoodsCategoryLevelFirst> levelAll();

    //声明通过1级商品的分类id获取2级商品分类集合
    ArrayList<GoodsCategoryLevelSecond> getLevelSeconds_First(long category_id);

    //声明通过2级商品分类id获取3级商品分类集合
    ArrayList<GoodsCategoryLevelThird> getLevelThirds_Second(long category_id);

    //声明通过商品分类id获取所有3级分类
    ArrayList<GoodsCategoryLevelThird> levelThirdAll(long category_id);

    //声明添加商品分类信息方法
    void addGoodsCategory(GoodsCategoryLevelThird goodsCategoryLevelThird);

    //声明修改商品分类信息方法
    boolean updateGoodsCategory(GoodsCategoryLevelThird goodsCategoryLevelThird);

    //声明删除商品分类信息方法
    boolean deleteGoodsCategory(long category_id,int update_user);

    //声明根据3级商品的分类id获取商品分类信息
    GoodsCategoryLevelThird get_category_third(long category_id);

    //声明根据3级商品的分类id获取2级分类信息
    GoodsCategoryLevelSecond get_category_second(long category_id);

    //声明根据2级商品分类id获取1级商品分类信息
    GoodsCategoryLevelFirst get_category_first(long category_id);


}
