package com.qst.qstmall.mapper;

import com.qst.qstmall.domin.GoodsCategoryLevelThird;
import com.qst.qstmall.domin.GoodsInfo;
import com.qst.qstmall.domin.IndexConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/*
    数据库中tb_qst_mall_goods_info商品信息表的操控类
*/
@Mapper
@Repository
public interface GoodsInfoMapper {
    //根据商品id查询商品信息表
    @Select("SELECT * FROM tb_qst_mall_goods_info WHERE goods_id = #{goods_id}")
    GoodsInfo select_goodsInfo(long goods_id);

    //查询商品信息表（主页用）
    @Select("SELECT * FROM tb_qst_mall_goods_info WHERE goods_id IN(SELECT goods_id FROM tb_qst_mall_index_config WHERE config_type = #{config_type})")
    ArrayList<GoodsInfo> select_goodsInfo_index(int config_type);

    //根据关联分类id查询商品信息表（搜索页用）
    @Select("SELECT * FROM tb_qst_mall_goods_info WHERE goods_category_id = #{goods_category_id}")
    ArrayList<GoodsInfo> select_goodsInfo_goods_category_id(long goods_category_id);

    //根据关键词模糊查询商品信息表（搜索页用）
    @Select("SELECT * FROM tb_qst_mall_goods_info WHERE goods_name LIKE CONCAT('%',#{keyword},'%')")
    ArrayList<GoodsInfo> select_goodsInfo_goods_name(String keyword);

}
