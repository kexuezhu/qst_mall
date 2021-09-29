package com.qst.qstmall.service;

import com.qst.qstmall.domin.GoodsCategoryLevelFirst;
import com.qst.qstmall.domin.GoodsCategoryLevelSecond;
import com.qst.qstmall.domin.GoodsCategoryLevelThird;

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
}
