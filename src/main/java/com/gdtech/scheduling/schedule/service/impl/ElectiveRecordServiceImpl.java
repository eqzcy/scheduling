package com.gdtech.scheduling.schedule.service.impl;

import com.gdtech.core.base.util.UUIDUtils;
import com.gdtech.scheduling.schedule.dto.ElectiveRecordGroupDto;
import com.gdtech.scheduling.schedule.dto.ElectiveRecordLessonGroupDto;
import com.gdtech.scheduling.schedule.dto.TeachCourseStatDto;
import com.gdtech.scheduling.schedule.entity.ElectiveRecord;
import com.gdtech.scheduling.schedule.entity.SubjectGroupCourse;
import com.gdtech.scheduling.schedule.entity.TeachingClassSetting;
import com.gdtech.scheduling.schedule.enums.ElectiveGroupEnum;
import com.gdtech.scheduling.schedule.enums.SubjectCodeEnum;
import com.gdtech.scheduling.schedule.mapper.ElectiveRecordMapper;
import com.gdtech.scheduling.schedule.mapper.ScheduleTeacherMapper;
import com.gdtech.scheduling.schedule.mapper.SubjectGroupCourseMapper;
import com.gdtech.scheduling.schedule.mapper.TeachingClassSettingMapper;
import com.gdtech.scheduling.schedule.service.ElectiveRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.util.*;

