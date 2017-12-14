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
    //学生数
    private int size;

    public ElectiveRecordGroupDto() {

    }

    public ElectiveRecordGroupDto(List<SubjectCodeEnum> subjectCodeList, int size) {
        this.subjectCodeList = subjectCodeList;
        this.size = size;
    }

    public ElectiveRecordGroupDto(int size, SubjectCodeEnum... subjectCodeArray) {
        List<SubjectCodeEnum> subjectCodeList = new ArrayList<>();
        for (SubjectCodeEnum subjectCode : subjectCodeArray) {
            subjectCodeList.add(subjectCode);
        }
        this.subjectCodeList = subjectCodeList;
        this.size = size;
    }

    public List<SubjectCodeEnum> getSubjectCodeList() {
        return subjectCodeList;
    }

    public void setSubjectCodeList(List<SubjectCodeEnum> subjectCodeList) {
        this.subjectCodeList = subjectCodeList;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
