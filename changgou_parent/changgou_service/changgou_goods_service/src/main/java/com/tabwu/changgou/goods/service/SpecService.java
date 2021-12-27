package com.tabwu.changgou.goods.service;

import com.tabwu.changgou.entity.PageResult;
import com.tabwu.changgou.pojo.Category;
import com.tabwu.changgou.pojo.Para;
import com.tabwu.changgou.pojo.Spec;

import java.util.List;

public interface SpecService {
    void addSpec(Spec spec);

    void delSpec(Integer id);

    void updateSpec(Spec spec);

    Spec findById(Integer id);

    PageResult<Spec> findAll(Integer currentPage,Integer pageSize);

    PageResult<Spec> findAll(Integer currentPage,Integer pageSize,Spec spec);

    List<Spec> findByCategoryId(Integer categoryId);
}
