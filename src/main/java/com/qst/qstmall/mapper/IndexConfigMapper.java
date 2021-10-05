package com.qst.qstmall.mapper;


import com.qst.qstmall.domin.IndexConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
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
    @Select("SELECT * FROM tb_qst_mall_index_config WHERE config_type = #{config_type}")
    ArrayList<IndexConfig> select_indexConfig(int config_type);
}
