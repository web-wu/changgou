package com.tabwu.changgou.goods.controller;

import com.tabwu.changgou.entity.MsgConstant;
import com.tabwu.changgou.entity.PageResult;
import com.tabwu.changgou.entity.Result;
import com.tabwu.changgou.entity.StatusCode;
import com.tabwu.changgou.goods.service.TemplateService;
import com.tabwu.changgou.pojo.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/template")
@CrossOrigin
public class TemplateController {
    @Autowired
    private TemplateService templateService;

    /**
     * 添加 template
     * @param template
     * @return
     */
    @PostMapping("/add")
    public Result addTemplate(@RequestBody Template template) {
        templateService.addTemplate(template);
        return new Result(true, StatusCode.OK, MsgConstant.ADD_SUCCESS);
    }

    /**
     * 根据 id 删除 template
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result delTemplate(@PathVariable("id") Integer id) {
        templateService.delTemplate(id);
        return new Result(true, StatusCode.OK, MsgConstant.DELETE_SUCCESS);
    }

    /**
     * 修改 template
     * @param template
     * @return
     */
    @PutMapping("/update")
    public Result updateTemplate(@RequestBody Template template) {
        templateService.updateTemplate(template);
        return new Result(true, StatusCode.OK, MsgConstant.UPDATE_SUCCESS);
    }

    /**
     * 根据 id 查询 template
     * @param id
     * @return
     */
    @GetMapping("/findOne/{id}")
    public Result findOne(@PathVariable("id") Integer id) {
        Template template = templateService.findOne(id);
        return new Result(true, StatusCode.OK, MsgConstant.QUERY_SUCCESS,template);
    }

    /**
     * 分页查询 template
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("/findAll/{currentPage}/{pageSize}")
    public Result findOne(@PathVariable("currentPage") Integer currentPage,@PathVariable("pageSize") Integer pageSize) {
        PageResult<Template> pageResult = templateService.findAll(currentPage,pageSize);
        return new Result(true, StatusCode.OK, MsgConstant.QUERY_SUCCESS,pageResult);
    }


    /**
     * 条件搜索 分页查询
     * @param currentPage
     * @param pageSize
     * @param template
     * @return
     */
    @PostMapping("/findAll/{currentPage}/{pageSize}")
    public Result findOne(@PathVariable("currentPage") Integer currentPage,@PathVariable("pageSize") Integer pageSize,@RequestBody Template template) {
        PageResult<Template> pageResult = templateService.findAll(currentPage,pageSize,template);
        return new Result(true, StatusCode.OK, MsgConstant.QUERY_SUCCESS,pageResult);
    }

    /**
     * 根据 商品分类 id 查询，模板
     * @param id
     * @return
     */
    @GetMapping("/getTeemplateByCategoryId/{id}")
    public Result getTeemplateByCategoryId(@PathVariable("id") Integer id) {
        Template template = templateService.findByCategoryId(id);
        return new Result(true, StatusCode.OK, MsgConstant.QUERY_SUCCESS,template);
    }
}
