package com.tabwu.changgou.goods.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tabwu.changgou.entity.PageResult;
import com.tabwu.changgou.goods.mapper.CategoryMapper;
import com.tabwu.changgou.goods.mapper.ParaMapper;
import com.tabwu.changgou.goods.service.ParaService;
import com.tabwu.changgou.pojo.Category;
import com.tabwu.changgou.pojo.Para;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
@Transactional
public class ParaServiceImpl implements ParaService {

    @Autowired
    private ParaMapper paraMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void addPara(Para para) {
        paraMapper.insertSelective(para);
    }

    @Override
    public void delPara(Integer id) {
        paraMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updatePara(Para para) {
        paraMapper.updateByPrimaryKeySelective(para);
    }

    @Override
    public Para findById(Integer id) {
        return paraMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageResult<Para> findAll(Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage,pageSize);
        Page<Para> page = (Page<Para>) paraMapper.selectAll();
        return new PageResult<>(page.getTotal(),page.getResult());
    }

    @Override
    public PageResult<Para> findAll(Integer currentPage, Integer pageSize, Para para) {
        PageHelper.startPage(currentPage,pageSize);
        Example example = createExample(para);
        Page<Para> page = (Page<Para>) paraMapper.selectByExample(example);
        return new PageResult<>(page.getTotal(),page.getResult());
    }

    @Override
    public List<Para> findByCategoryId(Integer id) {
        Category category = categoryMapper.selectByPrimaryKey(id);
        Para para = new Para();
        para.setTemplateId(category.getTemplateId());
        return paraMapper.select(para);
    }


    public Example createExample(Para para) {
        Example example = new Example(Para.class);
        Example.Criteria criteria = example.createCriteria();

        if (para != null) {
            if (!StringUtils.isEmpty(para.getId())) {
                criteria.andEqualTo("id",para.getId());
            }
            if (!StringUtils.isEmpty(para.getName())) {
                criteria.andLike("name","%" + para.getName() + "%");
            }
            if (!StringUtils.isEmpty(para.getOptions())) {
                criteria.andLike("options","%" + para.getOptions() + "%");
            }
            if (!StringUtils.isEmpty(para.getSeq())) {
                criteria.andEqualTo("seq",para.getSeq());
            }
            if (!StringUtils.isEmpty(para.getTemplateId())) {
                criteria.andEqualTo("template_id",para.getTemplateId());
            }
        }
        return example;
    }
}
