package com.tabwu.changgou.goods.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tabwu.changgou.entity.PageResult;
import com.tabwu.changgou.goods.mapper.CategoryMapper;
import com.tabwu.changgou.goods.service.CategoryService;
import com.tabwu.changgou.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void addCategory(Category category) {
        categoryMapper.insertSelective(category);
    }

    @Override
    public void delCategory(Integer id) {
        categoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updateCategory(Category category) {
        categoryMapper.updateByPrimaryKeySelective(category);
    }

    @Override
    public Category findById(Integer id) {
        return categoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Category> findParentId(Integer pid) {
        Category category = new Category();
        category.setParentId(pid);
        return categoryMapper.select(category);
    }

    @Override
    public PageResult<Category> findAll(Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage,pageSize);
        Page<Category> page = (Page<Category>) categoryMapper.selectAll();
        return new PageResult<>(page.getTotal(),page.getResult());
    }

    @Override
    public PageResult<Category> findAll(Integer currentPage, Integer pageSize, Category category) {
        PageHelper.startPage(currentPage,pageSize);
        Example example = createExample(category);
        Page<Category> page = (Page<Category>) categoryMapper.selectByExample(example);
        return new PageResult<>(page.getTotal(),page.getResult());
    }


    public Example createExample(Category category) {
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        if (category != null) {
            if (!StringUtils.isEmpty(category.getId())) {
                criteria.andEqualTo("id",category.getId());
            }
            if (!StringUtils.isEmpty(category.getName())) {
                criteria.andLike("name","%" + category.getName() + "%");
            }
            if (!StringUtils.isEmpty(category.getGoodsNum())) {
                criteria.andEqualTo("goods_num",category.getGoodsNum());
            }
            if (!StringUtils.isEmpty(category.getIsShow())) {
                criteria.andEqualTo("is_show",category.getIsShow());
            }
            if (!StringUtils.isEmpty(category.getIsMenu())) {
                criteria.andEqualTo("is_menu",category.getIsMenu());
            }
            if (!StringUtils.isEmpty(category.getSeq())) {
                criteria.andEqualTo("seq",category.getSeq());
            }
            if (!StringUtils.isEmpty(category.getParentId())) {
                criteria.andEqualTo("parent_id",category.getParentId());
            }
            if (!StringUtils.isEmpty(category.getTemplateId())) {
                criteria.andEqualTo("template_id",category.getTemplateId());
            }
        }
        return example;
    }
}
