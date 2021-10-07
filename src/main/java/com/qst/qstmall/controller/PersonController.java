package com.qst.qstmall.controller;

import com.github.pagehelper.PageInfo;
import com.qst.qstmall.domin.GoodsInfo;
import com.qst.qstmall.domin.Order;
import com.qst.qstmall.domin.User;
import com.qst.qstmall.service.impl.OrderService;
import com.qst.qstmall.service.impl.PersonalService;
import com.qst.qstmall.service.impl.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/user")
public class PersonController {

    @Autowired
    public PersonalService personalService;
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private OrderService orderService;

    int pageNum = 1;
    int pageSize = 3;


    //跳转到登录界面
    @RequestMapping("/login.html")
    public ModelAndView loginHtml() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("mall/login");
        return modelAndView;
    }

    //跳转到注册界面
    @RequestMapping("/register.html")
    public ModelAndView registerHtml() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("mall/register");
        return modelAndView;
    }

    //跳转到个人中心界面
    @RequestMapping("/personal.html")
    public ModelAndView personHtml(HttpServletRequest request){
        //获取服务器中的session
        HttpSession session = request.getSession();
        //获取session中的用户对象
        User user = (User)session.getAttribute("user");
        if(user == null){//用户未登录，跳转到登录界面
            ModelAndView modelAndView = this.loginHtml();
            return modelAndView;
        }
        request.setAttribute("path","personal");
        ModelAndView modelAndView = new ModelAndView("mall/personal");
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
            User userServer = personalService.register(user);
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
            User userServer = personalService.login(user);
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
                    session.setAttribute("user",userServer);//将登录成功的用户对象写入服务器session中
                    //获取当前用户最新购物车商品数量并写入session
                    shoppingCartService.session_myShopping_count(request,response);
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
        //获取服务器中的session
        HttpSession session = request.getSession();
        //移除user对象
        session.removeAttribute("user");
        ModelAndView modelAndView = new ModelAndView("mall/login");
        return modelAndView;
    }

    //修改个人信息
    @GetMapping("/updateInfo")
    public String updateInfo(User user,HttpServletRequest request, HttpServletResponse response){
        String s = personalService.updateUserInfo(user);
        if(s == "success") {//修改成功
            //获取最新用户信息
            User userInfo = personalService.getUserInfo(user.getUser_id());
            HttpSession session = request.getSession();//获取服务器中的session
            session.setAttribute("user",userInfo);//将登录成功的用户对象写入服务器session中
            //期望客户端关闭后，session也能相同
            Cookie cookie = new Cookie("JSESSIONID",session.getId());
            cookie.setMaxAge(60 * 60);  //设置cookie存活时间为1个小时
            response.addCookie(cookie); //将cookie写入浏览器

            return "200";//向客户端返回 "200"信息
        }
        //修改失败
        return null;//向客户端返回 空信息
    }

    //修改收货地址
    @GetMapping("/updateAddress")
    public String updateAddress(User user,HttpServletRequest request, HttpServletResponse response){
        User userInfo1 = personalService.getUserInfo(user.getUser_id());
        userInfo1.setAddress(user.getAddress());
        String s = personalService.updateUserInfo(userInfo1);
        if(s == "success") {//修改成功
            //获取最新用户信息
            User userInfo = personalService.getUserInfo(user.getUser_id());
            HttpSession session = request.getSession();//获取服务器中的session
            session.setAttribute("user",userInfo);//将登录成功的用户对象写入服务器session中
            //期望客户端关闭后，session也能相同
            Cookie cookie = new Cookie("JSESSIONID",session.getId());
            cookie.setMaxAge(60 * 60);  //设置cookie存活时间为1个小时
            response.addCookie(cookie); //将cookie写入浏览器

            return "200";//向客户端返回 "200"信息
        }
        //修改失败
        return null;//向客户端返回 空信息
    }

    //跳转到我的订单页面
    @GetMapping("/my-orders.html")
    public ModelAndView myOrdersHtml(HttpServletRequest request){
        //获取服务器中的session
        HttpSession session = request.getSession();
        //获取当前用户id
        User user = (User)session.getAttribute("user");
        long user_id = user.getUser_id();
        //获取当前用户的订单总数
        int myOrders_count = orderService.get_order_count(user_id);

        if(request.getParameter("pageNum") != null){
            pageNum = Integer.parseInt(request.getParameter("pageNum"));
        }
        if(request.getParameter("pageSize") != null){
            pageSize = Integer.parseInt(request.getParameter("pageSize"));
        }
        //获取当前用户的当前页订单信息
        ArrayList<Order> orders = orderService.get_order(pageNum, pageSize, user_id);
        PageInfo<Order> pageInfos = new PageInfo<>(orders);
        List<Order> list = pageInfos.getList();

        ModelAndView modelAndView = new ModelAndView("mall/my-orders");
        modelAndView.addObject("path","orders");//添加path键，声明当前为订单页
        modelAndView.addObject("orders",list);//添加订单分页信息
        modelAndView.addObject("myOrders_count",myOrders_count);//添加订单总数
        return modelAndView;
    }

    //我的订单页面分页请求
    @GetMapping("/my-orders-page")
    public void myOrdersPage(HttpServletRequest request){
        if(request.getParameter("pageNum") != null){
            pageNum = Integer.parseInt(request.getParameter("pageNum"));
        }
        if(request.getParameter("pageSize") != null){
            pageSize = Integer.parseInt(request.getParameter("pageSize"));
        }

        //获取服务器中的session
        HttpSession session = request.getSession();
        //获取当前用户id
        User user = (User)session.getAttribute("user");
        long user_id = user.getUser_id();

        //获取当前用户的当前页订单集合信息
        ArrayList<Order> orders = orderService.get_order(pageNum, pageSize, user_id);

        //将分页订单集合封装成PageInfo集合
        PageInfo<Order> pageInfos = new PageInfo<>(orders);
        List<Order> list = pageInfos.getList();

        request.setAttribute("orders",list);//添加订单分页信息
    }

}
