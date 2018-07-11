package com.jacliu.test.vo;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * author : jacliu createTime : 2018-06-28 description : 定时任务初始化 version : 1.0
 */
@SuppressWarnings("restriction")
@Component
public class ScheduleJobInitddd {

	/** 日志对象 */
	private static final Logger LOG = LoggerFactory.getLogger(ScheduleJobInitddd.class);

	@PostConstruct
	public void init() {

		if (LOG.isInfoEnabled()) {
			LOG.info("init aaaa ");
		}

		if (LOG.isInfoEnabled()) {
			LOG.info("end bbbbb");
		}
	}

}
