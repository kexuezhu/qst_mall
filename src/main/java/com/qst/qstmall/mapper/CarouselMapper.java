package com.qst.qstmall.mapper;

import com.qst.qstmall.domin.Carousel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/*
    数据库中tb_qst_mall_carousel轮播图表的操控类
 */
@Mapper
@Repository
public interface CarouselMapper {
    //查询未删除的图片
    //返回轮播图对象所有信息
    @Select("select * from tb_qst_mall_carousel where is_deleted=0")
    ArrayList<Carousel> select_carousels();
}
