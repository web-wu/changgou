package com.tabwu.changgou.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tabwu.changgou.entity.Result;
import com.tabwu.changgou.pojo.Sku;
import com.tabwu.changgou.search.entity.SkuInfo;
import com.tabwu.changgou.search.feign.SkuFeign;
import com.tabwu.changgou.search.mapper.SkuEsMapper;
import com.tabwu.changgou.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SearchServiceImpl implements SearchService {
    @Autowired
    private SkuEsMapper skuEsMapper;
    @Autowired
    private SkuFeign skuFeign;

    @Override
    public void importEs() {
        Result<List<Sku>> skuList = skuFeign.findSkuByStatus("1");
        List<SkuInfo> skuInfos = JSON.parseArray(JSON.toJSONString(skuList.getData()), SkuInfo.class);
        for (SkuInfo skuInfo : skuInfos) {
            Map<String,Object> specMap = JSON.parseObject(skuInfo.getSpec());
            skuInfo.setSpecMap(specMap);
        }

        skuEsMapper.saveAll(skuInfos);
    }
}
