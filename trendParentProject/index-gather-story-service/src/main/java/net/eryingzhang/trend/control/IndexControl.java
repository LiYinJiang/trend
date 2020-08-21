package net.eryingzhang.trend.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import net.eryingzhang.trend.pojo.Index;
import net.eryingzhang.trend.service.IndexService;

@RestController
public class IndexControl {
	@Autowired
	IndexService indexService;

	@GetMapping("/remove")
	public void remove() {
		indexService.remove();

	}

	@GetMapping("/get")
	public List<Index> get() {
		return indexService.get();
	}

	@GetMapping("/fresh")
	public List<Index> fresh() {
		return indexService.fresh();
	}
}
