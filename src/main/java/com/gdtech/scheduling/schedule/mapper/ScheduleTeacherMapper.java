package com.gdtech.scheduling.schedule.mapper;

import com.gdtech.core.base.mapper.MyBaseMapper;
import com.gdtech.scheduling.schedule.dto.TeachCourseStatDto;
import com.gdtech.scheduling.schedule.entity.ScheduleTeacher;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhucy
 */
@Repository
public interface ScheduleTeacherMapper extends MyBaseMapper<ScheduleTeacher> {

    /**
     * 教师可用数统计
     * @return
     */
    List<TeachCourseStatDto> getTeachCountStatList();

    /**
     * 教师必须用数统计(依据选课组合)
     * @return
     */
    List<TeachCourseStatDto> getNeedTeachCountStatListByRecordGroup();
}
