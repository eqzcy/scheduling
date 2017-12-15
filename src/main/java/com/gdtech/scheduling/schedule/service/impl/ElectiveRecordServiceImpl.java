package com.gdtech.scheduling.schedule.service.impl;

import com.gdtech.core.base.util.UUIDUtils;
import com.gdtech.scheduling.schedule.dto.ElectiveRecordGroupDto;
import com.gdtech.scheduling.schedule.dto.TeachCourseStatDto;
import com.gdtech.scheduling.schedule.entity.ElectiveRecord;
import com.gdtech.scheduling.schedule.entity.SubjectGroupCourse;
import com.gdtech.scheduling.schedule.entity.TeachClassSetting;
import com.gdtech.scheduling.schedule.enums.ElectiveGroupEnum;
import com.gdtech.scheduling.schedule.enums.SubjectCodeEnum;
import com.gdtech.scheduling.schedule.mapper.ElectiveRecordMapper;
import com.gdtech.scheduling.schedule.mapper.ScheduleTeacherMapper;
import com.gdtech.scheduling.schedule.mapper.SubjectGroupCourseMapper;
import com.gdtech.scheduling.schedule.mapper.TeachClassSettingMapper;
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

    @Autowired
    private TeachClassSettingMapper teachClassSettingMapper;

    @Autowired
    private ScheduleTeacherMapper scheduleTeacherMapper;


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
    public void calElectiveGroup(String actId, int minute) {
        /*SubjectGroupCourse delParam = new SubjectGroupCourse();
        delParam.setActId(actId);*/
        long startMillis = System.currentTimeMillis();
        //耗时
        long takeMillis = minute * 60 * 1000;
        //已用时
        long usedMillis = 0;
        //匹配次数
        int times = 1;
        subjectGroupCourseMapper.truncateTmpTable();

        TeachClassSetting teachClassSetting = new TeachClassSetting();
        teachClassSetting.setActId(actId);
        teachClassSetting = teachClassSettingMapper.selectOne(teachClassSetting);

        Map<ElectiveGroupEnum, List<String>> map = new HashMap<>();
        for(ElectiveGroupEnum electiveGroup : ElectiveGroupEnum.values()) {
            List<String> stuIdList = electiveRecordMapper.queryStuElectiveGroupList(actId, electiveGroup.getValues());
            if(!CollectionUtils.isEmpty(stuIdList)) {
                stuIdList = createCommonSubjectGroup(actId, electiveGroup, stuIdList, teachClassSetting);
                if(!CollectionUtils.isEmpty(stuIdList)) {
                    map.put(electiveGroup, stuIdList);
                }
            }
        }

        while (usedMillis < takeMillis) {
            List<SubjectGroupCourse> targetList = new ArrayList<>();
            Set<ElectiveGroupEnum> electiveGroupSet = map.keySet();
            for(ElectiveGroupEnum electiveGroup : electiveGroupSet) {
                String[] subjectCodeArr = electiveGroup.getValues();
                List<String> stuIdList = map.get(electiveGroup);
                String codeGroup = Arrays.toString(subjectCodeArr);
                codeGroup = codeGroup.substring(1, codeGroup.length() - 1);

                List<String> subjectCodeList = Arrays.asList(subjectCodeArr);
                Collections.shuffle(subjectCodeList);
                for(int i = 0; i < subjectCodeList.size(); i++) {
                    String subjectCode = subjectCodeList.get(i);
                    SubjectGroupCourse groupCourse = new SubjectGroupCourse();
//                    groupCourse.setId(UUIDUtils.genUID32());
                    groupCourse.setActId(actId);
                    groupCourse.setLesson(i + 1);
                    groupCourse.setStuCount(stuIdList.size());
                    groupCourse.setSubjectCode(subjectCode);
                    groupCourse.setSubjectCodeGroup(codeGroup);
                    groupCourse.setTimes(times);

                    targetList.add(groupCourse);
                }
            }
            subjectGroupCourseMapper.batchInsertGroupCouseTmp(targetList);
            long currentMillis = System.currentTimeMillis();
                usedMillis = currentMillis - startMillis;
//            usedMillis = 1000000000000000000l;
            times++;
        }

        List<TeachCourseStatDto> teachCourseList = scheduleTeacherMapper.getTeachCountStatList();
        List<TeachCourseStatDto> needTeachCourseList = scheduleTeacherMapper.getNeedTeachCountStatListByRecordGroup();
        Map<String, Integer> needTeachCourseMap = getTeachCourseStatMap(needTeachCourseList);
        for(TeachCourseStatDto dto: teachCourseList) {
            int teacherCount = needTeachCourseMap.get(dto.getSubjectCode());
            dto.setTeacherCount(dto.getTeacherCount() - teacherCount);
        }
        //todo
    }

    private List<String> createCommonSubjectGroup(String actId, ElectiveGroupEnum electiveGroup, List<String> stuIdList,
                                          TeachClassSetting teachClassSetting) {
        List<SubjectGroupCourse> commonSubjectGroupList = new ArrayList<>();
        int quantity = teachClassSetting.getQuantity();
        int deviation = teachClassSetting.getDeviation();
        int minQuantity = quantity - deviation;
        int maxQuantity = quantity + deviation;
        //处理学生余数
        boolean dealRemainder = Boolean.TRUE;
        //余数
        int remainder = 0;
        List<String> remainderStuList = null;
        //通用班级数
        int classSize = stuIdList.size() / minQuantity;
        if(0 == classSize) {
            return stuIdList;
        }
        float classStuSize = (float) (stuIdList.size() * 1.0 / classSize);
        if(classStuSize > maxQuantity) {
            classStuSize = quantity;
            dealRemainder = Boolean.FALSE;
        }
        remainder = stuIdList.size() % classSize;
        classStuSize = (int)Math.floor(classStuSize);
        List<List<String>> stuGroupIdList = new ArrayList<>();
        int fromIndex = 0;
        int toIndex = (int) classStuSize;
        for(int cls = 0; cls < classSize; cls++) {
            if(dealRemainder && remainder > 0) {
                toIndex += 1;
                remainder--;
            }
            List<String> subIdList = stuIdList.subList(fromIndex, toIndex);
            fromIndex = toIndex;
            toIndex = fromIndex + (int)classStuSize;
            stuGroupIdList.add(subIdList);
        }

        remainderStuList = stuIdList.subList(fromIndex, stuIdList.size());
        for(int cls = 0; cls < classSize; cls++) {
            String[] subjectCodeArr = electiveGroup.getValues();
            String codeGroup = Arrays.toString(subjectCodeArr);
            codeGroup = codeGroup.substring(1, codeGroup.length() - 1);

            List<String> curStuIdList = stuGroupIdList.get(cls);
            List<String> subjectCodeList = Arrays.asList(subjectCodeArr);
            Collections.shuffle(subjectCodeList);
            for(int i = 0; i < subjectCodeList.size(); i++) {
                String subjectCode = subjectCodeList.get(i);
                SubjectGroupCourse groupCourse = new SubjectGroupCourse();
                groupCourse.setActId(actId);
                groupCourse.setLesson(-1);
                groupCourse.setStuCount(curStuIdList.size());
                groupCourse.setSubjectCode(subjectCode);
                groupCourse.setSubjectCodeGroup(codeGroup);
                groupCourse.setTimes(-1);

                commonSubjectGroupList.add(groupCourse);
            }
        }
        subjectGroupCourseMapper.batchInsertGroupCouseTmp(commonSubjectGroupList);

        return remainderStuList;
    }

    private Map<String, Integer> getTeachCourseStatMap(List<TeachCourseStatDto> teachCourseList) {
        Map<String, Integer> teachCourseMap = new HashMap<>();
        for(TeachCourseStatDto dto : teachCourseList) {
            teachCourseMap.put(dto.getSubjectCode(), dto.getTeacherCount());
        }
        return teachCourseMap;
    }
}

