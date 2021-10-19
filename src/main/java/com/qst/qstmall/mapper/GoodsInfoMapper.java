package com.qst.qstmall.mapper;

import com.qst.qstmall.domin.GoodsCategoryLevelThird;
import com.qst.qstmall.domin.GoodsInfo;
import com.qst.qstmall.domin.IndexConfig;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
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
    @Select("SELECT * FROM tb_qst_mall_goods_info WHERE goods_sell_status=0 AND goods_id = #{goods_id}")
    GoodsInfo select_goodsInfo(long goods_id);

    //查询商品信息表（主页用）
    @Select("SELECT * FROM tb_qst_mall_goods_info WHERE goods_sell_status=0 AND goods_id IN(SELECT goods_id FROM tb_qst_mall_index_config WHERE config_type = #{config_type})")
    ArrayList<GoodsInfo> select_goodsInfo_index(int config_type);

    //根据关联分类id查询商品信息表（搜索页用）
    @Select("SELECT * FROM tb_qst_mall_goods_info WHERE goods_sell_status=0 AND goods_category_id = #{goods_category_id}")
    ArrayList<GoodsInfo> select_goodsInfo_goods_category_id(long goods_category_id);

    //根据关键词模糊查询商品信息表（搜索页用）
    @Select("SELECT * FROM tb_qst_mall_goods_info WHERE goods_sell_status=0 AND goods_name LIKE CONCAT('%',#{keyword},'%')")
    ArrayList<GoodsInfo> select_goodsInfo_goods_name(String keyword);

    //查询所有商品信息
    @Select("SELECT * FROM tb_qst_mall_goods_info")
    ArrayList<GoodsInfo> select_all_goodsInfo();

    //根据商品id修改商品上架状态
    @Update("UPDATE tb_qst_mall_goods_info SET goods_sell_status=#{goods_sell_status},update_user=#{update_user} WHERE goods_id=#{goods_id}")
    int update_goods_sell_status(long goods_id,int goods_sell_status,int update_user);

    //添加商品
    @Insert("INSERT INTO tb_qst_mall_goods_info(goods_name,goods_intro,goods_category_id,goods_cover_img,goods_detail_content,original_price,stock_num,tag,goods_sell_status,create_user) VALUES(#{goods_name},#{goods_intro},#{goods_category_id},#{goods_cover_img},#{goods_detail_content},#{original_price},#{stock_num},#{tag},#{goods_sell_status},#{create_user})")
    void insert_goodsInfo(GoodsInfo goodsInfo);

    //根据商品id修改商品信息
    @Update("<script>"
            +"UPDATE tb_qst_mall_goods_info SET goods_name=#{goods_name},goods_intro=#{goods_intro},goods_category_id=#{goods_category_id},goods_detail_content=#{goods_detail_content},original_price=#{original_price},stock_num=#{stock_num},tag=#{tag},goods_sell_status=#{goods_sell_status},update_user=#{update_user}"
            +"<if test='goods_cover_img != null'>"
            +",goods_cover_img=#{goods_cover_img}"
            +"</if>"
            +" WHERE goods_id=#{goods_id}"
            +"</script>")
    int update_goodsInfo(GoodsInfo goodsInfo);

}
