package com.gdtech.scheduling.schedule.controller;

import com.gdtech.scheduling.schedule.entity.ElectiveRecord;
import com.gdtech.scheduling.schedule.service.ElectiveRecordService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("electiveRecord")
public class ElectiveRecordController {
    @Autowired
    private ElectiveRecordService courseRecordService;

    @RequestMapping(value = "getAll")
    public PageInfo<ElectiveRecord> getAll() {
        List<ElectiveRecord> recordList = courseRecordService.getAllElectiveRecordList();
        return new PageInfo<ElectiveRecord>(recordList);
    }
}
