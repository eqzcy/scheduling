package com.gdtech.scheduling.schedule.service;

/**
 * 分班服务
 * @author zhucy
 */
public interface DistClassService {

    /**
     * 教学班分班
     * @param actId
     * @param times
     */
    void distTeachClass(String settingId, String actId, Integer times);
}
