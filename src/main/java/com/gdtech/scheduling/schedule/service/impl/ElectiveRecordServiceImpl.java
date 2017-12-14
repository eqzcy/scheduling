package com.gdtech.scheduling.schedule.service.impl;

import com.gdtech.core.base.util.UUIDUtils;
import com.gdtech.scheduling.schedule.dto.ElectiveRecordGroupDto;
import com.gdtech.scheduling.schedule.entity.ElectiveRecord;
import com.gdtech.scheduling.schedule.entity.SubjectGroupCourse;
import com.gdtech.scheduling.schedule.enums.ElectiveGroupEnum;
import com.gdtech.scheduling.schedule.enums.SubjectCodeEnum;
import com.gdtech.scheduling.schedule.mapper.ElectiveRecordMapper;
import com.gdtech.scheduling.schedule.mapper.SubjectGroupCourseMapper;
import com.gdtech.scheduling.schedule.service.ElectiveRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.util.*;

@Service
public class ElectiveRecordServiceImpl implements ElectiveRecordService {

    @Autowired
    private ElectiveRecordMapper electiveRecordMapper;

    @Autowired
    private SubjectGroupCourseMapper subjectGroupCourseMapper;

    @Override
    public List<ElectiveRecord> getAllElectiveRecordList() {
        List<ElectiveRecord> list = electiveRecordMapper.selectAll();
        return list;
    }

    @Override
    @Transactional
    public int modStuElectiveGroup(String actId, List<ElectiveRecordGroupDto> groupList) {
        ElectiveRecord delParam = new ElectiveRecord();
        delParam.setActId(actId);
        electiveRecordMapper.delete(delParam);
        Timestamp curTime = new Timestamp(System.currentTimeMillis());
        List<ElectiveRecord> insertList = new ArrayList<>();
        for(ElectiveRecordGroupDto group : groupList) {
            List<SubjectCodeEnum> subjectList = group.getSubjectCodeList();
            int size = group.getSize();
            StringBuilder stuIdPre = new StringBuilder();
            for(SubjectCodeEnum subjectCode : subjectList) {
                stuIdPre.append(subjectCode.getValue()).append("_");
            }
            for(int i = 0; i < size; i++) {
                for(SubjectCodeEnum subjectCode : subjectList) {
                    ElectiveRecord record = new ElectiveRecord();
                    record.setId(UUIDUtils.genUID32());
                    record.setActId(actId);
                    record.setStuId(stuIdPre.toString() + i);
                    record.setSubjectCode(subjectCode.getValue());
                    record.setCreateTime(curTime);

                    insertList.add(record);
                }
            }
        }

        return electiveRecordMapper.insertList(insertList);
    }

    @Override
    @Transactional
    public void calElectiveGroup(String actId) {
        Map<ElectiveGroupEnum, List<String>> map = new HashMap<>();
        for(ElectiveGroupEnum electiveGroup : ElectiveGroupEnum.values()) {
            List<String> stuIdList = electiveRecordMapper.queryStuElectiveGroupList(actId, electiveGroup.getValues());
            if(!CollectionUtils.isEmpty(stuIdList)) {
                map.put(electiveGroup, stuIdList);
            }
        }
        List<SubjectGroupCourse> targetList = new ArrayList<>();
        Set<ElectiveGroupEnum> electiveGroupSet = map.keySet();
        for(ElectiveGroupEnum electiveGroup : electiveGroupSet) {
            String[] subjectCodeArr = electiveGroup.getValues();
            List<String> stuIdList = map.get(electiveGroup);

            for(int i = 0; i < subjectCodeArr.length; i++) {
                String subjectCode = subjectCodeArr[i];
                SubjectGroupCourse groupCourse = new SubjectGroupCourse();
                groupCourse.setId(UUIDUtils.genUID());
                groupCourse.setActId(actId);
                groupCourse.setLesson(i + 1);
                groupCourse.setStuCount(stuIdList.size());
                groupCourse.setSubjectCode(subjectCode);
                groupCourse.setSubjectCodeGroup(subjectCodeArr.toString());

                targetList.add(groupCourse);
            }
        }
        SubjectGroupCourse delParam = new SubjectGroupCourse();
        delParam.setActId(actId);
        subjectGroupCourseMapper.delete(delParam);

        subjectGroupCourseMapper.insertList(targetList);
    }
}
