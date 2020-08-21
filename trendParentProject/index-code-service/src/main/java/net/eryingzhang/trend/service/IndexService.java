package net.eryingzhang.trend.service;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import cn.hutool.core.collection.CollUtil;
import net.eryingzhang.trend.pojo.Index;

@Service
@CacheConfig(cacheNames="indexes")
public class IndexService {
	private List<Index> indexes;

    @Cacheable(key="'all_codes'")
    public List<Index> get(){
    	Index index = new Index();
    	index.setName("邦德把他们都消灭了");
    	index.setCode("004040");
    	return CollUtil.toList(index);
    }
}