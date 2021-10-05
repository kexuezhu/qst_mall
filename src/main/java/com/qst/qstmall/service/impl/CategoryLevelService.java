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

    @Override
    //重写获取商品1级分类
    public ArrayList<GoodsCategoryLevelFirst> levelFirst() {
        ArrayList<GoodsCategoryLevelFirst> goodsCategoryLevelFirsts = goodsCategoryMapper.select_LevelFirsts();
        return goodsCategoryLevelFirsts;
    }

    @Override
    //重写获取商品2级分类
    public ArrayList<GoodsCategoryLevelSecond> levelSecond() {
        ArrayList<GoodsCategoryLevelSecond> goodsCategoryLevelSeconds = goodsCategoryMapper.select_LevelSeconds();
        return goodsCategoryLevelSeconds;
    }

    @Override
    //重写获取商品3级分类
    public ArrayList<GoodsCategoryLevelThird> levelThird() {
        ArrayList<GoodsCategoryLevelThird> goodsCategoriesThirds = goodsCategoryMapper.select_LevelThirds();
        return goodsCategoriesThirds;
    }

    @Override
    //重写获取商品所有分类
    public ArrayList<GoodsCategoryLevelFirst> levelAll() {
        //1级商品集合
        ArrayList<GoodsCategoryLevelFirst> goodsCategoryLevelFirsts = this.levelFirst();
        //遍历1级商品集合
        for (GoodsCategoryLevelFirst goodsCategoryLevelFirst : goodsCategoryLevelFirsts) {
            //2级商品集合
            ArrayList<GoodsCategoryLevelSecond> goodsCategoryLevelSeconds = this.getLevelSeconds_First(goodsCategoryLevelFirst.getCategory_id());
            //遍历2级集合缓存对象
            for (GoodsCategoryLevelSecond goodsCategoryLevelSecond : goodsCategoryLevelSeconds) {
                //3级商品集合
                ArrayList<GoodsCategoryLevelThird> goodsCategoryLevelThirds = this.getLevelThirds_Second(goodsCategoryLevelSecond.getCategory_id());
                //将3级商品赋值给2级商品对应的3级商品集合
                goodsCategoryLevelSecond.setGoodsCategoryLevelThirds(goodsCategoryLevelThirds);
            }
            //将2级商品缓存对象赋值给1级商品对应的2级商品集合
            goodsCategoryLevelFirst.setGoodsCategoryLevelSeconds(goodsCategoryLevelSeconds);
        }
        return goodsCategoryLevelFirsts;
    }

    @Override
    //重写通过1级商品的分类id获取2级商品分类集合
    public ArrayList<GoodsCategoryLevelSecond> getLevelSeconds_First(long category_id) {
        //2级商品集合
        ArrayList<GoodsCategoryLevelSecond> goodsCategoryLevelSeconds = this.levelSecond();
        //2级商品集合缓存对象
        ArrayList<GoodsCategoryLevelSecond> cacheLevelSeconds = new ArrayList<>();
        //遍历2级商品集合
        for (GoodsCategoryLevelSecond goodsCategoryLevelSecond : goodsCategoryLevelSeconds) {
            if (goodsCategoryLevelSecond.getParent_id() == category_id) {//判断2级商品的父类id与1级商品的分类id是否相同
                //相同，则将此2级商品添加到2级商品集合缓存对象中
                cacheLevelSeconds.add(goodsCategoryLevelSecond);
            }
        }
        return cacheLevelSeconds;
    }

    @Override
    //重写通过2级商品分类id获取3级商品分类集合
    public ArrayList<GoodsCategoryLevelThird> getLevelThirds_Second(long category_id) {
        //3级商品集合
        ArrayList<GoodsCategoryLevelThird> goodsCategoryLevelThirds = this.levelThird();
        //3级商品集合缓存对象
        ArrayList<GoodsCategoryLevelThird> cacheLevelThirds = new ArrayList<>();
        //遍历3级商品集合
        for (GoodsCategoryLevelThird goodsCategoryLevelThird : goodsCategoryLevelThirds) {
            if (goodsCategoryLevelThird.getParent_id() == category_id) {//判断3级商品的父类id与2级商品的分类id是否相同
                //相同，则将此3级商品添加到3级商品集合缓存对象中
                cacheLevelThirds.add(goodsCategoryLevelThird);
            }
        }
        return cacheLevelThirds;
    }

    @Override
    //重写通过商品分类id获取所有3级分类
    public ArrayList<GoodsCategoryLevelThird> levelThirdAll(long category_id) {
        //3级商品集合缓存对象
        ArrayList<GoodsCategoryLevelThird> cacheLevelThirds = new ArrayList<>();
        //判断此商品所属分类等级
        int category_level = goodsCategoryMapper.select_category_id(category_id).getCategory_level();//通过商品分类id获取分类等级
        //属于1级分类
        if (category_level == 1) {
            //通过1级商品分类id获取对应2级商品分类集合
            ArrayList<GoodsCategoryLevelSecond> levelSeconds_first = this.getLevelSeconds_First(category_id);
            //遍历2级商品分类集合
            for (GoodsCategoryLevelSecond goodsCategoryLevelSecond : levelSeconds_first) {
                //通过2级商品分类id获取3级商品分类集合
                ArrayList<GoodsCategoryLevelThird> levelThirds_second = this.getLevelThirds_Second(goodsCategoryLevelSecond.getCategory_id());
                //遍历3级商品集合
                for (GoodsCategoryLevelThird goodsCategoryLevelThird : levelThirds_second) {
                    //将3级商品添加到3级商品集合缓存对象中
                    cacheLevelThirds.add(goodsCategoryLevelThird);
                }
            }
            //返回所有三级分类
            return cacheLevelThirds;
        }
        //属于2级分类
        if (category_level == 2) {
            //通过2级商品分类id获取3级商品分类集合
            ArrayList<GoodsCategoryLevelThird> levelThirds_second = this.getLevelThirds_Second(category_id);
            //遍历3级商品集合
            for (GoodsCategoryLevelThird goodsCategoryLevelThird : levelThirds_second) {
                //将3级商品添加到3级商品集合缓存对象中
                cacheLevelThirds.add(goodsCategoryLevelThird);
            }
            //返回所有三级分类
            return cacheLevelThirds;
        }
        //属于3级分类
        if (category_level == 3) {
            GoodsCategoryLevelThird goodsCategoryLevelThird = goodsCategoryMapper.select_category_id(category_id);
            //将3级商品添加到3级商品集合缓存对象中
            cacheLevelThirds.add(goodsCategoryLevelThird);
            //返回所有三级分类
            return cacheLevelThirds;
        }
        //此商品分类id不存在，返回空
        return null;
    }




}
