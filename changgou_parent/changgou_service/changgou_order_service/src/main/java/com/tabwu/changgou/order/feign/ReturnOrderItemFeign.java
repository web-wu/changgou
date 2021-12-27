package com.tabwu.changgou.order.feign;
import com.github.pagehelper.PageInfo;
import com.tabwu.changgou.entity.Result;
import com.tabwu.changgou.order.entity.ReturnOrderItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/****
 * @Author:shenkunlin
 * @Description:
 * @Date 2019/6/18 13:58
 *****/
@FeignClient(name="order-service")
@RequestMapping("/returnOrderItem")
public interface ReturnOrderItemFeign {

    /***
     * ReturnOrderItem分页条件搜索实现
     * @param returnOrderItem
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    Result<PageInfo> findPage(@RequestBody(required = false) ReturnOrderItem returnOrderItem, @PathVariable int page, @PathVariable  int size);

    /***
     * ReturnOrderItem分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    Result<PageInfo> findPage(@PathVariable  int page, @PathVariable  int size);

    /***
     * 多条件搜索品牌数据
     * @param returnOrderItem
     * @return
     */
    @PostMapping(value = "/search" )
    Result<List<ReturnOrderItem>> findList(@RequestBody(required = false) ReturnOrderItem returnOrderItem);

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    Result delete(@PathVariable String id);

    /***
     * 修改ReturnOrderItem数据
     * @param returnOrderItem
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    Result update(@RequestBody ReturnOrderItem returnOrderItem,@PathVariable String id);

    /***
     * 新增ReturnOrderItem数据
     * @param returnOrderItem
     * @return
     */
    @PostMapping
    Result add(@RequestBody ReturnOrderItem returnOrderItem);

    /***
     * 根据ID查询ReturnOrderItem数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    Result<ReturnOrderItem> findById(@PathVariable String id);

    /***
     * 查询ReturnOrderItem全部数据
     * @return
     */
    @GetMapping
    Result<List<ReturnOrderItem>> findAll();
}