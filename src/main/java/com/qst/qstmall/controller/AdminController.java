package com.qst.qstmall.controller;

import com.qst.qstmall.domin.AdminUser;
import com.qst.qstmall.service.impl.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminUserService adminUserService;

    //跳转到后台管理主页
    @RequestMapping("/index.html")
    public ModelAndView indexHtml(HttpServletRequest request) {
        //获取服务器中的session
        HttpSession session = request.getSession();
        //获取session中的管理员对象
        AdminUser adminUser = (AdminUser)session.getAttribute("adminUser");
        if(adminUser == null){//管理员未登录，跳转到管理员登录页面
            ModelAndView modelAndView = this.loginHtml();
            return modelAndView;
        }
        //管理员已登录，跳转到后台管理主页
        ModelAndView modelAndView = new ModelAndView("admin/index");
        modelAndView.addObject("path", "index");
        return modelAndView;

    }

    //跳转到管理员登录页面
    @RequestMapping("/login.html")
    public ModelAndView loginHtml() {
        ModelAndView modelAndView = new ModelAndView("admin/login");
        return modelAndView;
    }

    //跳转到管理员信息修改页面
    @GetMapping("/profile")
    public ModelAndView profileHtml(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView("admin/profile");
        modelAndView.addObject("path","profile");
        return modelAndView;

    }

    //处理修改管理员登录名称以及昵称请求
    @GetMapping("/update_user_name")
    @ResponseBody
    public String update_user_name(String login_user_name,String nick_name,HttpServletRequest request){
        HttpSession session = request.getSession();
        AdminUser adminUser = (AdminUser)session.getAttribute("adminUser");
        adminUser.setLogin_user_name(login_user_name);
        adminUser.setNick_name(nick_name);

        boolean b = adminUserService.update_user_name(adminUser);
        if(b){
            //创建一个新的AdminUser对象用于接收服务器返回的AdminUser对象
            AdminUser adminUserServer = adminUserService.login(adminUser);
            session.setAttribute("adminUser",adminUserServer);//将修改后的的用户对象写入服务器session中
            return "success";
        }
        else {
            return "error";
        }
    }

    //处理修改管理员密码请求
    @GetMapping("/update_user_password")
    @ResponseBody
    public String update_user_password(String original_password,String new_password,HttpServletRequest request){
        HttpSession session = request.getSession();
        AdminUser adminUser = (AdminUser)session.getAttribute("adminUser");
        adminUser.setLogin_password(original_password);

        //验证原密码输入是否正确
        AdminUser adminUserServer = adminUserService.login(adminUser);
        if(adminUserServer == null){//原密码错误
            return "error";
        }
        //原密码正确，进行密码修改
        adminUserServer.setLogin_password(new_password);
        boolean b = adminUserService.update_user_password(adminUserServer);
        if(b){//密码修改成功
            session.removeAttribute("adminUser");//删除session中的管理员对象，让管理员重新登录
            return "success";
        }
        else {
            return "error";
        }
    }

    //验证管理员登录信息
    @PostMapping("/login")
    @ResponseBody
    public String login(AdminUser adminUser, HttpServletRequest request, HttpServletResponse response) {
        //判断验证码是否正确
        String verification_code = request.getParameter("verification_code");//获取用户输入的验证码

        HttpSession session = request.getSession();//获取服务器中的session
        //判断session中是否有验证码
        if (session.getAttribute("checkCode") == null) {
            //验证码不存在，返回验证码已过期信息
            return "checkCode_expired";
        }
        //获取session中的验证码，并判断验证码是否正确
        String checkCode = (String) session.getAttribute("checkCode");
        //获取完session中的验证码之后，将session中的验证码销毁
        session.removeAttribute("checkCode");

        //忽略大小写进行字符串比较
        if (checkCode.equalsIgnoreCase(verification_code)) {
            //验证码正确
            //判断账号密码是否正确
            //创建一个新的AdminUser对象用于接收服务器返回的AdminUser对象
            AdminUser adminUserServer = adminUserService.login(adminUser);
            if (adminUserServer == null) {//adminUserServer对象为空，账号或密码输入错误
                return "login_error";//登录失败，返回账号或密码错误信息
            } else {//adminUserServer对象不为空，登陆成功
                session.setAttribute("adminUser", adminUserServer);//将登录成功的用户对象写入服务器session中
                return "login_success";
            }
        } else {
            //验证码错误，返回验证码错误信息
            return "checkCode_error";
        }
    }

    //退出登录
    @RequestMapping("/logout")
    public ModelAndView logout(HttpServletRequest request){
        //获取服务器中的session
        HttpSession session = request.getSession();
        //移除user对象
        session.removeAttribute("adminUser");
        ModelAndView modelAndView = new ModelAndView("admin/login");
        return modelAndView;
    }


}
