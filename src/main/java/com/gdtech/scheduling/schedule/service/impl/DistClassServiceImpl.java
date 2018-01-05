package com.gdtech.scheduling.schedule.service.impl;

import com.gdtech.core.base.util.UUIDUtils;
import com.gdtech.scheduling.schedule.dto.ElectiveRecordLessonGroupDto;
import com.gdtech.scheduling.schedule.entity.SubjectGroupCourse;
import com.gdtech.scheduling.schedule.entity.TeachingClass;
import com.gdtech.scheduling.schedule.entity.TeachingClassSetting;
import com.gdtech.scheduling.schedule.enums.ElectiveGroupEnum;
import com.gdtech.scheduling.schedule.enums.SubjectCodeEnum;
import com.gdtech.scheduling.schedule.mapper.SubjectGroupCourseMapper;
import com.gdtech.scheduling.schedule.mapper.TeachingClassMapper;
import com.gdtech.scheduling.schedule.mapper.TeachingClassSettingMapper;
import com.gdtech.scheduling.schedule.mapper.TeachingClassStuMapper;
import com.gdtech.scheduling.schedule.service.DistClassService;
import com.gdtech.scheduling.schedule.service.ElectiveRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhucy
 */
@Service
public class DistClassServiceImpl implements DistClassService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SubjectGroupCourseMapper subjectGroupCourseMapper;

    @Autowired
    private TeachingClassMapper teachingClassMapper;

    @Autowired
    private TeachingClassStuMapper teachingClassStuMapper;
    
    @Autowired
    private TeachingClassSettingMapper teachingClassSettingMapper;

    @Autowired
    private ElectiveRecordService electiveRecordService;

    @Override
    @Transactional
    public void distTeachClass(String settingId, String actId, Integer times) {
        List<TeachingClass> teachingClassList = new ArrayList<>();
        List<ElectiveRecordLessonGroupDto> lessonGroupList = subjectGroupCourseMapper.getSubjectGroupList(actId, times);
        final int commonTimes = -1;
        List<ElectiveRecordLessonGroupDto> commonLessonGroupList = subjectGroupCourseMapper.getSubjectGroupList(actId, commonTimes);
        TeachingClass delParam  = new TeachingClass();
        delParam.setSettingId(settingId);
        teachingClassMapper.delete(delParam);
        Map<ElectiveGroupEnum, List<String>> electiveStuGroupMap = electiveRecordService.queryStuElectiveGroupMap(actId);
        Map<String, Integer> classIndexMap = new HashMap<>();

        //todo 通用班级处理
       /* for(ElectiveRecordLessonGroupDto dto : commonLessonGroupList) {
            TeachingClass teachingClass = new TeachingClass();
            teachingClass.setSettingId(settingId);
            SubjectCodeEnum subjectCodeEnum = SubjectCodeEnum.getByValue(dto.getSubjectCode());
            if (null != subjectCodeEnum) {
                teachingClass.setName(subjectCodeEnum.getName());
            }
            teachingClass.setSubjectCode(dto.getSubjectCode());
            //班级序号
            Integer classIndex = classIndexMap.get(dto.getSubjectCode());
            if(null == classIndex) {
                classIndex = 1;
                classIndexMap.put(dto.getSubjectCode(), classIndex);
            }

            for (int lesson = 1; lesson < 4; lesson++) {
                teachingClass.setLesson(lesson);
                List<List<String>> stuIdList = getStuIdList(actId, dto.getSubjectCode(), commonTimes, lesson, electiveStuGroupMap);
                for(int i = 0; i < stuIdList.size(); i++) {
                    TeachingClass targetClass = new TeachingClass();
                    BeanUtils.copyProperties(teachingClass, targetClass);
                    targetClass.setId(UUIDUtils.genUID32());
                    targetClass.setName(teachingClass.getName() + classIndex + "班");
                    classIndex++;
                    teachingClassList.add(targetClass);
                    List<String> subList = stuIdList.get(i);
                    teachingClassStuMapper.batchSave(targetClass.getId(), subList);
                }
            }
        }*/
        for(ElectiveRecordLessonGroupDto dto : lessonGroupList) {
            TeachingClass teachingClass = new TeachingClass();
            teachingClass.setSettingId(settingId);
            SubjectCodeEnum subjectCodeEnum = SubjectCodeEnum.getByValue(dto.getSubjectCode());
            if (null != subjectCodeEnum) {
                teachingClass.setName(subjectCodeEnum.getName());
            }
            teachingClass.setSubjectCode(dto.getSubjectCode());
            //班级序号
            int classIndex = 1;
            for (int lesson = 1; lesson < 4; lesson++) {
                teachingClass.setLesson(lesson);
                List<List<String>> stuIdList = getStuIdList(actId, dto.getSubjectCode(), times, lesson, electiveStuGroupMap);
                for(int i = 0; i < stuIdList.size(); i++) {
                    TeachingClass targetClass = new TeachingClass();
                    BeanUtils.copyProperties(teachingClass, targetClass);
                    targetClass.setId(UUIDUtils.genUID32());
                    targetClass.setName(teachingClass.getName() + classIndex + "班");
                    classIndex++;
                    teachingClassList.add(targetClass);
                    List<String> subList = stuIdList.get(i);
                    teachingClassStuMapper.batchSave(targetClass.getId(), subList);
                }
            }
        }

        teachingClassMapper.insertList(teachingClassList);
    }

    private List<List<String>> getStuIdList(String actId, String subjectCode, Integer times, Integer lesson,
                                      Map<ElectiveGroupEnum, List<String>> electiveStuGroupMap) {
        final String split = ",";
        List<List<String>> targetList = new ArrayList<>();
        List<String> stuIdList = new ArrayList<>();
        SubjectGroupCourse queryParam =  new SubjectGroupCourse();
        queryParam.setActId(actId);
        queryParam.setSubjectCode(subjectCode);
        queryParam.setTimes(times);
        queryParam.setLesson(lesson);
        List<SubjectGroupCourse> subjectGroupCourseList = subjectGroupCourseMapper.select(queryParam);
        for(SubjectGroupCourse subjectGroupCourse : subjectGroupCourseList) {
            ElectiveGroupEnum key = ElectiveGroupEnum.getBySubjectCodeArr(subjectGroupCourse.getSubjectCodeGroup().split(split));
            List<String> groupStuIdList = electiveStuGroupMap.get(key);
            stuIdList.addAll(groupStuIdList.subList(groupStuIdList.size() - subjectGroupCourse.getStuCount(), groupStuIdList.size()));
        }
        TeachingClassSetting settingParam = new TeachingClassSetting();
        settingParam.setActId(actId);
        TeachingClassSetting classSetting = teachingClassSettingMapper.selectOne(settingParam);
        Double classSizeDouble = Math.ceil(stuIdList.size() * 1.0 / classSetting.getMaxQuantity());
        //标准班级人数
        int standardStudentSize = (int)Math.round(stuIdList.size() / classSizeDouble);
        for(int i = 0; i < classSizeDouble.intValue(); i++) {
            List<String> subList;
            if((i+1) == classSizeDouble.intValue()) {
                subList = stuIdList.subList(i*standardStudentSize, stuIdList.size());
            } else {
                subList = stuIdList.subList(i*standardStudentSize, (i+1) * standardStudentSize);
            }

            targetList.add(subList);
        }
        return targetList;
    }
}
