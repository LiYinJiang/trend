package net.eryingzhang.trend.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import net.eryingzhang.trend.pojo.IndexData;
import net.eryingzhang.trend.service.IndexDataService;

@RestController
public class IndexDataControl {

	@Autowired
	IndexDataService service;

	@GetMapping("/removeIndexData/{code}")
	public String remove(@PathVariable("code") String code) throws Exception {
		service.remove(code);
		return "remove code: " + code;
	}

	@GetMapping("/getIndexData/{code}")
	public List<IndexData> get(@PathVariable("code") String code) throws Exception {
		return service.get(code);
	}

	@GetMapping("/freshIndexData/{code}")
	public String fresh(@PathVariable("code") String code) throws Exception {
		service.fresh(code);
		return "refresh code: " + code;
	}
}
