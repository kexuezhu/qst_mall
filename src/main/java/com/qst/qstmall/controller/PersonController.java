package com.qst.qstmall.controller;

import com.qst.qstmall.domin.User;
import com.qst.qstmall.service.impl.LoginService;
import com.qst.qstmall.service.impl.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@RestController
@RequestMapping("/user")
public class PersonController {

    @Autowired
    private RegisterService registerService;
    @Autowired
    private LoginService loginService;

    //跳转到登录界面
    @RequestMapping("/login.html")
    public ModelAndView loginHtml() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("mall/login.html");
        return modelAndView;
    }

    //跳转到注册界面
    @RequestMapping("/register.html")
    public ModelAndView registerHtml() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("mall/register.html");
        return modelAndView;
    }

    //验证注册信息
    @PostMapping("/register")
    public void register(User user, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //判断验证码是否正确
        String verification_code = request.getParameter("verification_code");//获取用户输入的验证码
        HttpSession session = request.getSession();//获取服务器中的session
        //判断session中是否有验证码
        if (session.getAttribute("checkCode") == null) {
            //验证码不存在，返回验证码已过期信息
            response.getWriter().write("checkCode_expired");
            return;
        }
        //获取session中的验证码，并判断验证码是否正确
        String checkCode = (String) session.getAttribute("checkCode");
        //获取完session中的验证码之后，将session中的验证码销毁
        session.removeAttribute("checkCode");

        //忽略大小写进行字符串比较
        if (checkCode.equalsIgnoreCase(verification_code)) {
            //验证码正确
            //判断用户名是否已存在，不存在即可正常注册，已存在则注册失败
            //创建一个新的User对象用于接收服务器返回的User对象
            User userServer = registerService.register(user);
            if (userServer == null) {//user对象为空，数据库中已存在一个相同的用户名，注册失败
                try {
                    response.getWriter().write("register_error");//注册失败，返回注册失败信息
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {//user不为空，账号未重名，注册成功
                try {
                    response.getWriter().write("register_success");//注册成功，返回注册成功信息
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } else {
            //验证码错误，返回验证码错误信息
            response.getWriter().write("checkCode_error");
        }
    }

    //验证登录信息
    @PostMapping("/login")
    public void login(User user, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //判断验证码是否正确
        String verification_code = request.getParameter("verification_code");//获取用户输入的验证码
        HttpSession session = request.getSession();//获取服务器中的session
        //判断session中是否有验证码
        if (session.getAttribute("checkCode") == null) {
            //验证码不存在，返回验证码已过期信息
            response.getWriter().write("checkCode_expired");
            return;
        }
        //获取session中的验证码，并判断验证码是否正确
        String checkCode = (String) session.getAttribute("checkCode");
        //获取完session中的验证码之后，将session中的验证码销毁
        session.removeAttribute("checkCode");

        //忽略大小写进行字符串比较
        if (checkCode.equalsIgnoreCase(verification_code)) {
            //验证码正确
            //判断账号密码是否正确
            //创建一个新的User对象用于接收服务器返回的User对象
            User userServer = loginService.login(user);
            if (userServer == null) {//user对象为空，用户输入的账号不存在
                try {
                    response.getWriter().write("login_error_username");//登录失败，返回账号不存在信息
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {//user不为空，账号正确，判断密码是否正确
                if (userServer.getPassword_md5() == null) {//user用户密码为空，密码错误
                    try {
                        response.getWriter().write("login_error_password");//登录失败，返回密码错误信息
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {//user用户密码不为空,密码正确
                    session.setAttribute("user",user);//将登录成功的用户对象写入服务器session中
                    //期望客户端关闭后，session也能相同
                    Cookie cookie = new Cookie("JSESSIONID",session.getId());
                    cookie.setMaxAge(60 * 60);  //设置cookie存活时间为1个小时
                    response.addCookie(cookie); //将cookie写入浏览器

                    try {
                        response.getWriter().write("login_success");//登录成功，返回登录成功信息
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        } else {
            //验证码错误，返回验证码错误信息
            try {
                response.getWriter().write("checkCode_error");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //退出登录
    @RequestMapping("/logout")
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        ModelAndView modelAndView = new ModelAndView("mall/index.html");
        return modelAndView;
    }


}
