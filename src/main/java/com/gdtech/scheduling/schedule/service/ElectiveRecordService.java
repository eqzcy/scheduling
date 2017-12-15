package com.gdtech.scheduling.schedule.service;

import com.gdtech.scheduling.schedule.dto.ElectiveRecordGroupDto;
import com.gdtech.scheduling.schedule.entity.ElectiveRecord;

import java.util.List;


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
}
