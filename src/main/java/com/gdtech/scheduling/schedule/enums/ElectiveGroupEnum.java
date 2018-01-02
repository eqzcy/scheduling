package com.gdtech.scheduling.schedule.enums;

import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 选课组合
 * @author zhucy
 */
public enum ElectiveGroupEnum {
    whs(new String[] {SubjectCodeEnum.Physics.value,SubjectCodeEnum.Chemistry.value,SubjectCodeEnum.Biology.value}),   //物化生
    whz(new String[] {SubjectCodeEnum.Physics.value,SubjectCodeEnum.Chemistry.value,SubjectCodeEnum.Politics.value}),   //物化政
    whl(new String[] {SubjectCodeEnum.Physics.value,SubjectCodeEnum.Chemistry.value,SubjectCodeEnum.History.value}),   //物化历
    whd(new String[] {SubjectCodeEnum.Physics.value,SubjectCodeEnum.Chemistry.value,SubjectCodeEnum.Geography.value}),   //物化地
    wsz(new String[] {SubjectCodeEnum.Physics.value,SubjectCodeEnum.Biology.value,SubjectCodeEnum.Politics.value}),   //物生政
    wsl(new String[] {SubjectCodeEnum.Physics.value,SubjectCodeEnum.Biology.value,SubjectCodeEnum.History.value}),   //物生历
    wsd(new String[] {SubjectCodeEnum.Physics.value,SubjectCodeEnum.Biology.value,SubjectCodeEnum.Geography.value}),   //物生地
    wzl(new String[] {SubjectCodeEnum.Physics.value,SubjectCodeEnum.Politics.value,SubjectCodeEnum.History.value}),   //物政历
    wzd(new String[] {SubjectCodeEnum.Physics.value,SubjectCodeEnum.Politics.value,SubjectCodeEnum.Geography.value}),   //物政地
    wld(new String[] {SubjectCodeEnum.Physics.value,SubjectCodeEnum.History.value,SubjectCodeEnum.Geography.value}),   //物历地
    hsz(new String[] {SubjectCodeEnum.Chemistry.value,SubjectCodeEnum.Biology.value,SubjectCodeEnum.Politics.value}),   //化生政
    hsl(new String[] {SubjectCodeEnum.Chemistry.value,SubjectCodeEnum.Biology.value,SubjectCodeEnum.History.value}),   //化生历
    hsd(new String[] {SubjectCodeEnum.Chemistry.value,SubjectCodeEnum.Biology.value,SubjectCodeEnum.Geography.value}),   //化生地
    hzl(new String[] {SubjectCodeEnum.Chemistry.value,SubjectCodeEnum.Politics.value,SubjectCodeEnum.History.value}),   //化政历
    hzd(new String[] {SubjectCodeEnum.Chemistry.value,SubjectCodeEnum.Politics.value,SubjectCodeEnum.Geography.value}),   //化政地
    hld(new String[] {SubjectCodeEnum.Chemistry.value,SubjectCodeEnum.History.value,SubjectCodeEnum.Geography.value}),   //化历地
    szl(new String[] {SubjectCodeEnum.Biology.value,SubjectCodeEnum.Politics.value,SubjectCodeEnum.History.value}),   //生政历
    szd(new String[] {SubjectCodeEnum.Biology.value,SubjectCodeEnum.Politics.value,SubjectCodeEnum.Geography.value}),   //生政地
    sld(new String[] {SubjectCodeEnum.Biology.value,SubjectCodeEnum.History.value,SubjectCodeEnum.Geography.value}),   //生历地
    zld(new String[] {SubjectCodeEnum.Politics.value,SubjectCodeEnum.History.value,SubjectCodeEnum.Geography.value});   //政历地
    //科目数量
    static final int subjectCodeSize = 3;
    String[] values;

    ElectiveGroupEnum(String[] values) {
        this.values = values;
    }

    public String[] getValues() {
        return values;
    }

    public static ElectiveGroupEnum getBySubjectCodeArr(String[] subjectCodeArr) {
        if(null == subjectCodeArr || subjectCodeArr.length != subjectCodeSize) {
            return null;
        }
        Set<String> subjectCodeSet = new HashSet<>(Arrays.asList(subjectCodeArr));
        for(ElectiveGroupEnum groupEnum : ElectiveGroupEnum.values()) {
            String[] curArr = groupEnum.getValues();
            Set<String> curSet = new HashSet<>(Arrays.asList(curArr));
            curSet.removeAll(subjectCodeSet);
            if(curSet.size() == 0) {
                return groupEnum;
            }
        }
        return null;
    }
}
