package com.gdtech.scheduling.schedule.entity;

/**
 * @author zhucy
 */
public class SubjectGroupCourseStu {
    //科目组合课程id
    private String groupCourseId;
    //学生id
    private String stuId;

    public String getGroupCourseId() {
        return groupCourseId;
    }

    public void setGroupCourseId(String groupCourseId) {
        this.groupCourseId = groupCourseId;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }
}
