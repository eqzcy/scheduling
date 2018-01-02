package com.gdtech.scheduling.schedule.entity;

import java.io.Serializable;

/**
 * 教学班学生中间表
 * @author zhucy
 */
public class TeachingClassStu implements Serializable {
    //教学班id
    private String classId;
    //学生id
    private String stuId;

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }
}
