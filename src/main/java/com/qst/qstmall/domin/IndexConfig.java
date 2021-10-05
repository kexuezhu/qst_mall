package com.qst.qstmall.domin;

import java.util.Date;

/*
    首页配置 实体类
 */
public class IndexConfig {
    //首页配置项主键id
    private  long config_id;
    //显示字符(配置搜索时不可为空，其他可为空)
    private String config_name;
    //1-搜索框热搜 2-搜索下拉框热搜 3-(首页)热销商品 4-(首页)新品上线 5-(首页)为你推荐'
    private int config_type;
    //商品id 默认为0
    private long goods_id;
    //点击后的跳转地址(默认不跳转)'
    private String redirect_url;
    //排序值(字段越大越靠前)'
    private int config_rank;
    //删除标识字段(0-未删除 1-已删除)'
    private boolean is_deleted;
    //创建时间
    private Date create_time;
    //创建者id
    private int create_user;
    //最新修改时间
    private Date update_time;
    //修改者id
    private int update_user;

    public long getConfig_id() {
        return config_id;
    }

    public void setConfig_id(long config_id) {
        this.config_id = config_id;
    }

    public String getConfig_name() {
        return config_name;
    }

    public void setConfig_name(String config_name) {
        this.config_name = config_name;
    }

    public int getConfig_type() {
        return config_type;
    }

    public void setConfig_type(int config_type) {
        this.config_type = config_type;
    }

    public long getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(long goods_id) {
        this.goods_id = goods_id;
    }

    public String getRedirect_url() {
        return redirect_url;
    }

    public void setRedirect_url(String redirect_url) {
        this.redirect_url = redirect_url;
    }

    public int getConfig_rank() {
        return config_rank;
    }

    public void setConfig_rank(int config_rank) {
        this.config_rank = config_rank;
    }

    public boolean isIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(boolean is_deleted) {
        this.is_deleted = is_deleted;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public int getCreate_user() {
        return create_user;
    }

    public void setCreate_user(int create_user) {
        this.create_user = create_user;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public int getUpdate_user() {
        return update_user;
    }

    public void setUpdate_user(int update_user) {
        this.update_user = update_user;
    }
}
