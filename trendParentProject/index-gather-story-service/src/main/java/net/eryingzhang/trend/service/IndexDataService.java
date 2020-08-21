package net.eryingzhang.trend.service;

import java.util.ArrayList;
import java.util.HashMap;
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
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import net.eryingzhang.trend.pojo.IndexData;
import net.eryingzhang.trend.util.SpringContextUtil;

@Service
@CacheConfig(cacheNames = "index_datas")
public class IndexDataService {
	private Map<String, List<IndexData>> indexdatas = new HashMap<String, List<IndexData>>();
	@Autowired
	RestTemplate restTemplate;

	@HystrixCommand(fallbackMethod = "index_data_null")
	public List<IndexData> fetch_index_data(String code) {
		List<Map> temp = restTemplate.getForObject("http://127.0.0.1:8090/indexes/" + code +".json", List.class);
		return map2List(temp);
	}

	private List<IndexData> map2List(List<Map> temp) {
		List<IndexData> indexdatas = new ArrayList<IndexData>();
		for (Map map : temp) {
			IndexData data = new IndexData();
			data.setClosePoint(Convert.toFloat(map.get("closePoint")));
			data.setDate(map.get("date").toString());
			indexdatas.add(data);
		}
		return indexdatas;
	}

	@CacheEvict(key = "'indexData-code-'+ #p0")
	public void remove(String code) {

	}

	@Cacheable(key = "'indexData-code-'+ #p0")
	public List<IndexData> get(String code) {
		return CollUtil.toList();
	}

	@Cacheable(key = "'indexData-code-'+ #p0")
	public List<IndexData> storage(String code) {
		return indexdatas.get(code);
	}

	@Cacheable(key = "'indexData-code-'+ #p0")
	public List<IndexData> fresh(String code) {
		List<IndexData> indexdata = fetch_index_data(code);
		indexdatas.put(code, indexdata);
		System.out.println("code: " + code);
		System.out.println("indexdata count: " + indexdata.size());
		IndexDataService service = SpringContextUtil.getBean(IndexDataService.class);
		service.remove(code);
		return service.storage(code);
	}

	public List<IndexData> index_data_null() {
		IndexData data = new IndexData();
		data.setClosePoint(0);
		data.setDate(DateUtil.now());

		return CollUtil.toList(data);
	}

}
