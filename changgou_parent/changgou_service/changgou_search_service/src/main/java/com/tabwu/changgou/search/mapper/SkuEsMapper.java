package com.tabwu.changgou.search.mapper;

import com.tabwu.changgou.search.entity.SkuInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkuEsMapper extends ElasticsearchRepository<SkuInfo,Long> {
}
