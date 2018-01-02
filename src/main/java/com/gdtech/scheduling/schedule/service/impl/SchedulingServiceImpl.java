package com.gdtech.scheduling.schedule.service.impl;

import com.gdtech.core.base.util.UUIDUtils;
import com.gdtech.scheduling.schedule.entity.ScheduleTeacher;
import com.gdtech.scheduling.schedule.entity.TeachingClass;
import com.gdtech.scheduling.schedule.entity.ScheduleSetting;
import com.gdtech.scheduling.schedule.entity.Timetable;
import com.gdtech.scheduling.schedule.enums.ClassTypeEnum;
import com.gdtech.scheduling.schedule.mapper.ScheduleTeacherMapper;
import com.gdtech.scheduling.schedule.mapper.TeachingClassMapper;
import com.gdtech.scheduling.schedule.mapper.ScheduleSettingMapper;
import com.gdtech.scheduling.schedule.mapper.TimetableMapper;
import com.gdtech.scheduling.schedule.service.SchedulingService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class SchedulingServiceImpl implements SchedulingService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ScheduleSettingMapper scheduleSettingMapper;

    @Autowired
    private TeachingClassMapper teachingClassMapper;

    @Autowired
    private ScheduleTeacherMapper scheduleTeacherMapper;

    @Autowired
    private TimetableMapper timetableMapper;

    @Override
    @Transactional
    public void scheduleTeachingClass(String id) {
        ScheduleSetting settingParam  = new ScheduleSetting();
        settingParam.setId(id);
        ScheduleSetting scheduleSetting = scheduleSettingMapper.selectOne(settingParam);
        TeachingClass teachingClassParam = new TeachingClass();
        teachingClassParam.setSettingId(scheduleSetting.getTeachingClassSettingId());
        List<TeachingClass> teachingClassList = teachingClassMapper.select(teachingClassParam);
        List<ScheduleTeacher> teacherList = scheduleTeacherMapper.selectAll();
        Map<String, List<ScheduleTeacher>> teacherMap = getScheduleTeacherMap(teacherList);
        Timetable delParam = new Timetable();
        delParam.setScheduleSettingId(id);
        timetableMapper.delete(delParam);
//        final Integer days = 5; //一周5天可
//        final Integer lessons = 3;//一天3节课
        List<Timetable> timetableList = new ArrayList<>();
        for(TeachingClass teachingClass : teachingClassList) {
            for(int day = 1; day <= scheduleSetting.getDays(); day++) {
                for(int lesson = 1; lesson <= scheduleSetting.getLessons(); lesson++) {
                    Timetable timetable = new Timetable();
                    timetable.setId(UUIDUtils.genUID32());
                    timetable.setScheduleSettingId(id);
                    timetable.setClassId(teachingClass.getId());
                    timetable.setClassType(ClassTypeEnum.Teaching);
                    timetable.setDay(day);
                    timetable.setLesson(lesson);

                    timetableList.add(timetable);
                }
            }
        }

        timetableMapper.insertList(timetableList);
    }

    @Override
    public PageInfo<ScheduleSetting> getScheduleSettingList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ScheduleSetting> list = scheduleSettingMapper.selectAll();

        return new PageInfo<>(list);
    }

    private Map<String, List<ScheduleTeacher>> getScheduleTeacherMap(List<ScheduleTeacher> teacherList) {
        Map<String, List<ScheduleTeacher>> teacherMap = new HashMap<>();
        for(ScheduleTeacher teacher : teacherList) {
            List<ScheduleTeacher> subjectTeacherList = teacherMap.get(teacher.getSubjectCode());
            if(null == subjectTeacherList) {
                subjectTeacherList = new ArrayList<>();
                teacherMap.put(teacher.getSubjectCode(), subjectTeacherList);
            } else {
                subjectTeacherList.add(teacher);
            }
        }

        return teacherMap;
    }
}
