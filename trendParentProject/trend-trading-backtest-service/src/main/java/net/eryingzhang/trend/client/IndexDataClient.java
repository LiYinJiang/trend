package net.eryingzhang.trend.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import net.eryingzhang.trend.hystrix.IndexDataClientHystrix;
import net.eryingzhang.trend.pojo.IndexData;

@FeignClient(value = "INDEX-DATA-SERVICE", fallback = IndexDataClientHystrix.class)
public interface IndexDataClient {
	@GetMapping("/data/{code}")
	List<IndexData> get(@PathVariable("code") String code);
}
