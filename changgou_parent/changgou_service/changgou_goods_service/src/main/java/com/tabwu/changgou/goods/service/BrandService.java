package com.tabwu.changgou.goods.service;

import com.tabwu.changgou.entity.PageResult;
import com.tabwu.changgou.pojo.Brand;


import java.util.List;

public interface BrandService {

    Brand getBrandById(Integer id);

    void add(Brand brand);

    void delete(Integer id);

    void update(Brand brand);

    PageResult<Brand> findPage(Integer currentPage, Integer pageSize);

    PageResult<Brand> findPage(Brand brand,Integer currentPage, Integer pageSize);

    List<Brand> findByCategoryId(Integer id);
}
