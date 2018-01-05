package com.gdtech.scheduling.schedule.controller;

import com.gdtech.scheduling.schedule.service.DistClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 分班controller
 * @author zhucy
 */
@RestController
@RequestMapping("distClass")
public class DistClassController {
    @Autowired
    private DistClassService distClassService;

    @RequestMapping("distTeachClass")
    public void distTeachClass(String settingId, String actId, Integer times) {
        distClassService.distTeachClass(settingId, actId, times);
    }
}
