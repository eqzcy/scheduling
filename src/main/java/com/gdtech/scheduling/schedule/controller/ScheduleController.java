package com.gdtech.scheduling.schedule.controller;

import com.gdtech.scheduling.schedule.entity.ScheduleSetting;
import com.gdtech.scheduling.schedule.service.SchedulingService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhucy
 */
@RestController
@RequestMapping("schedule")
public class ScheduleController {
    @Autowired
    private SchedulingService schedulingService;

    @RequestMapping(value = "getScheduleSettingList")
    public PageInfo<ScheduleSetting> getScheduleSettingList(int pageNum, int pageSize) {
        return  schedulingService.getScheduleSettingList(pageNum, pageSize);
    }
}
