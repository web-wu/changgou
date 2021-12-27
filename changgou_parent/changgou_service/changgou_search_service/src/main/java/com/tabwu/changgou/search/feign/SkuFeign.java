package com.tabwu.changgou.search.feign;

import com.tabwu.changgou.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("goods-service")
@RequestMapping("/sku")
public interface SkuFeign {

    @GetMapping("/findSkuByStatus/{status}")
    public Result findSkuByStatus(@PathVariable("status") String status);
}
