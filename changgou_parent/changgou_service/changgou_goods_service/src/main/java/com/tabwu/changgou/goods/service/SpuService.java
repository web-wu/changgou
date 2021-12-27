package com.tabwu.changgou.goods.service;

import com.tabwu.changgou.entity.PageResult;
import com.tabwu.changgou.pojo.Goods;
import com.tabwu.changgou.pojo.Spu;

public interface SpuService {
    void addSpu(Spu spu);

    void delSpu(Integer id);

    void updateSpu(Spu spu);

    Spu findOne(Integer id);

    PageResult<Spu> findAll(Integer currentPage, Integer pageSize);

    PageResult<Spu> findAll(Integer currentPage,Integer pageSize,Spu spu);

    void saveGoods(Goods goods);

    void delGoods(Long spuId);

    Goods findGoodsById(Long spuId);

    PageResult<Goods> findAllGoods(Integer currentPage, Integer pageSize);

    PageResult<Goods> findAllGoods(Integer currentPage, Integer pageSize,Goods goods);

    void goodsAudit(Long spuId,String statusNum);

    void goodsMarketTable(Long spuId,String statusNum);


}
