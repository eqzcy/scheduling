package com.gdtech.scheduling.schedule.mapper;

import com.gdtech.core.base.mapper.MyBaseMapper;
import com.gdtech.scheduling.schedule.entity.ElectiveRecord;
import com.gdtech.scheduling.schedule.entity.SubjectGroupCourse;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhucy
 */
@Repository
public interface SubjectGroupCourseMapper extends MyBaseMapper<SubjectGroupCourse> {

    void truncateTmpTable();

    int batchInsertGroupCouseTmp(List<SubjectGroupCourse> list);
}
