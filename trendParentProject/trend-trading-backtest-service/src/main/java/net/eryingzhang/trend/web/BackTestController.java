package net.eryingzhang.trend.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import net.eryingzhang.trend.pojo.IndexData;
import net.eryingzhang.trend.service.BackTestService;

@RestController
public class BackTestController {

	@Autowired
	BackTestService backTestService;

	@GetMapping("/simulate/{code}/{startDate}/{endDate}")
	@CrossOrigin
	public Map<String, Object> backTest(@PathVariable("code") String code, @PathVariable("startDate") String startDate,
			@PathVariable("endDate") String endDate) throws Exception {
		List<IndexData> allIndexDatas = backTestService.listIndexData(code);
		IndexData startData = allIndexDatas.get(0);
		IndexData endData = allIndexDatas.get(allIndexDatas.size() - 1);
		allIndexDatas = filterRangeIndexData(allIndexDatas, startDate, endDate);

		Map<String, Object> result = new HashMap<>();
		result.put("indexDatas", allIndexDatas);
		result.put("startDate", startData);
		result.put("endDate", endData);

		return result;
	}

	private List<IndexData> filterRangeIndexData(List<IndexData> allIndexDatas, String startDate, String endDate) {
		if (StrUtil.isBlankOrUndefined(startDate) || StrUtil.isBlankOrUndefined(startDate))
			return allIndexDatas;
		List<IndexData> temp = new ArrayList<IndexData>();
		Date start = DateUtil.parse(startDate);
		Date end = DateUtil.parse(endDate);

		for (IndexData indexData : allIndexDatas) {
			Date current = DateUtil.parse(indexData.getDate());
			if (current.after(start) && end.after(current)) {
				temp.add(indexData);
			}
		}

		return temp;
	}

}
