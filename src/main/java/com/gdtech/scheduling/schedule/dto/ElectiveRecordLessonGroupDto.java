package com.gdtech.scheduling.schedule.dto;

/**
 * @author zhucy
 */
public class ElectiveRecordLessonGroupDto {
    //匹配次数
    private  Integer times;
    private String subjectCode;
    private Integer stuCount;
//    private Integer lesson;
    private Integer lesson1;
    private Integer lesson2;
    private Integer lesson3;

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public Integer getStuCount() {
        return stuCount;
    }

    public void setStuCount(Integer stuCount) {
        this.stuCount = stuCount;
    }

    public Integer getLesson1() {
        return lesson1;
    }

    public void setLesson1(Integer lesson1) {
        this.lesson1 = lesson1;
    }

    public Integer getLesson2() {
        return lesson2;
    }

    public void setLesson2(Integer lesson2) {
        this.lesson2 = lesson2;
    }

    public Integer getLesson3() {
        return lesson3;
    }

    public void setLesson3(Integer lesson3) {
        this.lesson3 = lesson3;
    }
    
}
