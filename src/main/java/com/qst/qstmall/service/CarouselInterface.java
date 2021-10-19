package com.qst.qstmall.service;

import com.qst.qstmall.domin.Carousel;

import java.util.ArrayList;

/*
    获取轮播图   接口类
 */
public interface CarouselInterface {
    //声明获取轮播图方法
    ArrayList<Carousel> getCarousel();

    //声明添加轮播图方法
    void addCarousel(Carousel carousel);

    //声明修改轮播图方法
    boolean updateCarousel(Carousel carousel);

    //声明删除轮播图方法
    boolean deleteCarousel(int carousel_id,int update_user);



}
