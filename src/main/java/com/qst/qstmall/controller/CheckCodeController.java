package com.qst.qstmall.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@RestController
@RequestMapping("/checkCode")
public class CheckCodeController {
    //生成验证码
    @RequestMapping("/creatCheckCode")
    public void creatCheckCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int width = 98;
        int height = 42;

        //1.创建一对象，在内存内代表一个图片（验证图片对象）
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        //2.美化图片
        //2.1 填充背景色
        Graphics graphics = image.getGraphics();    //画笔对象
        graphics.setColor(Color.PINK);  //设置画笔颜色
        graphics.fillRect(0, 0, width, height);

        //2.2 画边框
        graphics.setColor(Color.BLUE);
        graphics.drawRect(0, 0, width - 1, height - 1);


        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random ran = new Random();
        //创建一个可变长度字符串用于接收生成的验证码
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i <= 4; i++) {
            //生成随机角标
            int index = ran.nextInt(str.length());
            //获取字符
            char ch = str.charAt(index); //随机字符

            //2.3 写验证码
            graphics.drawString(ch + "", width / 5 * i, height / 2);

            //将字符添加到可变长度字符串中
            stringBuilder.append(ch);
        }
        //将接收的验证码转换成字符串形式
        String string = stringBuilder.toString();

        HttpSession session = request.getSession();//获取服务器中的session对象
        session.setAttribute("checkCode", string);//将验证码字符串添加到session中
        //期望客户端关闭后，session也能相同
        Cookie cookie = new Cookie("JSESSIONID",session.getId());
        cookie.setMaxAge(60 * 60);  //设置cookie存活时间为1个小时
        response.addCookie(cookie); //将cookie写入浏览器


        //2.4 画干扰线
        graphics.setColor(Color.GREEN);
        //随机生成坐标点
        for (int i = 0; i < 10; i++) {
            int x1 = ran.nextInt(width);
            int x2 = ran.nextInt(width);
            int y1 = ran.nextInt(height);
            int y2 = ran.nextInt(height);

            graphics.drawLine(x1, y1, x2, y2);
        }

        //3.将图片输出到页面展示
        ImageIO.write(image, "jpg", response.getOutputStream());

    }

}
