package com.qst.qstmall.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        //登录注册不做拦截
        if(request.getRequestURI().equals("/user/login.html") || request.getRequestURI().equals("/user/login") || request.getRequestURI().equals("/user/register") || request.getRequestURI().equals("/user/register.html"))
        {
            return true;
        }
        //验证session是否存在
        Object user = request.getSession().getAttribute("user");
        if(user == null)
        {
            response.sendRedirect("/user/login.html");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
