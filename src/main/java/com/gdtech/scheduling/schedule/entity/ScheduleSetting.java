package com.gdtech.scheduling.schedule.entity;

import com.gdtech.core.base.entity.BaseEntity;

/**
 * 排课配置
 * @author zhucy
 */
public class ScheduleSetting extends BaseEntity {
    //名称
    private String name;
    //每周天数
    private Integer days;
    //每天课程数
    private Integer lessons;
    //教学班分班配置id
    private String teachingClassSettingId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Integer getLessons() {
        return lessons;
    }

    public void setLessons(Integer lessons) {
        this.lessons = lessons;
    }

    public String getTeachingClassSettingId() {
        return teachingClassSettingId;
    }

    public void setTeachingClassSettingId(String teachingClassSettingId) {
        this.teachingClassSettingId = teachingClassSettingId;
    }
}
