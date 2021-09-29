package com.qst.qstmall.service.impl;

import com.qst.qstmall.domin.GoodsCategoryLevelFirst;
import com.qst.qstmall.domin.GoodsCategoryLevelSecond;
import com.qst.qstmall.domin.GoodsCategoryLevelThird;
import com.qst.qstmall.mapper.GoodsCategoryMapper;
import com.qst.qstmall.service.CategoryLevelInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


/*
    获取商品分类服务    实现类
 */
@Service
public class CategoryLevelService implements CategoryLevelInterface {
    @Autowired
    private GoodsCategoryMapper goodsCategoryMapper;

    //重写获取商品1级分类
    @Override
    public ArrayList<GoodsCategoryLevelFirst> levelFirst(){
        ArrayList<GoodsCategoryLevelFirst> goodsCategoryLevelFirsts = goodsCategoryMapper.select_LevelFirsts();
        return goodsCategoryLevelFirsts;
    }

    @Override
    //重写获取商品2级分类
    public ArrayList<GoodsCategoryLevelSecond> levelSecond(){
        ArrayList<GoodsCategoryLevelSecond> goodsCategoryLevelSeconds = goodsCategoryMapper.select_LevelSeconds();
        return goodsCategoryLevelSeconds;
    }

    @Override
    //重写获取商品3级分类
    public ArrayList<GoodsCategoryLevelThird> levelThird(){
        ArrayList<GoodsCategoryLevelThird> goodsCategoriesThirds = goodsCategoryMapper.select_LevelThirds();
        return goodsCategoriesThirds;
    }

}
