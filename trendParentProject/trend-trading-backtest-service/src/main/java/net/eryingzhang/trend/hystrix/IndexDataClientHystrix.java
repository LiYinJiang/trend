package net.eryingzhang.trend.hystrix;

import java.util.List;

import org.springframework.stereotype.Component;

import cn.hutool.core.collection.CollUtil;
import net.eryingzhang.trend.client.IndexDataClient;
import net.eryingzhang.trend.pojo.IndexData;

@Component
public class IndexDataClientHystrix implements IndexDataClient {

	@Override
	public List<IndexData> get(String code) {
		IndexData data = new IndexData();
		data.setClosePoint(0);
		data.setDate("0000-00-00");
		return CollUtil.toList(data);
	}

}
