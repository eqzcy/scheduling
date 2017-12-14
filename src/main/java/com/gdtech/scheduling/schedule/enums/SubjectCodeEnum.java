package com.gdtech.scheduling.schedule.enums;

/**
 *
 */
/**
 * 科目编码枚举
 * @author zhucy
 */
public enum SubjectCodeEnum {
    Chinese("01","语文"), //语文
    Math("02","数学"),//数学
    English("03","英语"),//英语
    Politics("04","政治"),//政治
    Physics("05","物理"),//物理
    Chemistry("06","化学"),//化学
    History("07","历史"),//历史
    Geography("08","地理"),//地理
    Biology("09","生物"),//生物
    Sport("21","体育"),//体育
    Art("22","艺术");//艺术

    String value;
    String name;

    SubjectCodeEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
