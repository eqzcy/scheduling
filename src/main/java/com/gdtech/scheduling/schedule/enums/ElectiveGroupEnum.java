package com.gdtech.scheduling.schedule.enums;

/**
 * 选课组合
 * @author zhucy
 */
public enum ElectiveGroupEnum {
    whs(new String[] {SubjectCodeEnum.Physics.value,SubjectCodeEnum.Chemistry.value,SubjectCodeEnum.Biology.value}),   //物化生
    whl(new String[] {SubjectCodeEnum.Physics.value,SubjectCodeEnum.Chemistry.value,SubjectCodeEnum.History.value}),   //物化历
    whz(new String[] {SubjectCodeEnum.Physics.value,SubjectCodeEnum.Chemistry.value,SubjectCodeEnum.Politics.value}),   //物化政
    whd(new String[] {SubjectCodeEnum.Physics.value,SubjectCodeEnum.Chemistry.value,SubjectCodeEnum.Geography.value}),   //物化地
    wsl(new String[] {SubjectCodeEnum.Physics.value,SubjectCodeEnum.Biology.value,SubjectCodeEnum.History.value}),   //物生历
    wsz(new String[] {SubjectCodeEnum.Physics.value,SubjectCodeEnum.Biology.value,SubjectCodeEnum.Politics.value}),   //物生政
    wsd(new String[] {SubjectCodeEnum.Physics.value,SubjectCodeEnum.Biology.value,SubjectCodeEnum.Geography.value}),   //物生地
    wlz(new String[] {SubjectCodeEnum.Physics.value,SubjectCodeEnum.History.value,SubjectCodeEnum.Politics.value}),   //物历政
    wld(new String[] {SubjectCodeEnum.Physics.value,SubjectCodeEnum.History.value,SubjectCodeEnum.Geography.value}),   //物历地
    wzd(new String[] {SubjectCodeEnum.Physics.value,SubjectCodeEnum.Politics.value,SubjectCodeEnum.Geography.value}),   //物政地
    hsl(new String[] {SubjectCodeEnum.Chemistry.value,SubjectCodeEnum.Biology.value,SubjectCodeEnum.History.value}),   //化生历
    hsz(new String[] {SubjectCodeEnum.Chemistry.value,SubjectCodeEnum.Biology.value,SubjectCodeEnum.Politics.value}),   //化生政
    hsd(new String[] {SubjectCodeEnum.Chemistry.value,SubjectCodeEnum.Biology.value,SubjectCodeEnum.Geography.value}),   //化生地
    hlz(new String[] {SubjectCodeEnum.Chemistry.value,SubjectCodeEnum.History.value,SubjectCodeEnum.Politics.value}),   //化历政
    hld(new String[] {SubjectCodeEnum.Chemistry.value,SubjectCodeEnum.History.value,SubjectCodeEnum.Geography.value}),   //化历地
    hzd(new String[] {SubjectCodeEnum.Chemistry.value,SubjectCodeEnum.Politics.value,SubjectCodeEnum.Geography.value}),   //化政地
    slz(new String[] {SubjectCodeEnum.Biology.value,SubjectCodeEnum.History.value,SubjectCodeEnum.Politics.value}),   //生历政
    sld(new String[] {SubjectCodeEnum.Biology.value,SubjectCodeEnum.History.value,SubjectCodeEnum.Geography.value}),   //生历地
    szd(new String[] {SubjectCodeEnum.Biology.value,SubjectCodeEnum.Politics.value,SubjectCodeEnum.Geography.value}),   //生政地
    lzd(new String[] {SubjectCodeEnum.History.value,SubjectCodeEnum.Politics.value,SubjectCodeEnum.Geography.value});   //历政地

    String[] values;

    ElectiveGroupEnum(String[] values) {
        this.values = values;
    }

    public String[] getValues() {
        return values;
    }
}
