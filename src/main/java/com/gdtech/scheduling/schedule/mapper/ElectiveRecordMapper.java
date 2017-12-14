package com.gdtech.scheduling.schedule.mapper;

import com.gdtech.core.base.mapper.MyBaseMapper;
import com.gdtech.scheduling.schedule.dto.TeachCourseStatDto;
import com.gdtech.scheduling.schedule.entity.ElectiveRecord;
import com.gdtech.scheduling.schedule.enums.SubjectCodeEnum;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElectiveRecordMapper extends MyBaseMapper<ElectiveRecord> {
    List<String> queryStuElectiveGroupList(@Param("actId")String actId,
                                           @Param("subjectCodeArr")String[] subjectCodeArr);

    List<TeachCourseStatDto> getTeachCountStatList();
}
