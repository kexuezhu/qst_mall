package com.qst.qstmall.domin;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.ArrayList;
import java.util.Date;

/*
    商品分类1级类 实体类
 */
public class GoodsCategoryLevelFirst {
    //2级商品集合
    private ArrayList<GoodsCategoryLevelSecond> goodsCategoryLevelSeconds;

    //分类id
    private long category_id;
    //分类级别(1-一级分类 2-二级分类 3-三级分类)
    private int category_level;
    //父分类id
    private long parent_id;
    //分类名称
    private String category_name;
    //排序值(字段越大越靠前)
    private int category_rank;
    //删除标识字段(0-未删除 1-已删除)
    private boolean is_deleted;
    //创建时间
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String create_time;
    //创建者id
    private int create_user;
    //修改时间
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String update_time;
    //修改者id
    private int update_user;

    public long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(long category_id) {
        this.category_id = category_id;
    }

    public int getCategory_level() {
        return category_level;
    }

    public void setCategory_level(int category_level) {
        this.category_level = category_level;
    }

    public long getParent_id() {
        return parent_id;
    }

    public void setParent_id(long parent_id) {
        this.parent_id = parent_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public int getCategory_rank() {
        return category_rank;
    }

    public void setCategory_rank(int category_rank) {
        this.category_rank = category_rank;
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

    public ArrayList<GoodsCategoryLevelSecond> getGoodsCategoryLevelSeconds() {
        return goodsCategoryLevelSeconds;
    }

    public void setGoodsCategoryLevelSeconds(ArrayList<GoodsCategoryLevelSecond> goodsCategoryLevelSeconds) {
        this.goodsCategoryLevelSeconds = goodsCategoryLevelSeconds;
    }
}
