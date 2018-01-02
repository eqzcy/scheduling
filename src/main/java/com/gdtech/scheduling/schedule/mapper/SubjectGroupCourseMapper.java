package com.gdtech.scheduling.schedule.mapper;

import com.gdtech.core.base.mapper.MyBaseMapper;
import com.gdtech.scheduling.schedule.dto.ElectiveRecordGroupDto;
import com.gdtech.scheduling.schedule.dto.ElectiveRecordLessonGroupDto;
import com.gdtech.scheduling.schedule.entity.SubjectGroupCourse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author zhucy
 */
@Repository
public interface SubjectGroupCourseMapper extends MyBaseMapper<SubjectGroupCourse> {

    void truncateTmpTable();

    int batchInsertGroupCourseTmp(List<SubjectGroupCourse> list);

    List<ElectiveRecordLessonGroupDto> getSubjectGroupList(@Param("actId")String actId, @Param("times") Integer times);

    List<ElectiveRecordLessonGroupDto> getAllSubjectGroupCourseTmpList();

    void batchSaveSubjectGroupCourse(@Param("passTimesSet")Set<Integer> passTimesSet);

    List<ElectiveRecordGroupDto> getAutoGroupList(@Param("actId")String actId);

    List<ElectiveRecordLessonGroupDto> getElectiveRecordLessonGroupList(@Param("actId")String actId);

    List<SubjectGroupCourse> queryList(@Param("subjectGroupCourse") SubjectGroupCourse subjectGroupCourse);
}
