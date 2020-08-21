package net.eryingzhang.trend.service;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import net.eryingzhang.trend.pojo.IndexData;

@Service
@CacheConfig(cacheNames = "index_datas")
public class IndexDataService {

	@Cacheable(key = "'indexData-code-'+ #p0")
	@CrossOrigin
	public List<IndexData> getData(String code) {
		IndexData data = new IndexData();
		data.setClosePoint(0);
		data.setDate(DateUtil.now());
		return CollUtil.toList(data);
	}

}
