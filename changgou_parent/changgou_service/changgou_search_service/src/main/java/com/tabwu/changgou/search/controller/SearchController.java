package com.tabwu.changgou.search.controller;

import com.tabwu.changgou.entity.MsgConstant;
import com.tabwu.changgou.entity.Result;
import com.tabwu.changgou.entity.StatusCode;
import com.tabwu.changgou.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
@CrossOrigin
public class SearchController {
    @Autowired
    private SearchService searchService;


    @GetMapping("/import")
    public Result importToES() {
        searchService.importEs();
        return new Result(true, StatusCode.OK, MsgConstant.ADD_SUCCESS);
    }


}
