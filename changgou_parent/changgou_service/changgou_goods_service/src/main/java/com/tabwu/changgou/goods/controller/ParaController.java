package com.tabwu.changgou.goods.controller;

import com.tabwu.changgou.entity.MsgConstant;
import com.tabwu.changgou.entity.PageResult;
import com.tabwu.changgou.entity.Result;
import com.tabwu.changgou.entity.StatusCode;
import com.tabwu.changgou.goods.service.ParaService;
import com.tabwu.changgou.pojo.Para;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/para")
@CrossOrigin
public class ParaController {
    @Autowired
    private ParaService paraService;

    /**
     * 添加 商品参数
     * @param para
     * @return
     */
    @PostMapping("/add")
    public Result addPara(@RequestBody Para para) {
        paraService.addPara(para);
        return new Result(true, StatusCode.OK, MsgConstant.ADD_SUCCESS);
    }

    /**
     * 根据 id 删除 商品 参数
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result delPara(@PathVariable("id") Integer id) {
        paraService.delPara(id);
        return new Result(true,StatusCode.OK,MsgConstant.DELETE_SUCCESS);
    }

    /**
     * 修改 商品参数
     * @param para
     * @return
     */
    @PutMapping("/update")
    public Result updatePara(@RequestBody Para para) {
        paraService.updatePara(para);
        return new Result(true,StatusCode.OK,MsgConstant.UPDATE_SUCCESS);
    }

    /**
     * 根据 id 查询 商品参数
     * @param id
     * @return
     */
    @GetMapping("/findOne/{id}")
    public Result findById(@PathVariable("id") Integer id) {
        Para para = paraService.findById(id);
        return new Result(true,StatusCode.OK,MsgConstant.QUERY_SUCCESS,para);
    }

    /**
     * 分页查询
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("/findAll/{currentPage}/{pageSize}")
    public Result findAll(@PathVariable("currentPage") Integer currentPage,@PathVariable("pageSize") Integer pageSize) {
        PageResult<Para> pageResult = paraService.findAll(currentPage, pageSize);
        return new Result(true,StatusCode.OK,MsgConstant.QUERY_SUCCESS,pageResult);
    }


    /**
     * 多条件搜索 分页查询
     * @param currentPage
     * @param pageSize
     * @param para
     * @return
     */
    @PostMapping("/findAll/{currentPage}/{pageSize}")
    public Result findAll(@PathVariable("currentPage") Integer currentPage,@PathVariable("pageSize") Integer pageSize,@RequestBody Para para) {
        PageResult<Para> pageResult = paraService.findAll(currentPage, pageSize, para);
        return new Result(true,StatusCode.OK,MsgConstant.QUERY_SUCCESS,pageResult);
    }

    /**
     * 根据 分类id 查询商品参数
     * @param id
     * @return
     */
    @GetMapping("/findByCategoryId/{id}")
    public Result findByCategoryId(@PathVariable("id") Integer id) {
        List<Para> paraList = paraService.findByCategoryId(id);
        return new Result(true,StatusCode.OK,MsgConstant.QUERY_SUCCESS,paraList);
    }
}
