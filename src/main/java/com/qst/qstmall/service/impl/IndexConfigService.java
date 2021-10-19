package com.qst.qstmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.qst.qstmall.domin.IndexConfig;
import com.qst.qstmall.mapper.IndexConfigMapper;
import com.qst.qstmall.service.IndexConfigInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/*
    获取首页配置服务  实现类
 */
@Service
public class IndexConfigService implements IndexConfigInterface {
    @Autowired
    private IndexConfigMapper indexConfigMapper;

    @Override
    //重写获取首页配置方法    config_type=  3-(首页)热销商品  4-(首页)新品上线  5-(首页)为你推荐
    public ArrayList<IndexConfig> getIndexConfigs(int config_type) {
        ArrayList<IndexConfig> indexConfigs = indexConfigMapper.select_indexConfig(config_type);
        return indexConfigs;
    }

    @Override
    //重写添加首页配置信息方法
    public void addIndexConfig(IndexConfig indexConfig){
        indexConfigMapper.insert_indexConfig(indexConfig);
    }

    @Override
    //声明修改首页配置信息方法
    public boolean updateIndexConfig(IndexConfig indexConfig){
        int i = indexConfigMapper.update_indexConfig(indexConfig);
        if(i == 0){//修改失败
            return false;//返回假
        }
        //修改成功
        return true;//返回真
    }

    @Override
    //声明删除首页配置信息方法
    public boolean deleteIndexConfig(long config_id,int update_user){
        int i = indexConfigMapper.update_indexConfig_is_deleted(config_id,update_user);
        if(i == 0){//修改失败
            return false;//返回假
        }
        //修改成功
        return true;//返回真
    }

}
