package com.qst.qstmall.service.impl;

import com.qst.qstmall.domin.Carousel;
import com.qst.qstmall.mapper.CarouselMapper;
import com.qst.qstmall.service.CarouselInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/*
    获取轮播图   实现类
 */
@Service
public class CarouselService implements CarouselInterface {
    @Autowired
    private CarouselMapper carouselMapper;

    //重写获取轮播图方法
    @Override
    public ArrayList<Carousel> getCarousel() {
        //获取数据库中的查询出的轮播图集合
        ArrayList<Carousel> carousels = carouselMapper.select_carousels();
        //返回此轮播图集合
        return carousels;
    }
}
