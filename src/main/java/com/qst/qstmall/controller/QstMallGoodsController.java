package com.qst.qstmall.controller;

import com.qst.qstmall.domin.*;
import com.qst.qstmall.service.impl.CategoryLevelService;
import com.qst.qstmall.service.impl.GoodsInfoService;
import org.apache.ibatis.annotations.Param;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@Controller
@RequestMapping("admin")
public class QstMallGoodsController {
    @Autowired
    private GoodsInfoService goodsInfoService;
    @Autowired
    private CategoryLevelService categoryLevelService;

    //跳转到商品管理页面
    @RequestMapping("/goods")
    public ModelAndView qst_mall_goodsHtml(HttpServletRequest request) {
        //获取服务器中的session
        HttpSession session = request.getSession();
        //获取session中的管理员对象
        AdminUser adminUser = (AdminUser)session.getAttribute("adminUser");
        if(adminUser == null){//管理员未登录，跳转到管理员登录页面
            ModelAndView modelAndView = new ModelAndView("admin/login");
            return modelAndView;
        }

        ArrayList<GoodsInfo> goodsInfos = goodsInfoService.get_allGoodsInfo();

        ModelAndView modelAndView = new ModelAndView("admin/qst_mall_goods");
        modelAndView.addObject("path", "qst_mall_goods");
        modelAndView.addObject("goodsInfos", goodsInfos);
        return modelAndView;
    }

    //处理跳转到添加商品页面请求
    @RequestMapping("/goods/edit")
    public ModelAndView edit_qst_mall_goods_editHtml(HttpServletRequest request) {
        //获取服务器中的session
        HttpSession session = request.getSession();
        //获取session中的管理员对象
        AdminUser adminUser = (AdminUser)session.getAttribute("adminUser");
        if(adminUser == null){//管理员未登录，跳转到管理员登录页面
            ModelAndView modelAndView = new ModelAndView("admin/login");
            return modelAndView;
        }


        ArrayList<GoodsCategoryLevelFirst> goodsCategoryLevelFirsts = categoryLevelService.levelFirst();//获取所有1级分类

        ModelAndView modelAndView = new ModelAndView("admin/qst_mall_goods_edit");
        modelAndView.addObject("path", "goods-edit");
        modelAndView.addObject("category_id_first",null);//添加1级分类id    为空
        modelAndView.addObject("category_id_second",null);//添加2级分类id   为空
        modelAndView.addObject("category_id_third",null);//添加3级分类id    为空
        modelAndView.addObject("goodsCategoryLevelFirsts",goodsCategoryLevelFirsts);//添加1级分类集合
        return modelAndView;
    }

    //处理1级下拉框请求
    @GetMapping("/goods/edit/first")
    @ResponseBody
    public ArrayList<GoodsCategoryLevelSecond> category_levelFirst(Long category_id){
        //根据1级分类id获取2级分类
        ArrayList<GoodsCategoryLevelSecond> goodsCategoryLevelSeconds = categoryLevelService.getLevelSeconds_First(category_id);

        return goodsCategoryLevelSeconds;
    }

    //处理2级下拉框请求
    @GetMapping("/goods/edit/second")
    @ResponseBody
    public ArrayList<GoodsCategoryLevelThird> category_levelSecond(Long category_id){
        //根据2级分类id获取3级分类
        ArrayList<GoodsCategoryLevelThird> goodsCategoryLevelThirds = categoryLevelService.getLevelThirds_Second(category_id);

        return goodsCategoryLevelThirds;
    }


