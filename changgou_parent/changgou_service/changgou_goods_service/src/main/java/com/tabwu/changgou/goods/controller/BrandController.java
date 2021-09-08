package com.tabwu.changgou.goods.controller;

import com.tabwu.changgou.entity.MsgConstant;
import com.tabwu.changgou.entity.PageResult;
import com.tabwu.changgou.entity.Result;
import com.tabwu.changgou.entity.StatusCode;
import com.tabwu.changgou.goods.service.BrandService;
import com.tabwu.changgou.pojo.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/brand")
@CrossOrigin
public class BrandController {
    @Autowired
    private BrandService brandService;


    /**
     * 根据 id 查询品牌
     * @param id
     * @return
     */
    @GetMapping("/getBrandById/{id}")
    public Result getBrandById(@PathVariable("id") Integer id) {
        Brand brand = brandService.getBrandById(id);
        return new Result(true, StatusCode.OK, MsgConstant.FIND_BRAND_SUCCESS,brand);
    }

    /**
     * 添加 品牌
     * @param brand
     * @return
     */
    @PostMapping("/addBrand")
    public Result addBrand(@RequestBody Brand brand) {
        brandService.add(brand);
        return new Result(true, StatusCode.OK, MsgConstant.ADD_BRAND_SUCCESS,null);
    }

    /**
     * 根据 id 删除品牌
     * @param id
     * @return
     */
    @DeleteMapping("/delBrand/{id}")
    public Result delBrand(@PathVariable("id") Integer id) {
        brandService.delete(id);
        return new Result(true, StatusCode.OK, MsgConstant.DEL_BRAND_SUCCESS);
    }

    /**
     * 根据 id 修改品牌
     * @param brand
     * @return
     */
    @PutMapping("/updateBrand")
    public Result updateBrand(@RequestBody Brand brand) {
        brandService.update(brand);
        return new Result(true, StatusCode.OK, MsgConstant.UPDATE_BRAND_SUCCESS,null);
    }


    /**
     * 分页查询
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("/findPage/{currentPage}/{pageSize}")
    public Result findPage(@PathVariable("currentPage") Integer currentPage,@PathVariable("pageSize") Integer pageSize) {
        PageResult<Brand> pageResult = brandService.findPage(currentPage, pageSize);
        return new Result(true,StatusCode.OK,MsgConstant.FIND_BRAND_SUCCESS,pageResult);
    }

    /**
     * 条件 分页查询
     * @param brand
     * @param currentPage
     * @param pageSize
     * @return
     */
    @PostMapping("/findPageByCondition/{currentPage}/{pageSize}")
    public Result findPage(@RequestBody Brand brand,@PathVariable("currentPage") Integer currentPage,@PathVariable("pageSize") Integer pageSize) {
        PageResult<Brand> pageResult = brandService.findPage(brand, currentPage, pageSize);
        return new Result(true,StatusCode.OK,MsgConstant.FIND_BRAND_SUCCESS,pageResult);
    }
}
