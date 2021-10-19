package com.qst.qstmall.domin;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/*
    轮播图 实体类
 */
public class Carousel {
    //首页轮播图主键id
    private Integer carousel_id;
    //轮播图
    private String carousel_url;
    //点击后的跳转地址(默认不跳转)
    private String redirect_url;
    //排序值(字段越大越靠前)
    private int carousel_rank;
    //删除标识字段(0-未删除 1-已删除)
    private boolean is_deleted;
    //创建时间
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private String create_time;
    //创建者id
    private int create_user;
    //修改时间
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private String update_time;
    //修改者id
    private int update_user;

    @Override
    public String toString() {
        return "Carousel{" +
                "carousel_id=" + carousel_id +
                ", carousel_url='" + carousel_url + '\'' +
                ", redirect_url='" + redirect_url + '\'' +
                ", carousel_rank=" + carousel_rank +
                ", is_deleted=" + is_deleted +
                ", create_time='" + create_time + '\'' +
                ", create_user=" + create_user +
                ", update_time='" + update_time + '\'' +
                ", update_user=" + update_user +
                '}';
    }

    public Integer getCarousel_id() {
        return carousel_id;
    }

    public void setCarousel_id(Integer carousel_id) {
        this.carousel_id = carousel_id;
    }

    public String getCarousel_url() {
        return carousel_url;
    }

    public void setCarousel_url(String carousel_url) {
        this.carousel_url = carousel_url;
    }

    public String getRedirect_url() {
        return redirect_url;
    }

    public void setRedirect_url(String redirect_url) {
        this.redirect_url = redirect_url;
    }

    public int getCarousel_rank() {
        return carousel_rank;
    }

    public void setCarousel_rank(int carousel_rank) {
        this.carousel_rank = carousel_rank;
    }

    public boolean isIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(boolean is_deleted) {
        this.is_deleted = is_deleted;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getCreate_user() {
        return create_user;
    }

    public void setCreate_user(int create_user) {
        this.create_user = create_user;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public int getUpdate_user() {
        return update_user;
    }

    public void setUpdate_user(int update_user) {
        this.update_user = update_user;
    }
}
