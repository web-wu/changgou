package com.tabwu.changgou.goods.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tabwu.changgou.entity.PageResult;
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
    public Brand getBrandById(Integer id) {
        Brand brand = brandMapper.selectByPrimaryKey(id);
        return brand;
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
    public PageResult<Brand> findPage(Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage,pageSize);
        Page<Brand> page = (Page<Brand>) brandMapper.selectAll();
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public PageResult<Brand> findPage(Brand brand,Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage,pageSize);
        Example example = createExample(brand);
        Page<Brand> page = (Page<Brand>) brandMapper.selectByExample(example);
        return new PageResult<>(page.getTotal(),page.getResult());
    }

    @Override
    public List<Brand> findByCategoryId(Integer id) {
        return brandMapper.findByCategoryId(id);
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
