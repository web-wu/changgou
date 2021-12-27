package com.tabwu.changgou.goods.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tabwu.changgou.entity.PageResult;
import com.tabwu.changgou.goods.mapper.CategoryMapper;
import com.tabwu.changgou.goods.mapper.SpecMapper;
import com.tabwu.changgou.goods.service.SpecService;
import com.tabwu.changgou.pojo.Category;
import com.tabwu.changgou.pojo.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
@Transactional
public class SpecServiceImpl implements SpecService {
    @Autowired
    private SpecMapper specMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void addSpec(Spec spec) {
        specMapper.insertSelective(spec);
    }

    @Override
    public void delSpec(Integer id) {
        specMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updateSpec(Spec spec) {
        specMapper.updateByPrimaryKeySelective(spec);
    }

    @Override
    public Spec findById(Integer id) {
        return specMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageResult<Spec> findAll(Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage,pageSize);
        Page<Spec> page = (Page<Spec>) specMapper.selectAll();
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public PageResult<Spec> findAll(Integer currentPage, Integer pageSize, Spec spec) {
        PageHelper.startPage(currentPage,pageSize);
        Example example = createExample(spec);
        Page<Spec> page = (Page<Spec>) specMapper.selectByExample(example);
        return new PageResult<>(page.getTotal(),page.getResult());
    }

    @Override
    public List<Spec> findByCategoryId(Integer categoryId) {
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        Spec spec = new Spec();
        spec.setTemplateId(category.getTemplateId());
        return specMapper.select(spec);
    }


    public Example createExample(Spec spec) {
        Example example = new Example(Spec.class);
        Example.Criteria criteria = example.createCriteria();
        if (spec != null) {
            if (!StringUtils.isEmpty(spec.getId())) {
                criteria.andEqualTo("id",spec.getId());
            }
            if (!StringUtils.isEmpty(spec.getName())) {
                criteria.andLike("name","%" +spec.getName() + "%");
            }
            if (!StringUtils.isEmpty(spec.getOptions())) {
                criteria.andLike("options","%" + spec.getOptions() + "%");
            }
            if (!StringUtils.isEmpty(spec.getSeq())) {
                criteria.andEqualTo("seq",spec.getSeq());
            }
            if (!StringUtils.isEmpty(spec.getTemplateId())) {
                criteria.andEqualTo("template_id",spec.getTemplateId());
            }
        }
        return example;
    }
}
