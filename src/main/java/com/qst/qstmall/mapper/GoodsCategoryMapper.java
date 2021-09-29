package com.qst.qstmall.mapper;

import com.qst.qstmall.domin.GoodsCategoryLevelFirst;
import com.qst.qstmall.domin.GoodsCategoryLevelSecond;
import com.qst.qstmall.domin.GoodsCategoryLevelThird;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/*
    数据库中tb_qst_mall_goods_category商品分类表的操控类
 */
@Mapper
@Repository
public interface GoodsCategoryMapper {
    //根据商品分类等级查询1级分类商品所有信息
    @Select("select * from tb_qst_mall_goods_category where category_level = 1")
    ArrayList<GoodsCategoryLevelFirst> select_LevelFirsts();

    //根据商品分类等级查询1级分类商品所有信息
    @Select("select * from tb_qst_mall_goods_category where category_level = 2")
    ArrayList<GoodsCategoryLevelSecond> select_LevelSeconds();

    //根据商品分类等级查询1级分类商品所有信息
    @Select("select * from tb_qst_mall_goods_category where category_level = 3")
    ArrayList<GoodsCategoryLevelThird> select_LevelThirds();

}
