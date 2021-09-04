package com.tabwu.changgou.goods.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tabwu.changgou.goods.mapper.BrandMapper;
import com.tabwu.changgou.goods.service.BrandService;
import com.tabwu.changgou.pojo.Brand;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
@Transactional
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandMapper brandMapper;

    @Override
    public List<Brand> findAll() {
        return brandMapper.selectAll();
    }

    @Override
    public Brand getBrandById(Integer id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    @Override
    public void add(Brand brand) {
        brandMapper.insertSelective(brand);
    }

    @Override
    public void delete(Integer id) {
        brandMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Brand brand) {
        brandMapper.updateByPrimaryKeySelective(brand);
    }

    @Override
    public List<Brand> searchBrand(Brand brand) {
        Example example = createExample(brand);
        return brandMapper.selectByExample(example);
    }

    @Override
    public PageInfo<Brand> findPage(Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage,pageSize);
        return (PageInfo<Brand>) brandMapper.selectAll();
    }

    @Override
    public PageInfo<Brand> findPage(Brand brand,Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage,pageSize);
        Example example = createExample(brand);
        return (PageInfo<Brand>) brandMapper.selectByExample(example);
    }

    public Example createExample(Brand brand) {
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();
        if (brand != null) {
            if (!StringUtils.isEmpty(brand.getName())) {
                criteria.andLike("name","%"+ brand.getName() +"%");
            }
            if (!StringUtils.isEmpty(brand.getImage())) {
                criteria.andLike("image","%"+ brand.getImage() +"%");
            }
            if (!StringUtils.isEmpty(brand.getLetter())) {
                criteria.andLike("letter","%"+ brand.getLetter() +"%");
            }
            if (!StringUtils.isEmpty(brand.getLetter())) {
                criteria.andEqualTo("id",brand.getId());
            }
            /*if (!StringUtils.isEmpty(brand.getSeq())) {
                criteria.andEqualTo("seq",brand.getSeq());
            }*/

        }
        return example;
    }

}
