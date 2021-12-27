package com.tabwu.changgou.goods.service;

import com.tabwu.changgou.entity.PageResult;
import com.tabwu.changgou.pojo.Template;

public interface TemplateService {
    void addTemplate(Template template);

    void delTemplate(Integer id);

    void updateTemplate(Template template);

    Template findOne(Integer id);

    PageResult<Template> findAll(Integer currentPage, Integer pageSize);

    PageResult<Template> findAll(Integer currentPage, Integer pageSize, Template template);

    Template findByCategoryId(Integer id);
}
