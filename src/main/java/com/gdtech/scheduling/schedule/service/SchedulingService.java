package com.gdtech.scheduling.schedule.service;

import com.gdtech.scheduling.schedule.entity.ScheduleSetting;
import com.github.pagehelper.PageInfo;

/**
 * 排课服务
 * @author zhucy
 */
public interface SchedulingService {

    /**
     * 教学班排班
     * @param id
     */
    void scheduleTeachingClass(String id);

    PageInfo<ScheduleSetting> getScheduleSettingList(int pageNum, int pageSize);
}
