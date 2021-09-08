package com.tabwu.changgou.goods.service;

import com.github.pagehelper.Page;
import com.tabwu.changgou.entity.PageResult;
import com.tabwu.changgou.pojo.Album;

public interface AlbumService {
    void addAlbum(Album album);

    void delAlbum(Integer id);

    void updateAlbum(Album album);

    Album findOne(Integer id);

    PageResult findAll(Integer currentPage, Integer pageSize);

    PageResult findAll(Integer currentPage, Integer pageSize, Album album);
}
