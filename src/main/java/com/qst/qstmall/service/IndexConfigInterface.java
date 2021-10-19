package com.qst.qstmall.service;

import com.qst.qstmall.domin.IndexConfig;

import java.util.ArrayList;

/*
    获取首页配置服务    接口类
 */
public interface IndexConfigInterface {
    //声明获取首页配置方法    config_type=  3-(首页)热销商品  4-(首页)新品上线  5-(首页)为你推荐
    ArrayList<IndexConfig> getIndexConfigs(int config_type);

    //声明添加首页配置信息方法
    void addIndexConfig(IndexConfig indexConfig);

    //声明修改首页配置信息方法
    boolean updateIndexConfig(IndexConfig indexConfig);

    //声明删除首页配置信息方法
    boolean deleteIndexConfig(long config_id,int update_user);

}
