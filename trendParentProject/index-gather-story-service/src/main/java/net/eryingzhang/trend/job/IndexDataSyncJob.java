package net.eryingzhang.trend.job;

import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.ctc.wstx.util.DataUtil;

import cn.hutool.core.date.DateUtil;
import net.eryingzhang.trend.pojo.Index;
import net.eryingzhang.trend.service.IndexDataService;
import net.eryingzhang.trend.service.IndexService;

public class IndexDataSyncJob extends QuartzJobBean {
	@Autowired
	private IndexService indexService;

	@Autowired
	private IndexDataService indexDataService;

	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		System.out.println("quartz start: " + DateUtil.now());
		List<Index> indexes = indexService.fresh();
		for (Index index : indexes) {
			indexDataService.fresh(index.getCode());
		}

		System.out.println("quartz end: " + DateUtil.now());
	}

}
