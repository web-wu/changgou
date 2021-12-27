package com.tabwu.changgou.goods.service;

import com.tabwu.changgou.entity.PageResult;
import com.tabwu.changgou.pojo.Para;

import java.util.List;

public interface ParaService {
    void addPara(Para para);

    void delPara(Integer id);

    void updatePara(Para para);

    Para findById(Integer id);

    PageResult<Para> findAll(Integer currentPage,Integer pageSize);

    PageResult<Para> findAll(Integer currentPage,Integer pageSize,Para para);

    List<Para> findByCategoryId(Integer id);
}
