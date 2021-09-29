package com.qst.qstmall.controller;

import com.qst.qstmall.domin.Carousel;
import com.qst.qstmall.domin.GoodsCategoryLevelFirst;
import com.qst.qstmall.domin.GoodsCategoryLevelSecond;
import com.qst.qstmall.domin.GoodsCategoryLevelThird;
import com.qst.qstmall.service.impl.CarouselService;
import com.qst.qstmall.service.impl.CategoryLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

/*
    商品主页
 */
@RestController
public class MallIndex {
    //注入获取商品分类服务对象
    @Autowired
    private CategoryLevelService categoryLevelService;
    //注入获取轮播图服务对象
    @Autowired
    private CarouselService carouselService;

    //跳转到商城主页
    @RequestMapping("/index.html")
    public ModelAndView mallIndexHtml() {
        //商品分类集合
        //1级商品集合
        ArrayList<GoodsCategoryLevelFirst> goodsCategoryLevelFirsts = categoryLevelService.levelFirst();
        //2级商品集合
        ArrayList<GoodsCategoryLevelSecond> goodsCategoryLevelSeconds = categoryLevelService.levelSecond();
        //3级商品集合
        ArrayList<GoodsCategoryLevelThird> goodsCategoryLevelThirds = categoryLevelService.levelThird();
        //遍历1级商品集合
        for (GoodsCategoryLevelFirst goodsCategoryLevelFirst : goodsCategoryLevelFirsts) {
            //2级商品集合缓存对象
            ArrayList<GoodsCategoryLevelSecond> cacheLevelSeconds = new ArrayList<>();
            //遍历2级商品集合
            for (GoodsCategoryLevelSecond goodsCategoryLevelSecond : goodsCategoryLevelSeconds) {
                //3级商品集合缓存对象
                ArrayList<GoodsCategoryLevelThird> cacheLevelThirds = new ArrayList<>();
                //遍历3级商品集合
                for (GoodsCategoryLevelThird goodsCategoryLevelThird : goodsCategoryLevelThirds) {
                    if (goodsCategoryLevelThird.getParent_id() == goodsCategoryLevelSecond.getCategory_id()) {//判断3级商品的父类id与2级商品的分类id是否相同
                        //相同，则将此3级商品添加到3级商品集合缓存对象中
                        cacheLevelThirds.add(goodsCategoryLevelThird);
                    }
                }
                //将3级商品缓存对象赋值给2级商品对应的3级商品集合
                goodsCategoryLevelSecond.setGoodsCategoryLevelThirds(cacheLevelThirds);

                if (goodsCategoryLevelSecond.getParent_id() == goodsCategoryLevelFirst.getCategory_id()) {//判断2级商品的父类id与1级商品的分类id是否相同
                    //相同，则将此2级商品添加到2级商品集合缓存对象中
                    cacheLevelSeconds.add(goodsCategoryLevelSecond);
                }
            }
            //将2级商品缓存对象赋值给1级商品对应的2级商品集合
            goodsCategoryLevelFirst.setGoodsCategoryLevelSeconds(cacheLevelSeconds);
        }

        //轮播图集合
        ArrayList<Carousel> carousels = carouselService.getCarousel();

        //展示商城主页视图
        ModelAndView modelAndView = new ModelAndView("mall/index.html");
        modelAndView.addObject("categories", goodsCategoryLevelFirsts);//添加商品分类信息
        modelAndView.addObject("carousels",carousels);//添加轮播图信息
        return modelAndView;
    }
    //测试
//    @RequestMapping("/categoryDemo")
//    public void categoryDemo(){
//        List<GoodsCategory> goodsCategories = categoryLevelFirstService.goodsCategoryLevel();
//        System.out.println("返回页面的一级目录");
//        for (GoodsCategory goodsCategory : goodsCategories) {
//            System.out.println(goodsCategory);
//        }
//    }

}
