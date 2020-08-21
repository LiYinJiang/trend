package net.eryingzhang.trend.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import net.eryingzhang.trend.config.IpConfiguration;
import net.eryingzhang.trend.pojo.IndexData;
import net.eryingzhang.trend.service.IndexDataService;

@RestController
public class IndexDataControl {

	@Autowired
	IndexDataService indexDataService;
	@Autowired
	IpConfiguration ipConfiguration;

	@GetMapping("/data/{code}")
	public List<IndexData> getData(@PathVariable("code") String code) {
		System.out.println("current instance is :" + ipConfiguration.getServicePort());
		return indexDataService.getData(code);
	}
}
