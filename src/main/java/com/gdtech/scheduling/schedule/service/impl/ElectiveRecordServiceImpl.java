package com.gdtech.scheduling.schedule.service.impl;

import com.gdtech.core.base.util.UUIDUtils;
import com.gdtech.scheduling.schedule.dto.ElectiveRecordGroupDto;
import com.gdtech.scheduling.schedule.entity.ElectiveRecord;
import com.gdtech.scheduling.schedule.enums.ElectiveGroupEnum;
import com.gdtech.scheduling.schedule.enums.SubjectCodeEnum;
import com.gdtech.scheduling.schedule.mapper.ElectiveRecordMapper;
import com.gdtech.scheduling.schedule.service.ElectiveRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ElectiveRecordServiceImpl implements ElectiveRecordService {

    @Autowired
    private ElectiveRecordMapper electiveRecordMapper;

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
    public void calElectiveGroup(String actId) {
        Map<ElectiveGroupEnum, List<String>> map = new HashMap<>();
        for(ElectiveGroupEnum electiveGroup : ElectiveGroupEnum.values()) {
            List<String> stuIdList = electiveRecordMapper.queryStuElectiveGroupList(actId, electiveGroup.getValues());
            map.put(electiveGroup, stuIdList);
        }
    }
}
