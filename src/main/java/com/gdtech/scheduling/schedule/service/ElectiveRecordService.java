package com.gdtech.scheduling.schedule.service;

import com.gdtech.scheduling.schedule.dto.ElectiveRecordGroupDto;
import com.gdtech.scheduling.schedule.dto.ElectiveRecordLessonGroupDto;
import com.gdtech.scheduling.schedule.entity.ElectiveRecord;
import com.gdtech.scheduling.schedule.enums.ElectiveGroupEnum;

import java.util.List;
import java.util.Map;


public interface ElectiveRecordService {

    /**
     * 获取所有选课记录
     * @return
     */
    List<ElectiveRecord> getAllElectiveRecordList();

    /**
     * 模拟学生选课组合数
     * @param groupList
     */
    int modStuElectiveGroup(String actId, List<ElectiveRecordGroupDto> groupList);

    /**
     * 计算最优选修组合
     * @param actId
     * @param minute 计算时间(分钟)
     */
    void calElectiveGroup(String actId, int minute);

    /**
     * 处理通过验证的科目组合
     * @param actId
     */
    void dealPassGroupCourse(String actId);

    /**
     * 处理通用排课学生
     * @param actId
     */
    void dealCommonsGroupCourseStu(String actId);

    /**
     * 获取自由排课列表
     * @param actId
     * @return
     */
    List<ElectiveRecordGroupDto> getAutoGroupList(String actId);
    /**
     * 获取分组统计组合
     * @return
     */
    List<List<ElectiveRecordLessonGroupDto>> getElectiveStatList(String actId);

    /**
     * 获取选课组合学生列表map
     * @param actId
     * @return
     */
    Map<ElectiveGroupEnum, List<String>> queryStuElectiveGroupMap(String actId);
}
