package com.qst.qstmall.service.impl;

import com.qst.qstmall.domin.Carousel;
import com.qst.qstmall.mapper.CarouselMapper;
import com.qst.qstmall.service.CarouselInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/*
    获取轮播图   实现类
 */
@Service
public class CarouselService implements CarouselInterface {
    @Autowired
    private CarouselMapper carouselMapper;

    @Override
    //重写获取轮播图方法
    public ArrayList<Carousel> getCarousel() {
        //获取数据库中的查询出的轮播图集合
        ArrayList<Carousel> carousels = carouselMapper.select_carousels();

        //返回此轮播图集合
        return carousels;
    }

    @Override
    //重写添加轮播图方法
    public void addCarousel(Carousel carousel){
        carouselMapper.insert_carousel(carousel);
    }

    @Override
    //重写修改轮播图方法
    public boolean updateCarousel(Carousel carousel){
        int i = carouselMapper.update_carousel(carousel);
        if(i == 0){
            //轮播图修改失败
            return false;//返回假
        }
        //轮播图修改成功
        return true;//返回真
    }


    @Override
    //重写删除轮播图方法 （真，删除成功     假，删除失败）
    public boolean deleteCarousel(int carousel_id,int update_user){
        int i = carouselMapper.update_carousel_is_deleted(carousel_id,update_user);
        if(i == 0){
            //轮播图删除失败
            return false;//返回假
        }
        //轮播图删除成功
        return true;//返回真

    }



}
