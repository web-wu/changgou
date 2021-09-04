package com.tabwu.changgou.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * 商品组合实体类
 */
@ApiModel(description = "商品信息")
public class Goods implements Serializable {
    //SPU
    private com.tabwu.changgou.pojo.Spu spu;
    //SKU集合
    private List<com.tabwu.changgou.pojo.Sku> skuList;

    public com.tabwu.changgou.pojo.Spu getSpu() {
        return spu;
    }

    public void setSpu(com.tabwu.changgou.pojo.Spu spu) {
        this.spu = spu;
    }

    public List<com.tabwu.changgou.pojo.Sku> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<com.tabwu.changgou.pojo.Sku> skuList) {
        this.skuList = skuList;
    }
}