package com.tabwu.changgou.goods.controller;

import com.tabwu.changgou.entity.MsgConstant;
import com.tabwu.changgou.entity.PageResult;
import com.tabwu.changgou.entity.Result;
import com.tabwu.changgou.entity.StatusCode;
import com.tabwu.changgou.goods.service.AlbumService;
import com.tabwu.changgou.pojo.Album;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/album")
@CrossOrigin
public class AlbumController {
    @Autowired
    private AlbumService albumService;

    /**
     * 添加相册
     * @param album
     * @return
     */
    @PostMapping("/addAlbum")
    public Result addAlbum(@RequestBody Album album) {
        albumService.addAlbum(album);
        return new Result(true, StatusCode.OK, MsgConstant.Album_Add_SUCCESS);
    }

    /**
     * 根据id删除相册
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result delAlbum(@PathVariable("id") Integer id) {
        albumService.delAlbum(id);
        return new Result(true,StatusCode.OK,MsgConstant.DELETE_SUCCESS);
    }

    /**
     * 更新相册
     * @param album
     * @return
     */
    @PutMapping("/update")
    public Result updateAlbum(@RequestBody Album album) {
        albumService.updateAlbum(album);
        return new Result(true,StatusCode.OK,MsgConstant.UPDATE_SUCCESS);
    }

    /**
     * 根据id查询相册
     * @param id
     * @return
     */
    @GetMapping("/findOne/{id}")
    public Result findOne(@PathVariable("id") Long id) {
        Album album = albumService.findOne(id);
        return new Result(true,StatusCode.OK,MsgConstant.QUERY_SUCCESS, album);
    }

    /**
     * 分页查询
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("/findAll/{currentPage}/{pageSize}")
    public Result findAll(@PathVariable("currentPage") Integer currentPage,@PathVariable("pageSize") Integer pageSize) {
        PageResult pageResult = albumService.findAll(currentPage, pageSize);
        return new Result(true,StatusCode.OK,MsgConstant.QUERY_SUCCESS,pageResult);
    }

    /**
     * 条件搜索 分页查询
     * @param album
     * @param currentPage
     * @param pageSize
     * @return
     */
    @PostMapping("/findAll/{currentPage}/{pageSize}")
    public Result findAll(@RequestBody Album album,@PathVariable("currentPage") Integer currentPage,@PathVariable("pageSize") Integer pageSize) {
        PageResult pageResult = albumService.findAll(currentPage, pageSize, album);
        return new Result(true,StatusCode.OK,MsgConstant.QUERY_SUCCESS,pageResult);
    }
}
