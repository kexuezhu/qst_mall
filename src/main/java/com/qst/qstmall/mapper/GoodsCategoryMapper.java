package com.qst.qstmall.mapper;

import com.qst.qstmall.domin.GoodsCategoryLevelFirst;
import com.qst.qstmall.domin.GoodsCategoryLevelSecond;
import com.qst.qstmall.domin.GoodsCategoryLevelThird;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/*
    数据库中tb_qst_mall_goods_category商品分类表的操控类
 */
@Mapper
@Repository
public interface GoodsCategoryMapper {
    //根据商品分类等级查询1级分类商品所有信息
    @Select("select * from tb_qst_mall_goods_category where category_level = 1 AND is_deleted=0 ORDER BY category_rank DESC")
    ArrayList<GoodsCategoryLevelFirst> select_LevelFirsts();

    //根据商品分类等级查询2级分类商品所有信息
    @Select("select * from tb_qst_mall_goods_category where category_level = 2 AND is_deleted=0 ORDER BY category_rank DESC")
    ArrayList<GoodsCategoryLevelSecond> select_LevelSeconds();

    //根据商品分类等级查询3级分类商品所有信息
    @Select("select * from tb_qst_mall_goods_category where category_level = 3 AND is_deleted=0 ORDER BY category_rank DESC")
    ArrayList<GoodsCategoryLevelThird> select_LevelThirds();

    //根据商品分类id查询对应商品分类所有信息
    @Select("SELECT * FROM tb_qst_mall_goods_category WHERE category_id = #{category_id} AND is_deleted=0 ORDER BY category_rank DESC")
    GoodsCategoryLevelThird select_category_id(long category_id);

    //添加商品分类信息
    @Insert("INSERT INTO tb_qst_mall_goods_category(category_level,parent_id,category_name,category_rank,create_user) VALUES(#{category_level},#{parent_id},#{category_name},#{category_rank},#{create_user})")
    void insert_category(GoodsCategoryLevelThird goodsCategoryLevelThird);

    //根据分类id    修改商品分类信息
    @Update("UPDATE tb_qst_mall_goods_category SET category_name=#{category_name},category_rank=#{category_rank},update_user=#{update_user} WHERE category_id=#{category_id}")
    int update_category(GoodsCategoryLevelThird goodsCategoryLevelThird);

    //根据分类id    修改 删除标识字段
    @Update("UPDATE tb_qst_mall_goods_category SET update_user=#{update_user},is_deleted=1 WHERE category_id=#{category_id}")
    int update_category_is_deleted(long category_id,int update_user);


}
