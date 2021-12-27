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
            //获取Spu的名字
            String name = spu.getName();

            if (StringUtils.isEmpty(sku.getSpec())) {
                sku.setSpec("{}");
            }

            //将规格转换成Map
            Map<String,String> specMap = JSON.parseObject(sku.getSpec(), Map.class);
            //循环组装Sku的名字
            for (Map.Entry<String, String> entry : specMap.entrySet()) {
                name+=" "+entry.getValue();
            }
            sku.setName(name);
            //ID
            sku.setId(idWorker.nextId());
            //SpuId
            sku.setSpuId(spu.getId());
            //创建日期
            sku.setCreateTime(date);
            //修改日期
            sku.setUpdateTime(date);
            //商品分类ID
            sku.setCategoryId(spu.getCategory3Id());
            //分类名字
            sku.setCategoryName(category.getName());
            //品牌名字
            sku.setBrandName(brand.getName());
            //增加
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
            // 主键
            if (!StringUtils.isEmpty(spu.getId())) {
                criteria.andEqualTo("id", spu.getId());
            }
            // 货号
            if (!StringUtils.isEmpty(spu.getSn())) {
                criteria.andEqualTo("sn", spu.getSn());
            }
            // SPU名
            if (!StringUtils.isEmpty(spu.getName())) {
                criteria.andLike("name", "%" + spu.getName() + "%");
            }
            // 副标题
            if (!StringUtils.isEmpty(spu.getCaption())) {
                criteria.andEqualTo("caption", spu.getCaption());
            }
            // 品牌ID
            if (!StringUtils.isEmpty(spu.getBrandId())) {
                criteria.andEqualTo("brandId", spu.getBrandId());
            }
            // 一级分类
            if (!StringUtils.isEmpty(spu.getCategory1Id())) {
                criteria.andEqualTo("category1Id", spu.getCategory1Id());
            }
            // 二级分类
            if (!StringUtils.isEmpty(spu.getCategory2Id())) {
                criteria.andEqualTo("category2Id", spu.getCategory2Id());
            }
            // 三级分类
            if (!StringUtils.isEmpty(spu.getCategory3Id())) {
                criteria.andEqualTo("category3Id", spu.getCategory3Id());
            }
            // 模板ID
            if (!StringUtils.isEmpty(spu.getTemplateId())) {
                criteria.andEqualTo("templateId", spu.getTemplateId());
            }
            // 运费模板id
            if (!StringUtils.isEmpty(spu.getFreightId())) {
                criteria.andEqualTo("freightId", spu.getFreightId());
            }
            // 图片
            if (!StringUtils.isEmpty(spu.getImage())) {
                criteria.andEqualTo("image", spu.getImage());
            }
            // 图片列表
            if (!StringUtils.isEmpty(spu.getImages())) {
                criteria.andEqualTo("images", spu.getImages());
            }
            // 售后服务
            if (!StringUtils.isEmpty(spu.getSaleService())) {
                criteria.andEqualTo("saleService", spu.getSaleService());
            }
            // 介绍
            if (!StringUtils.isEmpty(spu.getIntroduction())) {
                criteria.andEqualTo("introduction", spu.getIntroduction());
            }
            // 规格列表
            if (!StringUtils.isEmpty(spu.getSpecItems())) {
                criteria.andEqualTo("specItems", spu.getSpecItems());
            }
            // 参数列表
            if (!StringUtils.isEmpty(spu.getParaItems())) {
                criteria.andEqualTo("paraItems", spu.getParaItems());
            }
            // 销量
            if (!StringUtils.isEmpty(spu.getSaleNum())) {
                criteria.andEqualTo("saleNum", spu.getSaleNum());
            }
            // 评论数
            if (!StringUtils.isEmpty(spu.getCommentNum())) {
                criteria.andEqualTo("commentNum", spu.getCommentNum());
            }
            // 是否上架
            if (!StringUtils.isEmpty(spu.getIsMarketable())) {
                criteria.andEqualTo("isMarketable", spu.getIsMarketable());
            }
            // 是否启用规格
            if (!StringUtils.isEmpty(spu.getIsEnableSpec())) {
                criteria.andEqualTo("isEnableSpec", spu.getIsEnableSpec());
            }
            // 是否删除
            if (!StringUtils.isEmpty(spu.getIsDelete())) {
                criteria.andEqualTo("isDelete", spu.getIsDelete());
            }
            // 审核状态
            if (!StringUtils.isEmpty(spu.getStatus())) {
                criteria.andEqualTo("status", spu.getStatus());
            }
        }
        return example;
    }
}
