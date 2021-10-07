package com.qst.qstmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qst.qstmall.domin.GoodsCategoryLevelThird;
import com.qst.qstmall.domin.GoodsInfo;
import com.qst.qstmall.service.impl.CategoryLevelService;
import com.qst.qstmall.service.impl.GoodsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@RestController
public class GoodsController {
    @Autowired
    private GoodsInfoService goodsInfoService;
    @Autowired
    private CategoryLevelService categoryLevelService;

    //跳转到商品详情页
    @GetMapping("/goods/detail")
    public ModelAndView detailHtml(HttpServletRequest request) {
        //获取请求参数中的goods_id
        if (request.getParameter("goods_id") == null) {//如果请求参数goods_id值为空
            //返回页面404错误
            ModelAndView modelAndView = new ModelAndView("error/error_404");
            return modelAndView;
        }
        long goods_id = Long.valueOf(request.getParameter("goods_id"));
        //根据请求的商品id，返回对应的商品信息对象
        GoodsInfo goodsInfo = goodsInfoService.getGoodsInfo(goods_id);

        ModelAndView modelAndView = new ModelAndView("mall/detail");
        modelAndView.addObject("goodsInfo",goodsInfo);//添加商品信息对象
        return modelAndView;
    }

    //商品三级分类跳转到商品搜索页
    @GetMapping("/search.html")
    public ModelAndView searchHtml(HttpServletRequest request) {
        //商品信息集合缓存对象
        ArrayList<GoodsInfo> goodsInfos = new ArrayList<>();
        //获取请求参数中的商品分类id
        long category_id = Long.valueOf(request.getParameter("goodsCategoryId"));

        //通过商品分类id获取对应3级商品分类集合
        ArrayList<GoodsCategoryLevelThird> goodsCategoryLevelThirds = categoryLevelService.levelThirdAll(category_id);
        //遍历获取的3级商品分类集合
        for (GoodsCategoryLevelThird goodsCategoryLevelThird : goodsCategoryLevelThirds) {
            //获取对应的商品分类id
            long goods_category_id = goodsCategoryLevelThird.getCategory_id();
            //判断对应的商品是否存在
            //通过分类关联id获取商品信息集合
            ArrayList<GoodsInfo> goodsInfo_goods_category_id = goodsInfoService.getGoodsInfo_goods_category_id(goods_category_id);

            //遍历获取的商品信息集合
            for (GoodsInfo goodsInfo : goodsInfo_goods_category_id) {
                //将对应的商品信息添加到商品信息集合缓存对象中
                goodsInfos.add(goodsInfo);
            }
        }

        ModelAndView modelAndView = new ModelAndView("mall/search");
        modelAndView.addObject("goodsInfos",goodsInfos);//添加商品信息集合
        return modelAndView;
    }

    //通过搜索引擎跳转到商品搜索页
    @RequestMapping("/search")
    public ModelAndView search(HttpServletRequest request){
        //获取请求参数中的关键词
        String keyword = request.getParameter("keyword");
        //根据关键词查询对应商品信息集合
        ArrayList<GoodsInfo> goodsInfo_goods_name = goodsInfoService.getGoodsInfo_goods_name(keyword);

        ModelAndView modelAndView = new ModelAndView("mall/search");
        modelAndView.addObject("goodsInfos",goodsInfo_goods_name);
        return modelAndView;
    }


}
