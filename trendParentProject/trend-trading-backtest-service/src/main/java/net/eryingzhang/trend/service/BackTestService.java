package net.eryingzhang.trend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.eryingzhang.trend.client.IndexDataClient;
import net.eryingzhang.trend.pojo.IndexData;

@Service
public class BackTestService {
	@Autowired
	IndexDataClient client;

	public List<IndexData> listIndexData(String code) {
		List<IndexData> datas = client.get(code);

		for (IndexData indexData : datas) {
			System.out.println("date: " + indexData.getDate());
		}
		return datas;
	}

}
