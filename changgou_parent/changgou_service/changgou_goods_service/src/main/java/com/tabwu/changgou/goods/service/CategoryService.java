package com.tabwu.changgou.goods.service;

import com.tabwu.changgou.entity.PageResult;
import com.tabwu.changgou.pojo.Category;

import java.util.List;

public interface CategoryService {
    void addCategory(Category category);

    void delCategory(Integer id);

    void updateCategory(Category category);

    Category findById(Integer id);

    List<Category> findParentId(Integer pid);

    PageResult<Category> findAll(Integer currentPage,Integer pageSize);

    PageResult<Category> findAll(Integer currentPage,Integer pageSize,Category category);
}
