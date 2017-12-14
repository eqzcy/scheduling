package com.gdtech.scheduling.schedule.entity;

/**
 * @author zhucy
 */
public class ScheduleTeacher {

    private String id;
    //姓名
    private String name;
    //任课科目
    private String subjectCode;
    // 任课班数
    private Integer classCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public Integer getClassCount() {
        return classCount;
    }

    public void setClassCount(Integer classCount) {
        this.classCount = classCount;
    }
}
