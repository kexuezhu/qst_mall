package com.qst.qstmall.mapper;

import com.qst.qstmall.domin.AdminUser;
import com.qst.qstmall.domin.Carousel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
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
    @Select("select * from tb_qst_mall_carousel where is_deleted=0 ORDER BY carousel_rank DESC")
    ArrayList<Carousel> select_carousels();

    //插入新图片
    @Insert("INSERT INTO tb_qst_mall_carousel(carousel_url,redirect_url,carousel_rank,create_user) VALUES(#{carousel_url},#{redirect_url},#{carousel_rank},#{create_user})")
    void insert_carousel(Carousel carousel);
//    void insert_carousel(String carousel_url, String redirect_url, int carousel_rank, long create_user);

    //根据轮播图id修改轮播图信息
    @Update("UPDATE tb_qst_mall_carousel SET redirect_url=#{redirect_url}, carousel_rank=#{carousel_rank}, update_user=#{update_user} WHERE carousel_id=#{carousel_id} AND is_deleted=0")
    int update_carousel(Carousel carousel);

    //根据轮播图id和管理员id 修改删除标识字段和修改者id（删除图片）
    @Update("UPDATE tb_qst_mall_carousel SET is_deleted=1,update_user=#{update_user} WHERE carousel_id=#{carousel_id} AND is_deleted=0")
    int update_carousel_is_deleted(int carousel_id,int update_user);


}
