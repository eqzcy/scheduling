package com.gdtech.scheduling.schedule.entity;

import com.gdtech.scheduling.schedule.enums.SubjectCodeEnum;

import java.sql.Timestamp;
import javax.persistence.*;

/**
 * 选课记录
 * @author zhucy
 */
public class ElectiveRecord {
//    @Id
//    @Column(name = "Id")
    private String id;

    //选课活动id
    private String actId;
    //学生id
    private String stuId;
    //科目编码
    private String subjectCode;
    //创建时间
    private Timestamp createTime;
    //创建时间
    private Timestamp updateTime;

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

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
}
