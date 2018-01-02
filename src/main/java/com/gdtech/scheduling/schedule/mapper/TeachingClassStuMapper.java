package com.gdtech.scheduling.schedule.mapper;

import com.gdtech.core.base.mapper.MyBaseMapper;
import com.gdtech.scheduling.schedule.entity.TeachingClassStu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhucy
 */
@Repository
public interface TeachingClassStuMapper extends MyBaseMapper<TeachingClassStu> {
    /**
     * 批量保存教学班学生
     * @param classId
     * @param stuIdList
     */
    void batchSave(@Param("classId")String classId, @Param("stuIdList") List<String> stuIdList);
}
