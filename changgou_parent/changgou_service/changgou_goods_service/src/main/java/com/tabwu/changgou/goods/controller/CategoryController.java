package com.tabwu.changgou.goods.controller;

import com.tabwu.changgou.entity.MsgConstant;
import com.tabwu.changgou.entity.PageResult;
import com.tabwu.changgou.entity.Result;
import com.tabwu.changgou.entity.StatusCode;
import com.tabwu.changgou.goods.service.CategoryService;
import com.tabwu.changgou.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@CrossOrigin
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 添加商品分类
     * @param category
     * @return
     */
    @PostMapping("/add")
    public Result addCategory(@RequestBody Category category) {
        categoryService.addCategory(category);
        return new Result(true, StatusCode.OK, MsgConstant.ADD_SUCCESS);
    }

    /**
     * 根据 id 删除商品分类
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result delCategory(@PathVariable("id") Integer id) {
        categoryService.delCategory(id);
        return new Result(true, StatusCode.OK, MsgConstant.DELETE_SUCCESS);
    }

    /**
     * 修改商品分类
     * @param category
     * @return
     */
    @PutMapping("/update")
    public Result updateCategory(@RequestBody Category category) {
        categoryService.updateCategory(category);
        return new Result(true, StatusCode.OK, MsgConstant.UPDATE_SUCCESS);
    }

    /**
     * 根据id 查询分类
     * @param id
     * @return
     */
    @GetMapping("/findById/{id}")
    public Result findById(@PathVariable("id") Integer id) {
        Category category = categoryService.findById(id);
        return new Result(true, StatusCode.OK, MsgConstant.QUERY_SUCCESS,category);
    }

    /**
     * 根据 parent_id 查询下级分类
     * @param pid
     * @return
     */
    @GetMapping("/findByPid/{pid}")
    public Result findSubCategory(@PathVariable("pid") Integer pid) {
        List<Category> categories = categoryService.findParentId(pid);
        return new Result(true, StatusCode.OK, MsgConstant.QUERY_SUCCESS,categories);
    }

    /**
     * 商品分类 分页查询
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("/findAll/{currentPage}/{pageSize}")
    public Result findAll(@PathVariable("currentPage") Integer currentPage,@PathVariable("pageSize") Integer pageSize) {
        PageResult<Category> categoryList = categoryService.findAll(currentPage, pageSize);
        return new Result(true, StatusCode.OK, MsgConstant.QUERY_SUCCESS,categoryList);
    }

    /**
     * 多条件搜索 分页查询
     * @param currentPage
     * @param pageSize
     * @param category
     * @return
     */
    @PostMapping("/findAll{currentPage}/{pageSize}")
    public Result findAll(@PathVariable("currentPage") Integer currentPage,@PathVariable("pageSize") Integer pageSize,@RequestBody Category category) {
        PageResult<Category> categoryList = categoryService.findAll(currentPage, pageSize,category);
        return new Result(true, StatusCode.OK, MsgConstant.QUERY_SUCCESS,categoryList);
    }
}
