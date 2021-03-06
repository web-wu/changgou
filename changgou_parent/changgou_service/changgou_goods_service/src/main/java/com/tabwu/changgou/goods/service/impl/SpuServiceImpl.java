package com.tabwu.changgou.goods.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tabwu.changgou.entity.IdWorker;
import com.tabwu.changgou.entity.MsgConstant;
import com.tabwu.changgou.entity.PageResult;
import com.tabwu.changgou.goods.mapper.BrandMapper;
import com.tabwu.changgou.goods.mapper.CategoryMapper;
import com.tabwu.changgou.goods.mapper.SkuMapper;
import com.tabwu.changgou.goods.mapper.SpuMapper;
import com.tabwu.changgou.goods.service.SpuService;
import com.tabwu.changgou.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class SpuServiceImpl implements SpuService {
    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private SkuMapper skuMapper;

    @Override
    public void addSpu(Spu spu) {
        spuMapper.insertSelective(spu);
    }

    @Override
    public void delSpu(Integer id) {
        spuMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updateSpu(Spu spu) {
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    @Override
    public Spu findOne(Integer id) {
        return spuMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageResult<Spu> findAll(Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage,pageSize);
        Page<Spu> page = (Page<Spu>) spuMapper.selectAll();
        return new PageResult<>(page.getTotal(),page.getResult());
    }

    @Override
    public PageResult<Spu> findAll(Integer currentPage, Integer pageSize, Spu spu) {
        PageHelper.startPage(currentPage,pageSize);
        Example example = createExample(spu);
        Page<Spu> page = (Page<Spu>) spuMapper.selectByExample(example);
        return new PageResult<>(page.getTotal(),page.getResult());
    }

    @Override
    public void saveGoods(Goods goods) {
        Spu spu = goods.getSpu();

        if (spu.getId() == null) {
            spu.setId(idWorker.nextId());
            spuMapper.insertSelective(spu);
        } else {
            spuMapper.updateByPrimaryKeySelective(spu);
            Sku sku = new Sku();
            sku.setSpuId(spu.getId());
            skuMapper.delete(sku);
        }

        Date date = new Date();

        Category category = categoryMapper.selectByPrimaryKey(spu.getCategory3Id());

        Brand brand = brandMapper.selectByPrimaryKey(spu.getBrandId());

        List<Sku> skuList = goods.getSkuList();

        for (Sku sku : skuList) {
            //??????Spu?????????
            String name = spu.getName();

            if (StringUtils.isEmpty(sku.getSpec())) {
                sku.setSpec("{}");
            }

            //??????????????????Map
            Map<String,String> specMap = JSON.parseObject(sku.getSpec(), Map.class);
            //????????????Sku?????????
            for (Map.Entry<String, String> entry : specMap.entrySet()) {
                name+=" "+entry.getValue();
            }
            sku.setName(name);
            //ID
            sku.setId(idWorker.nextId());
            //SpuId
            sku.setSpuId(spu.getId());
            //????????????
            sku.setCreateTime(date);
            //????????????
            sku.setUpdateTime(date);
            //????????????ID
            sku.setCategoryId(spu.getCategory3Id());
            //????????????
            sku.setCategoryName(category.getName());
            //????????????
            sku.setBrandName(brand.getName());
            //??????
            skuMapper.insertSelective(sku);
        }

    }

    @Override
    public Goods findGoodsById(Long spuId) {
        Goods goods = new Goods();
        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        Sku sku = new Sku();
        sku.setSpuId(spuId);
        List<Sku> skus = skuMapper.select(sku);
        goods.setSpu(spu);
        goods.setSkuList(skus);
        return goods;
    }

    @Override
    public PageResult<Goods> findAllGoods(Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage,pageSize);

        Page<Spu> page = (Page<Spu>) spuMapper.selectAll();
        List<Spu> spuList = page.getResult();

        List<Goods> goodsList = new ArrayList<>();

        for (Spu spu : spuList) {
            Sku sku = new Sku();
            sku.setSpuId(spu.getId());
            List<Sku> skuList = skuMapper.select(sku);
            Goods goods = new Goods();
            goods.setSpu(spu);
            goods.setSkuList(skuList);
            goodsList.add(goods);
        }

        return new PageResult<>(page.getTotal(),goodsList);
    }

    @Override
    public PageResult<Goods> findAllGoods(Integer currentPage, Integer pageSize, Goods goods) {
        PageHelper.startPage(currentPage,pageSize);
        Spu spu = goods.getSpu();
        Example example = createExample(spu);
        Page<Spu> page = (Page<Spu>) spuMapper.selectByExample(example);

        List<Goods> goodsList = new ArrayList<>();

        for (Spu spu1 : page.getResult()) {
            Sku sku = new Sku();
            sku.setSpuId(spu1.getId());
            List<Sku> skuList = skuMapper.select(sku);
            Goods goods1 = new Goods();
            goods.setSpu(spu1);
            goods.setSkuList(skuList);
            goodsList.add(goods1);
        }

        return new PageResult<>(page.getTotal(),goodsList);
    }

    @Override
    public void goodsAudit(Long spuId,String statusNum) {
        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        if (spu.getIsDelete().equalsIgnoreCase("1")) {
            throw new RuntimeException(MsgConstant.GOODS_DELETE);
        }
        spu.setStatus(statusNum);
        spu.setIsMarketable(statusNum);
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    @Override
    public void goodsMarketTable(Long spuId, String statusNum) {
        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        if (spu.getIsDelete().equalsIgnoreCase("1")) {
            throw new RuntimeException(MsgConstant.GOODS_DELETE);
        }
        if (spu.getStatus().equalsIgnoreCase("0")) {
            throw new RuntimeException(MsgConstant.GOODS_DELETE);
        }
        spu.setIsMarketable(statusNum);
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    @Override
    public void delGoods(Long spuId) {
        spuMapper.deleteByPrimaryKey(spuId);
        Sku sku = new Sku();
        sku.setSpuId(spuId);
        skuMapper.delete(sku);
    }


    public Example createExample(Spu spu) {
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        if (spu != null) {
            // ??????
            if (!StringUtils.isEmpty(spu.getId())) {
                criteria.andEqualTo("id", spu.getId());
            }
            // ??????
            if (!StringUtils.isEmpty(spu.getSn())) {
                criteria.andEqualTo("sn", spu.getSn());
            }
            // SPU???
            if (!StringUtils.isEmpty(spu.getName())) {
                criteria.andLike("name", "%" + spu.getName() + "%");
            }
            // ?????????
            if (!StringUtils.isEmpty(spu.getCaption())) {
                criteria.andEqualTo("caption", spu.getCaption());
            }
            // ??????ID
            if (!StringUtils.isEmpty(spu.getBrandId())) {
                criteria.andEqualTo("brandId", spu.getBrandId());
            }
            // ????????????
            if (!StringUtils.isEmpty(spu.getCategory1Id())) {
                criteria.andEqualTo("category1Id", spu.getCategory1Id());
            }
            // ????????????
            if (!StringUtils.isEmpty(spu.getCategory2Id())) {
                criteria.andEqualTo("category2Id", spu.getCategory2Id());
            }
            // ????????????
            if (!StringUtils.isEmpty(spu.getCategory3Id())) {
                criteria.andEqualTo("category3Id", spu.getCategory3Id());
            }
            // ??????ID
            if (!StringUtils.isEmpty(spu.getTemplateId())) {
                criteria.andEqualTo("templateId", spu.getTemplateId());
            }
            // ????????????id
            if (!StringUtils.isEmpty(spu.getFreightId())) {
                criteria.andEqualTo("freightId", spu.getFreightId());
            }
            // ??????
            if (!StringUtils.isEmpty(spu.getImage())) {
                criteria.andEqualTo("image", spu.getImage());
            }
            // ????????????
            if (!StringUtils.isEmpty(spu.getImages())) {
                criteria.andEqualTo("images", spu.getImages());
            }
            // ????????????
            if (!StringUtils.isEmpty(spu.getSaleService())) {
                criteria.andEqualTo("saleService", spu.getSaleService());
            }
            // ??????
            if (!StringUtils.isEmpty(spu.getIntroduction())) {
                criteria.andEqualTo("introduction", spu.getIntroduction());
            }
            // ????????????
            if (!StringUtils.isEmpty(spu.getSpecItems())) {
                criteria.andEqualTo("specItems", spu.getSpecItems());
            }
            // ????????????
            if (!StringUtils.isEmpty(spu.getParaItems())) {
                criteria.andEqualTo("paraItems", spu.getParaItems());
            }
            // ??????
            if (!StringUtils.isEmpty(spu.getSaleNum())) {
                criteria.andEqualTo("saleNum", spu.getSaleNum());
            }
            // ?????????
            if (!StringUtils.isEmpty(spu.getCommentNum())) {
                criteria.andEqualTo("commentNum", spu.getCommentNum());
            }
            // ????????????
            if (!StringUtils.isEmpty(spu.getIsMarketable())) {
                criteria.andEqualTo("isMarketable", spu.getIsMarketable());
            }
            // ??????????????????
            if (!StringUtils.isEmpty(spu.getIsEnableSpec())) {
                criteria.andEqualTo("isEnableSpec", spu.getIsEnableSpec());
            }
            // ????????????
            if (!StringUtils.isEmpty(spu.getIsDelete())) {
                criteria.andEqualTo("isDelete", spu.getIsDelete());
            }
            // ????????????
            if (!StringUtils.isEmpty(spu.getStatus())) {
                criteria.andEqualTo("status", spu.getStatus());
            }
        }
        return example;
    }
}
