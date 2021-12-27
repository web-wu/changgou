package com.tabwu.changgou.goods.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tabwu.changgou.entity.PageResult;
import com.tabwu.changgou.goods.mapper.CategoryMapper;
import com.tabwu.changgou.goods.mapper.TemplateMapper;
import com.tabwu.changgou.goods.service.TemplateService;
import com.tabwu.changgou.pojo.Category;
import com.tabwu.changgou.pojo.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
@Transactional
public class TemplateServiceImpl implements TemplateService {

    @Autowired
    private TemplateMapper templateMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void addTemplate(Template template) {
        templateMapper.insertSelective(template);
    }

    @Override
    public void delTemplate(Integer id) {
        templateMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updateTemplate(Template template) {
        templateMapper.updateByPrimaryKeySelective(template);
    }

    @Override
    public Template findOne(Integer id) {
        return templateMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageResult<Template> findAll(Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage,pageSize);
        Page<Template> page = (Page<Template>) templateMapper.selectAll();
        return new PageResult<>(page.getTotal(),page.getResult());
    }

    @Override
    public PageResult<Template> findAll(Integer currentPage, Integer pageSize, Template template) {
        PageHelper.startPage(currentPage,pageSize);
        Example example = createExample(template);
        Page<Template> page = (Page<Template>) templateMapper.selectByExample(example);
        return new PageResult<>(page.getTotal(),page.getResult());
    }

    @Override
    public Template findByCategoryId(Integer id) {
        Category category = categoryMapper.selectByPrimaryKey(id);
        return templateMapper.selectByPrimaryKey(category.getTemplateId());
    }


    public Example createExample(Template template) {
        Example example = new Example(Template.class);
        Example.Criteria criteria = example.createCriteria();
        if (template != null) {
            if (!StringUtils.isEmpty(template.getId())) {
                criteria.andEqualTo("id",template.getId());
            }
            if (!StringUtils.isEmpty(template.getName())) {
                criteria.andLike("name", "%" + template.getName() + "%");
            }
            if (!StringUtils.isEmpty(template.getParaNum())) {
                criteria.andEqualTo("paraNum",template.getParaNum());
            }
            if (!StringUtils.isEmpty(template.getSpecNum())) {
                criteria.andEqualTo("specNum",template.getSpecNum());
            }
        }
        return example;
    }
}
