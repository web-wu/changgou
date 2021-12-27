package com.tabwu.changgou.goods.controller;

import com.tabwu.changgou.entity.MsgConstant;
import com.tabwu.changgou.entity.PageResult;
import com.tabwu.changgou.entity.Result;
import com.tabwu.changgou.entity.StatusCode;
import com.tabwu.changgou.goods.service.SpuService;
import com.tabwu.changgou.pojo.Goods;
import com.tabwu.changgou.pojo.Spu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(("/spu"))
@CrossOrigin
public class SpuController {
    @Autowired
    private SpuService spuService;


    /**
     * 添加某类 商品 公共 属性
     * @param spu
     * @return
     */
    @PostMapping("/add")
    public Result addSpu(@RequestBody Spu spu) {
        spuService.addSpu(spu);
        return new Result(true, StatusCode.OK, MsgConstant.ADD_SUCCESS);
    }

    /**
     * 根据 id 删除 商品 公共 属性
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result delSpu(@PathVariable("id") Integer id) {
        spuService.delSpu(id);
        return new Result(true, StatusCode.OK, MsgConstant.DELETE_SUCCESS);
    }

    /**
     * 修改 商品 公共 属性
     * @param spu
     * @return
     */
    @PutMapping("/update")
    public Result updateSpu(@RequestBody Spu spu) {
        spuService.updateSpu(spu);
        return new Result(true, StatusCode.OK, MsgConstant.UPDATE_SUCCESS);
    }

    /**
     * 根据 id 修改 商品 公共 属性
     * @param id
     * @return
     */
    @GetMapping("/findOne/{id}")
    public Result findOne(@PathVariable("id") Integer id) {
        Spu spu = spuService.findOne(id);
        return new Result(true, StatusCode.OK, MsgConstant.QUERY_SUCCESS,spu);
    }

    /**
     *  商品 公共 属性 分页查询
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("/findAll/{currentPage}/{pageSize}")
    public Result findAll(@PathVariable("currentPage") Integer currentPage,@PathVariable("pageSize") Integer pageSize) {
        PageResult<Spu> pageResult = spuService.findAll(currentPage, pageSize);
        return new Result(true, StatusCode.OK, MsgConstant.QUERY_SUCCESS,pageResult);
    }

    /**
     * 多条件搜索 商品 公共 属性 分页查询
     * @param currentPage
     * @param pageSize
     * @param spu
     * @return
     */
    @PostMapping("/findAll/{currentPage}/{pageSize}")
    public Result findAll(@PathVariable("currentPage") Integer currentPage,@PathVariable("pageSize") Integer pageSize,@RequestBody Spu spu) {
        PageResult<Spu> pageResult = spuService.findAll(currentPage, pageSize,spu);
        return new Result(true, StatusCode.OK, MsgConstant.QUERY_SUCCESS,pageResult);
    }


    /**
     * 添加 商品
     * @param goods
     * @return
     */
    @PostMapping("/saveGoods")
    public Result saveGoods(@RequestBody Goods goods) {
        spuService.saveGoods(goods);
        return new Result(true, StatusCode.OK, MsgConstant.ADD_SUCCESS);
    }

    /**
     * 根据 id 查询 商品
     * @param spuId
     * @return
     */
    @GetMapping("/findGoodsById/{spuId}")
    public Result findGoodsById(@PathVariable("spuId") Long spuId) {
        Goods goods = spuService.findGoodsById(spuId);
        return new Result(true, StatusCode.OK, MsgConstant.QUERY_SUCCESS,goods);
    }

    /**
     * 根据 id 删除 商品
     * @param spuId
     * @return
     */
    @DeleteMapping("/deleteGoods/{spuId}")
    public Result deleteGoods(@PathVariable("spuId") Long spuId) {
        spuService.delGoods(spuId);
        return new Result(true, StatusCode.OK, MsgConstant.DELETE_SUCCESS);
    }

    /**
     * 商品分页查询
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("/findAllGoods/{currentPage}/{pageSize}")
    public Result findAllGoods(@PathVariable("currentPage") Integer currentPage,@PathVariable("pageSize") Integer pageSize) {
        PageResult<Goods> pageResult = spuService.findAllGoods(currentPage, pageSize);
        return new Result(true, StatusCode.OK, MsgConstant.QUERY_SUCCESS,pageResult);
    }

    /**
     * 公共属性 多条件搜索 商品分页查询
     * @param currentPage
     * @param pageSize
     * @param goods
     * @return
     */
    @PostMapping("/findAllGoods/{currentPage}/{pageSize}")
    public Result findAllGoods(@PathVariable("currentPage") Integer currentPage,@PathVariable("pageSize") Integer pageSize,@RequestBody Goods goods) {
        PageResult<Goods> pageResult = spuService.findAllGoods(currentPage, pageSize,goods);
        return new Result(true, StatusCode.OK, MsgConstant.QUERY_SUCCESS,pageResult);
    }

    /**
     * 商品 审核与删除 状态修改
     * @param spuId
     * @param statusNum  状态值 为String 类型 为 0 与 1
     * @return
     */
    @PutMapping("/auditGoods/{spuId}")
    public Result auditGoods(@PathVariable("spuId")Long spuId,String statusNum) {
        spuService.goodsAudit(spuId,statusNum);
        return new Result(true, StatusCode.OK, MsgConstant.GOODS_AUDIT_SUCCESS);
    }


    /**
     * 商品 上架 状态修改
     * @param spuId
     * @param statusNum  状态值 为String 类型 为 0 与 1
     * @return
     */
    @PutMapping("/changeGoodsMarketTable/{spuId}")
    public Result changeGoodsMarketTable(@PathVariable("spuId")Long spuId,String statusNum) {
        spuService.goodsMarketTable(spuId,statusNum);
        return new Result(true, StatusCode.OK, MsgConstant.GOODS_MARKETTABLE_SUCCESS);
    }

}
