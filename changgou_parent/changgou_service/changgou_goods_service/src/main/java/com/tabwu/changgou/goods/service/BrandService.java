package com.tabwu.changgou.goods.service;

import com.github.pagehelper.PageInfo;
import com.tabwu.changgou.pojo.Brand;


import java.util.List;

public interface BrandService {
    List<Brand> findAll();

    Brand getBrandById(Integer id);

    void add(Brand brand);

    void delete(Integer id);

    void update(Brand brand);

    List<Brand> searchBrand(Brand brand);

    PageInfo<Brand> findPage(Integer currentPage, Integer pageSize);

    PageInfo<Brand> findPage(Brand brand,Integer currentPage, Integer pageSize);
}
