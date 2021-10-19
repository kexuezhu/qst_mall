package com.qst.qstmall.mapper;


import com.qst.qstmall.domin.IndexConfig;
import net.sf.jsqlparser.statement.create.table.Index;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/*
    数据库中tb_qst_mall_index_config首页配置表的操控类
*/
@Mapper
@Repository
public interface IndexConfigMapper {
    //查询首页配置表
    //1-搜索框热搜 2-搜索下拉框热搜 3-(首页)热销商品 4-(首页)新品上线 5-(首页)为你推荐
    @Select("SELECT * FROM tb_qst_mall_index_config WHERE config_type = #{config_type} AND is_deleted=0 ORDER BY config_rank DESC")
    ArrayList<IndexConfig> select_indexConfig(int config_type);

    //添加首页配置信息
    @Insert("INSERT INTO tb_qst_mall_index_config(config_name,config_type,goods_id,redirect_url,config_rank,create_user) VALUES(#{config_name},#{config_type},#{goods_id},#{redirect_url},#{config_rank},#{create_user})")
    void insert_indexConfig(IndexConfig indexConfig);

    //根据首页配置id    修改首页配置信息
    @Update("UPDATE tb_qst_mall_index_config SET config_name=#{config_name},config_type=#{config_type},goods_id=#{goods_id},redirect_url=#{redirect_url},config_rank=#{config_rank},update_user=#{update_user} WHERE config_id=#{config_id}")
    int update_indexConfig(IndexConfig indexConfig);

    //根据首页配置id  修改删除标识字段
    @Update("UPDATE tb_qst_mall_index_config SET update_user=#{update_user},is_deleted=1 WHERE config_id=#{config_id}")
    int update_indexConfig_is_deleted(long config_id,int update_user);

}
