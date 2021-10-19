package com.qst.qstmall.controller;

import com.qst.qstmall.domin.User;
import com.qst.qstmall.service.impl.PersonalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@Controller
@RequestMapping("/admin")
public class QstUserController {
    @Autowired
    private PersonalService personalService;


    //跳转到会员管理页面
    @RequestMapping("/users")
    public ModelAndView qst_mall_userHtml(){
        ArrayList<User> users = personalService.get_all_user();//获取所有用户

        ModelAndView modelAndView = new ModelAndView("admin/qst_mall_user");
        modelAndView.addObject("path","users");
        modelAndView.addObject("users",users);//添加所有用户集合
        return modelAndView;
    }

    //处理修改用户锁定状态请求
    @GetMapping("/users/lock")
    @ResponseBody
    public String update_lock_flag(User user){
        boolean b = personalService.update_locked_flag(user);
        if(b){//真，修改成功
            return "success";//返回修改成功信息
        }
        //假，修改失败
        return "error";//返回修改失败信息
    }

}