@Service
public class ElectiveRecordServiceImpl implements ElectiveRecordService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ElectiveRecordMapper electiveRecordMapper;

    @Autowired
    private SubjectGroupCourseMapper subjectGroupCourseMapper;

    @Autowired
    private TeachingClassSettingMapper teachClassSettingMapper;

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
            int size = group.getStuCount();
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

        TeachingClassSetting teachClassSetting = new TeachingClassSetting();
        teachClassSetting.setActId(actId);
        teachClassSetting = teachClassSettingMapper.selectOne(teachClassSetting);

        Map<ElectiveGroupEnum, List<String>> map = new HashMap<>();
        Map<ElectiveGroupEnum, List<String>> stuElectiveGroupMap  = queryStuElectiveGroupMap(actId);
        for(ElectiveGroupEnum electiveGroup : ElectiveGroupEnum.values()) {
            List<String> stuIdList = stuElectiveGroupMap.get(electiveGroup);
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
                //防止科目序号被打乱
                List<String> subjectCodeList = new ArrayList<>(Arrays.asList(subjectCodeArr));
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
            subjectGroupCourseMapper.batchInsertGroupCourseTmp(targetList);
            long currentMillis = System.currentTimeMillis();
            usedMillis = currentMillis - startMillis;
//            usedMillis = 1000000000000000000l;
            times++;
        }


    }

    @Override
    public void dealPassGroupCourse(String actId) {
        TeachingClassSetting teachClassSetting = new TeachingClassSetting();
        teachClassSetting.setActId(actId);
        teachClassSetting = teachClassSettingMapper.selectOne(teachClassSetting);
        Set<Integer> unPassTimesSet = new HashSet<>();
        Set<Integer> passTimesSet = new HashSet<>();
        Map<String, Integer> teachTeachCourseMap = genTeachCourseStatMap(teachClassSetting);
        List<ElectiveRecordLessonGroupDto> lessonGroupList = subjectGroupCourseMapper.getAllSubjectGroupCourseTmpList();
        List<ElectiveRecordLessonGroupDto> firstSubjectGroupList = subjectGroupCourseMapper.getSubjectGroupList(actId, 1);
        Map<String, Integer> classSizeMap = getSubjectClassSizeMap(firstSubjectGroupList);
        passTimesSet.add(-1);
        for(ElectiveRecordLessonGroupDto groupDto : lessonGroupList) {
            if(unPassTimesSet.contains(groupDto.getTimes())) {
                continue;
            }
            if(!passTimesSet.contains(groupDto.getTimes())) {
                passTimesSet.add(groupDto.getTimes());
            }
            int classSize = classSizeMap.get(groupDto.getSubjectCode());
            /*Double classSize = Math.ceil(dto.getStuCount() / 55.0);
            map.put(dto.getSubjectCode(), classSize.intValue());*/
            Double size1 = Math.ceil(groupDto.getLesson1() / 55.0);
            int class1Size = size1.intValue();

            Double size2 = Math.ceil(groupDto.getLesson2() / 55.0);
            int class2Size = size2.intValue();

            Double size3 = Math.ceil(groupDto.getLesson3() / 55.0);
            int class3Size = size3.intValue();

            int totalClassSize = class1Size + class2Size + class3Size;
            if(classSize < totalClassSize) {
                unPassTimesSet.add(groupDto.getTimes());
            }

        }

        log.info("noPassList: {}", Arrays.toString(unPassTimesSet.toArray()));

        SubjectGroupCourse delGroupCourse = new SubjectGroupCourse();
        delGroupCourse.setActId(actId);
        subjectGroupCourseMapper.delete(delGroupCourse);
        passTimesSet.removeAll(unPassTimesSet);
        subjectGroupCourseMapper.batchSaveSubjectGroupCourse(passTimesSet);
    }

    @Override
    public void dealCommonsGroupCourseStu(String actId) {
        List<ElectiveRecordGroupDto> recordGroupList = subjectGroupCourseMapper.getAutoGroupList(actId);
        for(ElectiveRecordGroupDto recordGroup : recordGroupList) {
            String subjectGroup = recordGroup.getSubjectCodeGroup();
            String[] subjectGroupArr = subjectGroup.split(",");
            List<String> stuIdList = electiveRecordMapper.queryStuElectiveGroupList(actId, subjectGroupArr);
            //todo 待处理学生数据
        }

    }

    @Override
    public List<ElectiveRecordGroupDto> getAutoGroupList(String actId) {
        return subjectGroupCourseMapper.getAutoGroupList(actId);
    }

    @Override
    public List<List<ElectiveRecordLessonGroupDto>> getElectiveStatList(String actId) {
        List<List<ElectiveRecordLessonGroupDto>> targetList = new ArrayList<>();
        List<ElectiveRecordLessonGroupDto> list = subjectGroupCourseMapper.getElectiveRecordLessonGroupList(actId);
        Map<String, List<ElectiveRecordLessonGroupDto>> subjectMap = new HashMap<>();
        Integer currTimes = -1;
        List<ElectiveRecordLessonGroupDto> subList = null;
        for(ElectiveRecordLessonGroupDto dto : list) {
            if(!dto.getTimes().equals(currTimes)) {
                currTimes = dto.getTimes();
                subList = new ArrayList<>();
                subList.add(dto);
                targetList.add(subList);
            } else {
                subList.add(dto);
            }
        }
        return targetList;
    }

    @Override
    public Map<ElectiveGroupEnum, List<String>> queryStuElectiveGroupMap(String actId) {
        Map<ElectiveGroupEnum, List<String>> map = new HashMap<>();
        for(ElectiveGroupEnum electiveGroup : ElectiveGroupEnum.values()) {
            List<String> stuIdList = electiveRecordMapper.queryStuElectiveGroupList(actId, electiveGroup.getValues());
            if(!CollectionUtils.isEmpty(stuIdList)) {
                map.put(electiveGroup, stuIdList);
            }
        }
        return map;
    }

    private Map<String, Integer> getSubjectClassSizeMap(List<ElectiveRecordLessonGroupDto> subjectGroupList) {
        Map<String, Integer> map = new HashMap<>();
        for(ElectiveRecordLessonGroupDto dto : subjectGroupList) {
            Double classSize = Math.ceil(dto.getStuCount() / 45.0);
            map.put(dto.getSubjectCode(), classSize.intValue());
        }

        return map;
    }
    private List<String> createCommonSubjectGroup(String actId, ElectiveGroupEnum electiveGroup, List<String> stuIdList,
                                                  TeachingClassSetting teachClassSetting) {
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
            //防止科目序号被打乱
            List<String> subjectCodeList = new ArrayList<>(Arrays.asList(subjectCodeArr));
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
        subjectGroupCourseMapper.batchInsertGroupCourseTmp(commonSubjectGroupList);

        return remainderStuList;
    }

    private Map<String, Integer> genTeachCourseStatMap(TeachingClassSetting teachClassSetting) {
        //科目最大学生数 key: subjectCode, count: 学生数
        Map<String, Integer> teachCourseMap = new HashMap<>();
        List<TeachCourseStatDto> teachCourseList = scheduleTeacherMapper.getTeachCountStatList();
        List<TeachCourseStatDto> needTeachCourseList = scheduleTeacherMapper.getNeedTeachCountStatListByRecordGroup();
        int maxQuantity = teachClassSetting.getQuantity() + teachClassSetting.getDeviation();

        Map<String, Integer> needTeachCourseMap = new HashMap<>();
        for(TeachCourseStatDto dto : needTeachCourseList) {
            needTeachCourseMap.put(dto.getSubjectCode(), dto.getTeacherCount());
        }

        for(TeachCourseStatDto dto: teachCourseList) {
            int teacherCount = needTeachCourseMap.get(dto.getSubjectCode());
            int maxStuCount = maxQuantity * (dto.getTeacherCount() - teacherCount);
            teachCourseMap.put(dto.getSubjectCode(), maxStuCount);
        }
        return teachCourseMap;
    }
}

