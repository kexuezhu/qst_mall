package com.qst.qstmall.controller;


import com.qst.qstmall.domin.GoodsInfo;
import com.qst.qstmall.service.impl.GoodsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Controller
public class SearchController {
    @Autowired
    private GoodsInfoService goodsInfoService;

    //通过搜索引擎跳转到商品搜索页
    @RequestMapping("/search")
    public String search(HttpServletRequest request){
        //获取请求参数中的关键词
        String keyword = request.getParameter("keyword");
        //根据关键词查询对应商品信息集合
        ArrayList<GoodsInfo> goodsInfo_goods_name = goodsInfoService.getGoodsInfo_goods_name(keyword);
        request.setAttribute("goodsInfos",goodsInfo_goods_name);
        return "mall/search";
    }

}
