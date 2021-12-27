package com.tabwu.changgou.goods.mapper;

import com.tabwu.changgou.pojo.Brand;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BrandMapper extends Mapper<Brand> {

    @Select("SELECT tb.* FROM `tb_brand` tb JOIN `tb_category_brand` tcb ON tb.`id` = tcb.`brand_id` AND tcb.`category_id` = #{categoryId}")
    List<Brand> findByCategoryId(Integer categoryId);
}
