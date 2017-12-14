package com.gdtech.scheduling.schedule.entity;

/**
 * 科目组合课程安排
 * @author zhucy
 */
public class SubjectGroupCourse {
    private String id;
    //选课活动id
    private String actId;
    //第几节课
    private Integer lesson;
    //科目编码
    private String subjectCode;
    //科目组合
    private String subjectCodeGroup;
    //学生数
    private Integer stuCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActId() {
        return actId;
    }

    public void setActId(String actId) {
        this.actId = actId;
    }

    public Integer getLesson() {
        return lesson;
    }

    public void setLesson(Integer lesson) {
        this.lesson = lesson;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectCodeGroup() {
        return subjectCodeGroup;
    }

    public void setSubjectCodeGroup(String subjectCodeGroup) {
        this.subjectCodeGroup = subjectCodeGroup;
    }

    public Integer getStuCount() {
        return stuCount;
    }

    public void setStuCount(Integer stuCount) {
        this.stuCount = stuCount;
    }
}
