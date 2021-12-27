package com.tabwu.changgou.goods.controller;

import com.tabwu.changgou.entity.MsgConstant;
import com.tabwu.changgou.entity.PageResult;
import com.tabwu.changgou.entity.Result;
import com.tabwu.changgou.entity.StatusCode;
import com.tabwu.changgou.goods.service.SpecService;
import com.tabwu.changgou.pojo.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/spec")
@CrossOrigin
public class SpecController {
    @Autowired
    private SpecService specService;

    /**
     * 添加商品规格
     * @param spec
     * @return
     */
    @PostMapping("/add")
    public Result addSpec(@RequestBody Spec spec) {
        specService.addSpec(spec);
        return new Result(true, StatusCode.OK, MsgConstant.ADD_SUCCESS);
    }

    /**
     * 根据id 删除 商品规格
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result delSpec(@PathVariable("id") Integer id) {
        specService.delSpec(id);
        return new Result(true, StatusCode.OK, MsgConstant.DELETE_SUCCESS);
    }

    /**
     * 修改商品规格
     * @param spec
     * @return
     */
    @PutMapping("/update")
    public Result updateSpec(@RequestBody Spec spec) {
        specService.updateSpec(spec);
        return new Result(true, StatusCode.OK, MsgConstant.UPDATE_SUCCESS);
    }

    /**
     * 根据id查询商品规格
     * @param id
     * @return
     */
    @GetMapping("/findById/{id}")
    public Result findById(@PathVariable("id") Integer id) {
        Spec spec = specService.findById(id);
        return new Result(true, StatusCode.OK, MsgConstant.QUERY_SUCCESS,spec);
    }

    /**
     * 商品规格 分页查询
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("/findAll/{currentPage}/{pageSize}")
    public Result findAll(@PathVariable("currentPage") Integer currentPage,@PathVariable("pageSize") Integer pageSize) {
        PageResult<Spec> pageResult = specService.findAll(currentPage, pageSize);
        return new Result(true, StatusCode.OK, MsgConstant.QUERY_SUCCESS,pageResult);
    }

    /**
     * 多条件搜索 分页查询
     * @param currentPage
     * @param pageSize
     * @param spec
     * @return
     */
    @PostMapping("/findAll/{currentPage}/{pageSize}")
    public Result findAll(@PathVariable("currentPage") Integer currentPage,@PathVariable("pageSize") Integer pageSize,@RequestBody Spec spec) {
        PageResult<Spec> pageResult = specService.findAll(currentPage, pageSize, spec);
        return new Result(true, StatusCode.OK, MsgConstant.QUERY_SUCCESS,pageResult);
    }


    /**
     * 根据 分类id 查询商品规格
     * @param id
     * @return
     */
    @GetMapping("/findByCategoryId/{id}")
    public Result findByCategoryId(@PathVariable("id") Integer id) {
        List<Spec> specList = specService.findByCategoryId(id);
        return new Result(true, StatusCode.OK, MsgConstant.QUERY_SUCCESS,specList);
    }
}
