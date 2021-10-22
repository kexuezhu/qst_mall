package com.qst.qstmall.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SessionConfiguration implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AdminUserInterceptor()).addPathPatterns("/admin/**").excludePathPatterns("/**/*.js","/**/*.css","/**/*.jpg","/**/*.png","/**/*.ico","/**/*.jfjf","/**/*.jif");
        registry.addInterceptor(new UserInterceptor()).addPathPatterns("/user/**").excludePathPatterns("/**/*.js","/**/*.css","/**/*.jpg","/**/*.png","/**/*.ico","/**/*.jfjf","/**/*.jif");
    }
}
