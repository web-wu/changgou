package com.tabwu.changgou.goods.controller;

import com.tabwu.changgou.entity.MsgConstant;
import com.tabwu.changgou.entity.PageResult;
import com.tabwu.changgou.entity.Result;
import com.tabwu.changgou.entity.StatusCode;
import com.tabwu.changgou.goods.service.SkuService;
import com.tabwu.changgou.pojo.Sku;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sku")
@CrossOrigin
public class SkuController {
    @Autowired
    private SkuService skuService;

    /**
     * 添加 商品 特有的 属性
     * @param sku
     * @return
     */
    @PostMapping("/add")
    public Result addSku(@RequestBody Sku sku) {
        skuService.addSku(sku);
        return new Result(true, StatusCode.OK, MsgConstant.ADD_SUCCESS);
    }

    /**
     * 根据 id 删除 商品 特有的额属性
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result delSku(@PathVariable("id") Integer id) {
        skuService.delSku(id);
        return new Result(true, StatusCode.OK, MsgConstant.DELETE_SUCCESS);
    }

    /**
     * 修改 商品 特有的额属性
     * @param sku
     * @return
     */
    @PutMapping("/update")
    public Result updateSku(@RequestBody Sku sku) {
        skuService.updateSku(sku);
        return new Result(true, StatusCode.OK, MsgConstant.UPDATE_SUCCESS);
    }

    /**
     * 根据 id 查询 商品 特有的额属性
     * @param id
     * @return
     */
    @GetMapping("/findOne/{id}")
    public Result findOne(@PathVariable("id") Integer id) {
        Sku sku = skuService.findOne(id);
        return new Result(true, StatusCode.OK, MsgConstant.QUERY_SUCCESS,sku);
    }

    /**
     * 商品 特有的额属性  分页查询
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("/findAll/{currentPage}/{pageSize}")
    public Result findAll(@PathVariable("currentPage") Integer currentPage,@PathVariable("pageSize") Integer pageSize) {
        PageResult<Sku> pageResult = skuService.findAll(currentPage, pageSize);
        return new Result(true, StatusCode.OK, MsgConstant.QUERY_SUCCESS,pageResult);
    }

    /**
     * 多条件搜索 商品 特有的额属性  分页查询
     * @param currentPage
     * @param pageSize
     * @param sku
     * @return
     */
    @PostMapping("/findAll/{currentPage}/{pageSize}")
    public Result findAll(@PathVariable("currentPage") Integer currentPage,@PathVariable("pageSize") Integer pageSize,@RequestBody Sku sku) {
        PageResult<Sku> pageResult = skuService.findAll(currentPage, pageSize,sku);
        return new Result(true, StatusCode.OK, MsgConstant.QUERY_SUCCESS,pageResult);
    }

    /**
     * 根据 审核状态 查询出审核通过的 特有属性
     * @param status
     * @return
     */
    @GetMapping("/findSkuByStatus/{status}")
    public Result findSkuByStatus(@PathVariable("status") String status) {
        List<Sku> skuList = skuService.findSkuByStatus(status);
        return new Result(true, StatusCode.OK, MsgConstant.QUERY_SUCCESS,skuList);
    }
}
