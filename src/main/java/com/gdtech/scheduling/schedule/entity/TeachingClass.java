package com.gdtech.scheduling.schedule.entity;

import com.gdtech.core.base.entity.BaseEntity;

/**
 * 教学班
 * @author zhucy
 */
public class TeachingClass extends BaseEntity {
    //科目编码
    private String subjectCode;
    //分班配置id
    private String settingId;
    //教学班名称
    private String name;
    //第几节课
    private Integer lesson;

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSettingId() {
        return settingId;
    }

    public void setSettingId(String settingId) {
        this.settingId = settingId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLesson() {
        return lesson;
    }

    public void setLesson(Integer lesson) {
        this.lesson = lesson;
    }
}
