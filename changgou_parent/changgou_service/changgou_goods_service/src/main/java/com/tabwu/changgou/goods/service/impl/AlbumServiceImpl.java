package com.tabwu.changgou.goods.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tabwu.changgou.entity.PageResult;
import com.tabwu.changgou.goods.mapper.AlbumMapper;
import com.tabwu.changgou.goods.service.AlbumService;
import com.tabwu.changgou.pojo.Album;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    private AlbumMapper albumMapper;

    @Override
    public void addAlbum(Album album) {
        albumMapper.insertSelective(album);
    }

    @Override
    public void delAlbum(Integer id) {
        albumMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updateAlbum(Album album) {
        albumMapper.updateByPrimaryKeySelective(album);
    }

    @Override
    public Album findOne(Long id) {
        Album album = albumMapper.selectByPrimaryKey(id);
        return album;
    }

    @Override
    public PageResult findAll(Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage,pageSize);
        Page<Album> page = (Page<Album>) albumMapper.selectAll();
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public PageResult findAll(Integer currentPage, Integer pageSize, Album album) {
        PageHelper.startPage(currentPage,pageSize);
        Example example = createExample(album);
        Page<Album> page = (Page<Album>) albumMapper.selectByExample(example);
        return new PageResult(page.getTotal(),page.getResult());
    }


    public Example createExample(Album album) {
        Example example = new Example(Album.class);
        Example.Criteria criteria = example.createCriteria();

        if (album != null) {
            if (!StringUtils.isEmpty(album.getId())) {
                criteria.andEqualTo("id",album.getId());
            }
            if (!StringUtils.isEmpty(album.getTitle())) {
                criteria.andLike("title","%" + album.getTitle() + "%");
            }
            if (!StringUtils.isEmpty(album.getImage())) {
                criteria.andEqualTo("image",album.getImage());
            }
            if (!StringUtils.isEmpty(album.getImageItems())) {
                criteria.andEqualTo("imageItems",album.getImageItems());
            }
        }

        return example;

    }
}
