package com.gdtech.scheduling.schedule.dto;

/**
 * 教师任课课程统计
 * @author zhucy
 */
public class TeachCourseStatDto {
    //科目编码
    private String subjectCode;
    //老师总数
    private Integer teacherCount;
    //课程总数
    private Integer courseCount;

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public Integer getTeacherCount() {
        return teacherCount;
    }

    public void setTeacherCount(Integer teacherCount) {
        this.teacherCount = teacherCount;
    }

    public Integer getCourseCount() {
        return courseCount;
    }

    public void setCourseCount(Integer courseCount) {
        this.courseCount = courseCount;
    }
}
