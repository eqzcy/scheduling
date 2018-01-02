package com.gdtech.scheduling.schedule.entity;

import com.gdtech.core.base.entity.BaseEntity;
import com.gdtech.scheduling.schedule.enums.ClassTypeEnum;

/**
 * 课表
 * @author zhucy
 */
public class Timetable extends BaseEntity {
    //排课配置id
    private String scheduleSettingId;
    //类型
    private ClassTypeEnum classType;
    //班级id
    private String classId;
    //星期几
    private Integer day;
    //第几节课
    private Integer lesson;

    public String getScheduleSettingId() {
        return scheduleSettingId;
    }

    public void setScheduleSettingId(String scheduleSettingId) {
        this.scheduleSettingId = scheduleSettingId;
    }

    public ClassTypeEnum getClassType() {
        return classType;
    }

    public void setClassType(ClassTypeEnum classType) {
        this.classType = classType;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getLesson() {
        return lesson;
    }

    public void setLesson(Integer lesson) {
        this.lesson = lesson;
    }
}
