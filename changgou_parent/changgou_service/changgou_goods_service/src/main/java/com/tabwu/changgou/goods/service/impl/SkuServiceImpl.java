package com.tabwu.changgou.goods.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tabwu.changgou.entity.PageResult;
import com.tabwu.changgou.goods.mapper.SkuMapper;
import com.tabwu.changgou.goods.service.SkuService;
import com.tabwu.changgou.pojo.Sku;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;


@Service
@Transactional
public class SkuServiceImpl implements SkuService {
    @Autowired
    private SkuMapper skuMapper;

    @Override
    public void addSku(Sku sku) {
        skuMapper.insertSelective(sku);
    }

    @Override
    public void delSku(Integer id) {
        skuMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updateSku(Sku sku) {
        skuMapper.updateByPrimaryKeySelective(sku);
    }

    @Override
    public Sku findOne(Integer id) {
        return skuMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageResult<Sku> findAll(Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage,pageSize);
        Page<Sku> page = (Page<Sku>) skuMapper.selectAll();
        return new PageResult<>(page.getTotal(),page.getResult());
    }

    @Override
    public PageResult<Sku> findAll(Integer currentPage, Integer pageSize, Sku sku) {
        PageHelper.startPage(currentPage,pageSize);
        Example example = createExample(sku);
        Page<Sku> page = (Page<Sku>) skuMapper.selectByExample(example);
        return new PageResult<>(page.getTotal(),page.getResult());
    }

    @Override
    public List<Sku> findSkuByStatus(String status) {
        Sku sku = new Sku();
        sku.setStatus(status);
        return skuMapper.select(sku);
    }


    public Example createExample(Sku sku) {
        Example example = new Example(Sku.class);
        Example.Criteria criteria = example.createCriteria();
        if (sku != null) {
            // ??????id
            if(!StringUtils.isEmpty(sku.getId())){
                criteria.andEqualTo("id",sku.getId());
            }
            // ????????????
            if(!StringUtils.isEmpty(sku.getSn())){
                criteria.andEqualTo("sn",sku.getSn());
            }
            // SKU??????
            if(!StringUtils.isEmpty(sku.getName())){
                criteria.andLike("name","%"+sku.getName()+"%");
            }
            // ???????????????
            if(!StringUtils.isEmpty(sku.getPrice())){
                criteria.andEqualTo("price",sku.getPrice());
            }
            // ????????????
            if(!StringUtils.isEmpty(sku.getNum())){
                criteria.andEqualTo("num",sku.getNum());
            }
            // ??????????????????
            if(!StringUtils.isEmpty(sku.getAlertNum())){
                criteria.andEqualTo("alertNum",sku.getAlertNum());
            }
            // ????????????
            if(!StringUtils.isEmpty(sku.getImage())){
                criteria.andEqualTo("image",sku.getImage());
            }
            // ??????????????????
            if(!StringUtils.isEmpty(sku.getImages())){
                criteria.andEqualTo("images",sku.getImages());
            }
            // ???????????????
            if(!StringUtils.isEmpty(sku.getWeight())){
                criteria.andEqualTo("weight",sku.getWeight());
            }
            // ????????????
            if(!StringUtils.isEmpty(sku.getCreateTime())){
                criteria.andEqualTo("createTime",sku.getCreateTime());
            }
            // ????????????
            if(!StringUtils.isEmpty(sku.getUpdateTime())){
                criteria.andEqualTo("updateTime",sku.getUpdateTime());
            }
            // SPUID
            if(!StringUtils.isEmpty(sku.getSpuId())){
                criteria.andEqualTo("spuId",sku.getSpuId());
            }
            // ??????ID
            if(!StringUtils.isEmpty(sku.getCategoryId())){
                criteria.andEqualTo("categoryId",sku.getCategoryId());
            }
            // ????????????
            if(!StringUtils.isEmpty(sku.getCategoryName())){
                criteria.andEqualTo("categoryName",sku.getCategoryName());
            }
            // ????????????
            if(!StringUtils.isEmpty(sku.getBrandName())){
                criteria.andEqualTo("brandName",sku.getBrandName());
            }
            // ??????
            if(!StringUtils.isEmpty(sku.getSpec())){
                criteria.andEqualTo("spec",sku.getSpec());
            }
            // ??????
            if(!StringUtils.isEmpty(sku.getSaleNum())){
                criteria.andEqualTo("saleNum",sku.getSaleNum());
            }
            // ?????????
            if(!StringUtils.isEmpty(sku.getCommentNum())){
                criteria.andEqualTo("commentNum",sku.getCommentNum());
            }
            // ???????????? 1-?????????2-?????????3-??????
            if(!StringUtils.isEmpty(sku.getStatus())){
                criteria.andEqualTo("status",sku.getStatus());
            }
        }
        return example;
    }
}
