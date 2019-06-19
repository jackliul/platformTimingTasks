package com.jacliu.test.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jacliu.test.quartz.FaultJobsTasks;
import com.jacliu.test.service.ScheduleJobService;

@Controller
public class SFaultJobsController {

	private final Logger logger = LoggerFactory.getLogger(SFaultJobsController.class);

	@Autowired
	private FaultJobsTasks faultJobsTasks;

	@Autowired
	private ScheduleJobService scheduleJobService;

	@RequestMapping(value = "findAndUpdateFaultJobsRecord", method = RequestMethod.GET)
	@ResponseBody
	public String findAndUpdateFaultJobsRecord() {
		try {
			faultJobsTasks.findAndUpdateFaultJobsRecord();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return "findAndUpdateFaultJobsRecord";
	}

	@RequestMapping(value = "delFaultJobsRecord", method = RequestMethod.GET)
	@ResponseBody
	public String delFaultJobsRecord(String taskUrl) {
		try {
			scheduleJobService.delFaultJobsRecord(taskUrl);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return "delFaultJobsRecord";
	}
}