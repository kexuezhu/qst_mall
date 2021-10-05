package com.qst.qstmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qst.qstmall.domin.*;
import com.qst.qstmall.service.impl.CarouselService;
import com.qst.qstmall.service.impl.CategoryLevelService;
import com.qst.qstmall.service.impl.GoodsInfoService;
import com.qst.qstmall.service.impl.IndexConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/*
    商品主页
 */
@RestController
public class IndexController {
    //注入获取商品分类服务对象
    @Autowired
    private CategoryLevelService categoryLevelService;
    //注入获取轮播图服务对象
    @Autowired
    private CarouselService carouselService;
    //注入获取首页配置服务对象
    @Autowired
    private IndexConfigService indexConfigService;
    //注入获取商品信息服务对象
    @Autowired
    private GoodsInfoService goodsInfoService;

    private int pageNum = 1;
    private int pageSize = 5;


    //跳转到商城主页
    @RequestMapping("/index.html")
    public ModelAndView mallIndexHtml(HttpServletRequest request, HttpServletResponse response) {
        //商品分类集合
        ArrayList<GoodsCategoryLevelFirst> goodsCategoryLevelFirsts = categoryLevelService.levelAll();

        //轮播图集合
        ArrayList<Carousel> carousels = carouselService.getCarousel();

        //热销商品商品信息集合    config_type=3（热销商品）
        ArrayList<GoodsInfo> hot_goodsInfos = goodsInfoService.getGoodsInfo_index(3);
        //新品上新商品信息集合    config_type=4（新品上新）
        ArrayList<GoodsInfo> new_goodsInfos = goodsInfoService.getGoodsInfo_index(4);

        ArrayList<GoodsInfo> goodsInfo_index = goodsInfoService.getGoodsInfo_index(5);
//        int count = goodsInfo_index.size();
//        try {
//            response.getWriter().write(count);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        //为你推荐商品信息集合    config_type=5（为你推荐）
        if(request.getParameter("pageNum") != null){
            pageNum = Integer.parseInt(request.getParameter("pageNum"));
        }
        if(request.getParameter("pageSize") != null){
            pageSize = Integer.parseInt(request.getParameter("pageSize"));
        }
        //为你推荐商品信息集合    config_type=5（为你推荐）
        ArrayList<GoodsInfo> recommend_goodsInfos = goodsInfoService.getGoodsInfo_indexPage(5,pageNum,pageSize);

        PageInfo<GoodsInfo> GoodsInfos = new PageInfo<>(recommend_goodsInfos);
        List<GoodsInfo> list = GoodsInfos.getList();

        //期望客户端关闭后，session也能相同
        HttpSession session = request.getSession();
        Cookie cookie = new Cookie("JSESSIONID",session.getId());
        cookie.setMaxAge(60 * 60);  //设置cookie存活时间为1个小时
        response.addCookie(cookie); //将cookie写入浏览器


        //展示商城主页视图
        ModelAndView modelAndView = new ModelAndView("mall/index.html");
        modelAndView.addObject("categories", goodsCategoryLevelFirsts);//添加商品分类信息
        modelAndView.addObject("carousels",carousels);//添加轮播图信息
        modelAndView.addObject("hot_goodsInfos",hot_goodsInfos);//添加热销商品信息
        modelAndView.addObject("new_goodsInfos",new_goodsInfos);//添加新品上新信息
        modelAndView.addObject("recommend_goodsInfos",list);//添加为你推荐信息
        return modelAndView;
    }

    @RequestMapping("/index_recommend")
    public void index_recommend(HttpServletRequest request, HttpServletResponse response){
        //为你推荐商品信息集合    config_type=5（为你推荐）
        if(request.getParameter("pageNum") != null){
            pageNum = Integer.parseInt(request.getParameter("pageNum"));
        }
        if(request.getParameter("pageSize") != null){
            pageSize = Integer.parseInt(request.getParameter("pageSize"));
        }
        ArrayList<GoodsInfo> goodsInfo_index = goodsInfoService.getGoodsInfo_index(5);
        int count = goodsInfo_index.size();
        String count_str = "" + count;
        try {
            response.getWriter().write(count_str);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //为你推荐商品信息集合    config_type=5（为你推荐）
        ArrayList<GoodsInfo> recommend_goodsInfos = goodsInfoService.getGoodsInfo_indexPage(5,pageNum,pageSize);

        //将分页商品信息集合封装成PageInfo集合
        PageInfo<GoodsInfo> GoodsInfos = new PageInfo<>(recommend_goodsInfos);
        List<GoodsInfo> list = GoodsInfos.getList();

        request.setAttribute("recommend_goodsInfos",list);//添加为你推荐信息
        request.setAttribute("count",count_str);
    }


}
