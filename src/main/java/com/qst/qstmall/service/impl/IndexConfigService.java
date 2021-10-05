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
}
