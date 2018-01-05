package com.gdtech.scheduling.schedule.dto;

import com.gdtech.scheduling.schedule.enums.SubjectCodeEnum;

import java.util.ArrayList;
import java.util.List;

/**
 *  选课记录组合
 * @author zhucy
 */
public class ElectiveRecordGroupDto {

    //科目组合
    private List<SubjectCodeEnum> subjectCodeList;

    private String subjectCodeGroup;
    //学生数
    private Integer stuCount;
    //班级数
    private Integer classCount;

    public ElectiveRecordGroupDto() {

    }

    public ElectiveRecordGroupDto(List<SubjectCodeEnum> subjectCodeList, int stuCount) {
        this.subjectCodeList = subjectCodeList;
        this.stuCount = stuCount;
    }

    public ElectiveRecordGroupDto(int stuCount, SubjectCodeEnum... subjectCodeArray) {
        List<SubjectCodeEnum> subjectCodeList = new ArrayList<>();
        for (SubjectCodeEnum subjectCode : subjectCodeArray) {
            subjectCodeList.add(subjectCode);
        }
        this.subjectCodeList = subjectCodeList;
        this.stuCount = stuCount;
    }

    public List<SubjectCodeEnum> getSubjectCodeList() {
        return subjectCodeList;
    }

    public void setSubjectCodeList(List<SubjectCodeEnum> subjectCodeList) {
        this.subjectCodeList = subjectCodeList;
    }

    public Integer getStuCount() {
        return stuCount;
    }

    public void setStuCount(Integer stuCount) {
        this.stuCount = stuCount;
    }

    public String getSubjectCodeGroup() {
        if(null != subjectCodeGroup) {
            subjectCodeGroup.replaceAll(" ", "");
        }
        return subjectCodeGroup;
    }

    public void setSubjectCodeGroup(String subjectCodeGroup) {
        this.subjectCodeGroup = subjectCodeGroup;
    }

    public Integer getClassCount() {
        return classCount;
    }

    public void setClassCount(Integer classCount) {
        this.classCount = classCount;
    }
}
