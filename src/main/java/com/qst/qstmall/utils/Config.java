package com.qst.qstmall.utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.File;
import java.io.IOException;

@Configuration
public class Config implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //映射图片保存地址

        //重新创建一个类，这个类表示添加一个静态资源文件夹，所有上传到该路径的静态文件，都可以通过 /mall/upload-file/"+图片名 的方式来访问。

        // 上传文件/图像的指定文件夹（这里可以改成你想存放地址的相对路径）
        File saveCarousel = new File("src/main/resources/static/upload-file/carousel");
        File saveGoodsImg = new File("src/main/resources/static/upload-file/goods-img");
        String readPath1 = null;
        String readPath2 = null;
        try {
            readPath1 = saveCarousel.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            readPath2=saveGoodsImg.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String path1 = readPath1.replaceAll("\\\\","/");
        String path2 = readPath2.replaceAll("\\\\","/");

        registry.addResourceHandler("/upload-file/carousel/**").addResourceLocations("file:"+path1+"/");
        registry.addResourceHandler("/upload-file/goods-img/**").addResourceLocations("file:"+path2+"/");

    }

}
