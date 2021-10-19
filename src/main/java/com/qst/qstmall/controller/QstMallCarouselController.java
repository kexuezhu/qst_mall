package com.qst.qstmall.controller;

import com.qst.qstmall.domin.AdminUser;
import com.qst.qstmall.domin.Carousel;
import com.qst.qstmall.service.impl.AdminUserService;
import com.qst.qstmall.service.impl.CarouselService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class QstMallCarouselController {
    @Autowired
    private CarouselService carouselService;
    @Autowired
    private AdminUserService adminUserService;


    //跳转到轮播图配置页面
    @RequestMapping("/qst_mall_carousel.html")
    public ModelAndView qst_mall_carouselsHtml() {
        ArrayList<Carousel> carousels = carouselService.getCarousel();//获取轮播图集合

        ModelAndView modelAndView = new ModelAndView("admin/qst_mall_carousel");
        modelAndView.addObject("path", "qst_mall_carousel");//添加当前页面标识
        modelAndView.addObject("carousels",carousels);//添加轮播图集合
        return modelAndView;
    }

    //判断请求事件
    @PostMapping("/carousel/judge")
    public String carouselJudge(MultipartFile image){
        if(image != null){//file不为空，重定向到处理增加轮播图请求
            return "forward:/admin/carousel/add";
        }
        //file为空，重定向到处理修改轮播图请求
        return "forward:/admin/carousel/update";
    }

    //处理新增轮播图请求
    @PostMapping("/carousel/add")
    @ResponseBody
    public String carouselAdd(MultipartFile image,Carousel carousel, HttpServletRequest request){
        // 上传文件/图像到指定文件夹（这里可以改成你想存放地址的相对路径）
        File savePos = new File("src/main/resources/static/upload-file/carousel");
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

        String carousel_url = "/upload-file/carousel/"+image.getOriginalFilename();
        carousel.setCarousel_url(carousel_url);//添加轮播图

        HttpSession session = request.getSession();//获取服务器中的session
        AdminUser adminUser = (AdminUser)session.getAttribute("adminUser");//获取session中的管理员对象
        int create_user = adminUser.getAdmin_user_id();//获取管理员id
        carousel.setCreate_user(create_user);//添加创建者id

        carouselService.addCarousel(carousel);

        return "add_success";
    }

    //处理修改轮播图请求
    @PostMapping("/carousel/update")
    @ResponseBody
    public String carouselUpdate(Carousel carousel, HttpServletRequest request){
        HttpSession session = request.getSession();//获取服务器中的session
        AdminUser adminUser = (AdminUser)session.getAttribute("adminUser");//获取session中的管理员对象
        if(adminUser==null || carousel==null){
            return "error";
        }
        int update_user = adminUser.getAdmin_user_id();//获取管理员id
        carousel.setUpdate_user(update_user);//添加修改者id
        boolean bl = carouselService.updateCarousel(carousel);
        if(bl){//真，修改成功
            return "update_success";//返回修改成功信息
        }
        //假，修改失败
        return "update_error";//返回空
    }

    //处理删除轮播图请求
    @GetMapping("/carousel/delete")
    @ResponseBody
    public String carouselDelete(int carousel_id,HttpServletRequest request){
        HttpSession session = request.getSession();//获取服务器中的session
        AdminUser adminUser = (AdminUser)session.getAttribute("adminUser");//获取session中的管理员对象
        if(adminUser == null){
            return "error";
        }
        int update_user = adminUser.getAdmin_user_id();//获取管理员id

        boolean bl = carouselService.deleteCarousel(carousel_id,update_user);
        if(bl){//真，删除成功
            return "success";//返回删除成功信息
        }
        //假，删除失败
        return "error";//返回空
    }



}
