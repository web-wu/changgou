package com.tabwu.changgou.goods.service;

import com.tabwu.changgou.entity.PageResult;
import com.tabwu.changgou.pojo.Sku;

import java.util.List;

public interface SkuService {
    void addSku(Sku sku);

    void delSku(Integer id);

    void updateSku(Sku sku);

    Sku findOne(Integer id);

    PageResult<Sku> findAll(Integer currentPage,Integer pageSize);

    PageResult<Sku> findAll(Integer currentPage,Integer pageSize,Sku sku);

    List<Sku> findSkuByStatus(String status);
}
