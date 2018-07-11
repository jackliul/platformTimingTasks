package com.jacliu.test.event;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * author : jacliu createTime : 2018-06-28 description : 定时任务初始化 version : 1.0
 */
@SuppressWarnings("restriction")
@Component
public class ScheduleJobInit {

	/** 日志对象 */
	private static final Logger LOG = LoggerFactory.getLogger(ScheduleJobInit.class);

	/** 定时任务service */
	// @Autowired
	// private ScheduleJobService scheduleJobService;

	/**
	 * 项目启动时初始化
	 */
	@PostConstruct
	public void init() {

		if (LOG.isInfoEnabled()) {
			LOG.info("init");
			System.out.println("aaaaaaa,aaaaaaaa,");
		}

		try {
			// scheduleJobService.initScheduleJob();
		} catch (Exception e) {
			LOG.error("初始化：： " + e.getMessage());
			System.out.println("bbbbbbbbb,bbbbbbbbbbbb,");
		}

		if (LOG.isInfoEnabled()) {
			LOG.info("end");
			System.out.println("bbbbbbbbb,bbbbbbbbbbbb,");
		}
	}

}
