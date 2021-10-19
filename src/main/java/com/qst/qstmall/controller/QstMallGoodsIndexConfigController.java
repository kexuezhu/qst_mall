package com.qst.qstmall.controller;

import com.qst.qstmall.domin.AdminUser;
import com.qst.qstmall.domin.IndexConfig;
import com.qst.qstmall.service.impl.IndexConfigService;
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
public class QstMallGoodsIndexConfigController {
    @Autowired
    private IndexConfigService indexConfigService;

    //跳转到首页配置页面
    @RequestMapping("/indexConfigs")
    public ModelAndView qst_mall_goodsHtml(int config_type) {//1-搜索框热搜 2-搜索下拉框热搜 3-(首页)热销商品 4-(首页)新品上线 5-(首页)为你推荐
        String path = "";
        ArrayList<IndexConfig> indexConfigs = new ArrayList<>();
        if (config_type == 1) {
            path = "INDEX_SEARCH_HOTS";
            indexConfigs = indexConfigService.getIndexConfigs(config_type);
        }
        if (config_type == 2) {
            path = "INDEX_SEARCH_DOWN_HOTS";
            indexConfigs = indexConfigService.getIndexConfigs(config_type);
        }
        if (config_type == 3) {
            path = "INDEX_GOODS_HOTS";
            indexConfigs = indexConfigService.getIndexConfigs(config_type);
        }
        if (config_type == 4) {
            path = "INDEX_GOODS_NEW";
            indexConfigs = indexConfigService.getIndexConfigs(config_type);
        }
        if (config_type == 5) {
            path = "INDEX_GOODS_RECOMMOND";
            indexConfigs = indexConfigService.getIndexConfigs(config_type);
        }

        ModelAndView modelAndView = new ModelAndView("admin/qst_mall_index_config");
        modelAndView.addObject("path", path);//添加当前页面标识信息
        modelAndView.addObject("config_type", config_type);//添加配置类型
        modelAndView.addObject("indexConfigs", indexConfigs);//添加首页配置集合
        return modelAndView;
    }

    //处理新增首页配置请求
    @PostMapping("/indexConfig/add")
    @ResponseBody
    public String carouselAdd(IndexConfig indexConfig, HttpServletRequest request) {
        HttpSession session = request.getSession();//获取服务器中的session
        AdminUser adminUser = (AdminUser) session.getAttribute("adminUser");//获取session中的管理员对象
        int create_user = adminUser.getAdmin_user_id();//获取管理员id
        indexConfig.setCreate_user(create_user);//添加创建者id

        indexConfigService.addIndexConfig(indexConfig);//添加首页配置信息

        return "add_success";
    }

    //处理修改首页配置请求
    @PostMapping("/indexConfig/update")
    @ResponseBody
    public String carouselUpdate(IndexConfig indexConfig, HttpServletRequest request) {
        HttpSession session = request.getSession();//获取服务器中的session
        AdminUser adminUser = (AdminUser) session.getAttribute("adminUser");//获取session中的管理员对象
        int update_user = adminUser.getAdmin_user_id();//获取管理员id
        indexConfig.setUpdate_user(update_user);//添加修改者id

        boolean bl = indexConfigService.updateIndexConfig(indexConfig);//修改首页配置信息
        if (bl) {//真，修改成功
            return "update_success";//返回修改成功信息
        }
        //假，修改失败
        return "update_error";//返回空
    }

    //处理删除首页配置请求
    @GetMapping("/indexConfig/delete")
    @ResponseBody
    public String carouselDelete(int config_id, HttpServletRequest request) {
        HttpSession session = request.getSession();//获取服务器中的session
        AdminUser adminUser = (AdminUser) session.getAttribute("adminUser");//获取session中的管理员对象
        if (adminUser == null) {
            return "error";
        }
        int update_user = adminUser.getAdmin_user_id();//获取管理员id

        boolean bl = indexConfigService.deleteIndexConfig(config_id, update_user);//删除首页配置信息
        if (bl) {//真，删除成功
            return "success";//返回删除成功信息
        }
        //假，删除失败
        return "error";//返回空
    }


}