    //处理跳转到修改商品页面请求
    @RequestMapping("/goods/edit/{goods_id}")
    public ModelAndView update_qst_mall_goods_editHtml(@PathVariable("goods_id") long goods_id) {
        GoodsInfo goodsInfo = goodsInfoService.getGoodsInfo(goods_id);//根据商品id获取对应商品信息
        long category_id = goodsInfo.getGoods_category_id();//获取关联分类id
        GoodsCategoryLevelThird category_third = categoryLevelService.get_category_third(category_id);//获取此商品所处3级分类
        GoodsCategoryLevelSecond category_second = categoryLevelService.get_category_second(category_id);//获取此商品所处2级分类
        GoodsCategoryLevelFirst category_first = categoryLevelService.get_category_first(category_second.getCategory_id());//获取此商品所处1级分类

        long category_id_first = category_first.getCategory_id();//获取1级分类id
        long category_id_second = category_second.getCategory_id();//获取2级分类id
        long category_id_third = category_third.getCategory_id();//获取级分类id

        ArrayList<GoodsCategoryLevelFirst> goodsCategoryLevelFirsts = categoryLevelService.levelFirst();//获取1级分类集合
        ArrayList<GoodsCategoryLevelSecond> goodsCategoryLevelSeconds = categoryLevelService.getLevelSeconds_First(category_id_first);//获取当前商品所在1级分类的2级分类集合
        ArrayList<GoodsCategoryLevelThird> goodsCategoryLevelThirds = categoryLevelService.getLevelThirds_Second(category_id_second);//获取当前商品所在2级分类的3级分类集合

        ModelAndView modelAndView = new ModelAndView("admin/qst_mall_goods_edit");
        modelAndView.addObject("path", "goods-edit");
        modelAndView.addObject("goodsInfo", goodsInfo);//添加商品信息
        modelAndView.addObject("category_id_first",category_id_first);//添加1级分类id
        modelAndView.addObject("category_id_second",category_id_second);//添加2级分类id
        modelAndView.addObject("category_id_third",category_id_third);//添加3级分类id
        modelAndView.addObject("goodsCategoryLevelFirsts",goodsCategoryLevelFirsts);//添加1级分类集合
        modelAndView.addObject("goodsCategoryLevelSeconds",goodsCategoryLevelSeconds);//添加2级分类集合
        modelAndView.addObject("goodsCategoryLevelThirds",goodsCategoryLevelThirds);//添加3级分类集合
        return modelAndView;
    }


    //处理 添加/修改 商品信息请求
    @PostMapping("/goods/update")
    @ResponseBody
    public String goods_update(GoodsInfo goodsInfo, MultipartFile image, HttpServletRequest request){
        HttpSession session = request.getSession();//获取服务器中的session
        AdminUser adminUser = (AdminUser)session.getAttribute("adminUser");//获取session中的管理员对象
        int admin_user_id = adminUser.getAdmin_user_id();//获取管理员id

        if(image != null){
            // 上传文件/图像到指定文件夹（这里可以改成你想存放地址的相对路径）
            File savePos = new File("src/main/resources/static/upload-file/goods-img");
            if(!savePos.exists()){  // 不存在，则创建该文件夹
                savePos.mkdir();
            }
            // 获取存放位置的规范路径
            String realPath = null;
            try {
                realPath = savePos.getCanonicalPath();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 上传该文件/图像至该文件夹下
            try {
                image.transferTo(new File(realPath+"/"+image.getOriginalFilename()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            String goods_cover_img = "/upload-file/goods-img/" + image.getOriginalFilename();//创建商品主图
            goodsInfo.setGoods_cover_img(goods_cover_img);//将商品主图添加到商品信息中

        }

        long goods_id = goodsInfo.getGoods_id();//获取请求参数中的商品id
        if(goods_id == 0){//商品id为0，进行添加商品操作
            goodsInfo.setCreate_user(admin_user_id);//添加创建者id
            goodsInfoService.add_goodsInfo(goodsInfo);
            return "add_success";//返回添加成功信息
        }
        else{//商品id不为0，进行修改商品操作
            goodsInfo.setUpdate_user(admin_user_id);//添加修改者id
            boolean b = goodsInfoService.update_goodsInfo(goodsInfo);//修改商品信息
            if(b){//真：商品信息修改成功
                return "update_success";//返回修改成功信息
            }
            //假：商品信息修改失败
            return "update_error";//返回修改失败信息
        }

    }



    //处理上架商品请求
    @GetMapping("/goods/putUp")
    @ResponseBody
    public String goods_putUp(long goods_id, HttpServletRequest request){
        int goods_sell_status = 0;//设置上架状态  0-已上架
        HttpSession session = request.getSession();//获取服务器中的session
        AdminUser adminUser = (AdminUser)session.getAttribute("adminUser");//获取session中的管理员对象
        int update_user = adminUser.getAdmin_user_id();//获取管理员id
        boolean b = goodsInfoService.update_goods_sell_status(goods_id, goods_sell_status, update_user);//上架商品
        if(b){//上架成功
            return "putUp_success";//返回上架成功信息
        }
        //上架失败
        return "putUp_error";//返回上架失败信息
    }

    //处理下架商品请求
    @GetMapping("/goods/putDown")
    @ResponseBody
    public String goods_putDown(long goods_id, HttpServletRequest request){
        int goods_sell_status = 1;//设置上架状态  1-已下架
        HttpSession session = request.getSession();//获取服务器中的session
        AdminUser adminUser = (AdminUser)session.getAttribute("adminUser");//获取session中的管理员对象
        int update_user = adminUser.getAdmin_user_id();//获取管理员id
        boolean b = goodsInfoService.update_goods_sell_status(goods_id, goods_sell_status, update_user);//下架商品
        if(b){//下架成功
            return "putDown_success";//返回下架成功信息
        }
        //下架失败
        return "putDown_error";//返回下架失败信息

    }




}
