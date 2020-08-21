package net.eryingzhang.trend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import net.eryingzhang.trend.pojo.Index;
import net.eryingzhang.trend.util.SpringContextUtil;

@Service
@CacheConfig(cacheNames = "indexes")
public class IndexService {
	List<Index> indexes;

	@Autowired
	RestTemplate restTemplate;

	public List<Index> fetch_from_third_data() {
		List<Map> temp = restTemplate.getForObject("http://127.0.0.1:8090/indexes/codes.json", List.class);
		return map2List(temp);
	}

	private List<Index> map2List(List<Map> temp) {
		List<Index> indexes = new ArrayList<>();
		for (Map map : temp) {
			String code = map.get("code").toString();
			String name = map.get("name").toString();
			Index index = new Index();
			index.setCode(code);
			index.setName(name);
			indexes.add(index);
		}

		return indexes;
	}

	public List<Index> third_part_not_connected() {
		System.err.println("woo, third part notconnected!");
		Index index = new Index();
		index.setCode("007");
		index.setName("数据被邦德吃掉了");
		return CollectionUtil.toList(index);
	}

	@CacheEvict(allEntries = true)
	public void remove() {

	}

	@Cacheable(key = "'all_codes'")
	public List<Index> get() {
		return CollUtil.toList();
	}

	@Cacheable(key = "'all_codes'")
	public List<Index> store() {
		return indexes;
	}

	@HystrixCommand(fallbackMethod = "third_part_not_connected")
	public List<Index> fresh() {
		indexes = fetch_from_third_data();
		IndexService service = SpringContextUtil.getBean(IndexService.class);
		service.remove();
		service.store();
		return indexes;
	}
}
