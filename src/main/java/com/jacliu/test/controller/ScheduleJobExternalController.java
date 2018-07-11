package com.jacliu.test.controller;

import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jacliu.test.service.ScheduleJobService;
import com.jacliu.test.vo.ScheduleJobVo;

import cn.hutool.json.JSONUtil;

/**
 * author : jacliu createTime : 2018-06-28 description : 定时任务控制器 version : 1.0
 */
@Controller
@RequestMapping("schedule/")
public class ScheduleJobExternalController {

	@Autowired
	private ScheduleJobService scheduleJobService;

	private static final Logger LOG = LoggerFactory.getLogger(ScheduleJobExternalController.class);

	@RequestMapping(value = "list")
	public void list(ScheduleJobVo scheduleJobVo, HttpServletResponse response, PrintWriter writer) {
		List<ScheduleJobVo> scheduleJobVoList;
		String jsonStr = null;
		try {
			scheduleJobVoList = scheduleJobService.queryList(scheduleJobVo);
			jsonStr = JSONUtil.toJsonStr(scheduleJobVoList);
		} catch (Exception e) {
			LOG.error("ScheduleJobExternalController list :: " + e.getMessage());
		}
		writer.write(jsonStr);
	}

	@RequestMapping(value = "addOrUpdate")
	public void addOrUpdate(ScheduleJobVo scheduleJobVo, PrintWriter writer) {
		String result = "1";
		String jsonStr;
		try {
			if (scheduleJobVo.getScheduleJobId() == null) {
				// 生成jobName,jobGruop
				scheduleJobVo
						.setJobName(scheduleJobVo.getCompanyCode() + "_jobName_" + scheduleJobVo.getFunctionalName());
				scheduleJobVo
						.setJobGroup(scheduleJobVo.getCompanyCode() + "_jobGroup_" + scheduleJobVo.getFunctionalName());
				scheduleJobVo.setJobTrigger(
						scheduleJobVo.getCompanyCode() + "_jobTrigger_" + scheduleJobVo.getFunctionalName());
				scheduleJobVo.setGmtCreate(new Date());
				scheduleJobService.insert(scheduleJobVo);
			} else {
				scheduleJobVo.setGmtModify(new Date());
				scheduleJobService.externalUpdate(scheduleJobVo);
			}
		} catch (Exception e) {
			LOG.error("承运商新增错误：{}", e.getMessage());
			LOG.error("错误 bean：{}", scheduleJobVo.toString());
			result = "0";
		}

		jsonStr = JSONUtil.toJsonStr(result);
		writer.write(jsonStr);
	}

	@RequestMapping(value = "updateStartOrForbidden")
	public void updateStartOrForbidden(ScheduleJobVo scheduleJobVo, PrintWriter writer) {
		String result = "1";
		String jsonStr;
		try {
			scheduleJobService.updateStartOrForbidden(scheduleJobVo);
		} catch (Exception e) {
			LOG.error("承运商新增错误：{}", e.getMessage());
			LOG.error("错误 bean：{}", scheduleJobVo.toString());
			result = "0";
		}

		jsonStr = JSONUtil.toJsonStr(result);
		writer.write(jsonStr);
	}

}
