package com.qst.qstmall.controller;

import com.qst.qstmall.domin.*;
import com.qst.qstmall.service.impl.CategoryLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
@RequestMapping("/admin")
public class QstMallGoodsCategoryController {
    @Autowired
    private CategoryLevelService categoryLevelService;

    //跳转到商品分类管理页面
    @RequestMapping("/categories")
    public ModelAndView qst_mall_categoryHtml(String function, String category_level, String parent_id,String category_id,String back_parent_id) {
        ModelAndView modelAndView = new ModelAndView("admin/qst_mall_category");
        //判断是否是下级分类管理请求或返回请求
        if (function == null) {//请求跳转商品分类管理页面
            ArrayList<GoodsCategoryLevelFirst> goodsCategories = categoryLevelService.levelFirst();
            category_level = "1";
            parent_id = "0";
            modelAndView.addObject("goodsCategories", goodsCategories);//添加1级商品分类
        }
        else if (function.equals("manage")) {//请求下级分类管理
            long categoryId = 0;
            if (category_id != null){
                categoryId = Long.parseLong(category_id);
            }
            if (category_level.equals("1")){//1级分类页发出的请求
                //查询对应2级分类
                ArrayList<GoodsCategoryLevelSecond> goodsCategories = categoryLevelService.getLevelSeconds_First(categoryId);
                modelAndView.addObject("goodsCategories", goodsCategories);//添加2级商品分类
                category_level = "2";
                parent_id = category_id;
            }
            else if (category_level.equals("2")){//2级分类页发出的请求
                //查询对应2级分类
                ArrayList<GoodsCategoryLevelThird> goodsCategories = categoryLevelService.getLevelThirds_Second(categoryId);
                modelAndView.addObject("goodsCategories", goodsCategories);//添加3级商品分类
                category_level = "3";
                back_parent_id = parent_id;
                parent_id = category_id;
            }

        }
        else if (function.equals("back")) {//请求返回分类管理
            if(category_level.equals("2")){//2级分类页发出的请求
                //查询1级分类
                ArrayList<GoodsCategoryLevelFirst> goodsCategories = categoryLevelService.levelFirst();
                modelAndView.addObject("goodsCategories", goodsCategories);//添加1级商品分类
                category_level = "1";
                parent_id = "0";
            }
            else if(category_level.equals("3")){//3级分类页发出的请求
                long backParentId = Long.parseLong(back_parent_id);
                //查询对应2级分类
                ArrayList<GoodsCategoryLevelSecond> goodsCategories = categoryLevelService.getLevelSeconds_First(backParentId);
                modelAndView.addObject("goodsCategories", goodsCategories);//添加2级商品分类
                category_level = "2";
                parent_id = back_parent_id;
            }
        }


        modelAndView.addObject("path", "qst_mall_category");//添加当前页面标识信息
        modelAndView.addObject("category_level", category_level);//添加当前商品分类等级
        modelAndView.addObject("parent_id", parent_id);//添加当前商品分类的父分类id
        modelAndView.addObject("back_parent_id", back_parent_id);//添加用于返回的分类id

        return modelAndView;
    }

    //处理新增商品分类请求
    @GetMapping("/categories/add")
    @ResponseBody
    public String carouselAdd(GoodsCategoryLevelThird goodsCategoryLevelThird, HttpServletRequest request) {
        HttpSession session = request.getSession();//获取服务器中的session
        AdminUser adminUser = (AdminUser) session.getAttribute("adminUser");//获取session中的管理员对象
        int create_user = adminUser.getAdmin_user_id();//获取管理员id
        goodsCategoryLevelThird.setCreate_user(create_user);//添加创建者id

        categoryLevelService.addGoodsCategory(goodsCategoryLevelThird);//添加商品分类信息

        return "add_success";
    }

    //处理修改商品分类请求
    @GetMapping("/categories/update")
    @ResponseBody
    public String carouselUpdate(GoodsCategoryLevelThird goodsCategoryLevelThird, HttpServletRequest request) {
        HttpSession session = request.getSession();//获取服务器中的session
        AdminUser adminUser = (AdminUser) session.getAttribute("adminUser");//获取session中的管理员对象
        int update_user = adminUser.getAdmin_user_id();//获取管理员id
        goodsCategoryLevelThird.setUpdate_user(update_user);//添加修改者id

        boolean bl = categoryLevelService.updateGoodsCategory(goodsCategoryLevelThird);//修改商品分类信息
        if (bl) {//真，修改成功
            return "update_success";//返回修改成功信息
        }
        //假，修改失败
        return "update_error";//返回空
    }

    //处理删除商品分类请求
    @GetMapping("/categories/delete")
    @ResponseBody
    public String carouselDelete(int category_id, HttpServletRequest request) {
        HttpSession session = request.getSession();//获取服务器中的session
        AdminUser adminUser = (AdminUser) session.getAttribute("adminUser");//获取session中的管理员对象
        if (adminUser == null) {
            return "error";
        }
        int update_user = adminUser.getAdmin_user_id();//获取管理员id

        boolean bl = categoryLevelService.deleteGoodsCategory(category_id, update_user);//删除商品分类信息
        if (bl) {//真，删除成功
            return "success";//返回删除成功信息
        }
        //假，删除失败
        return "error";//返回空
    }



}
