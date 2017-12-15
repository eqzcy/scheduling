package com.gdtech.scheduling.schedule.service;

import com.gdtech.scheduling.schedule.SchedulingApplication;
import com.gdtech.scheduling.schedule.dto.ElectiveRecordGroupDto;
import com.gdtech.scheduling.schedule.entity.ElectiveRecord;
import com.gdtech.scheduling.schedule.enums.SubjectCodeEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import(SchedulingApplication.class)
public class CourseRecordServiceTest {


    @Autowired
    private ElectiveRecordService electiveRecordService;

    @Test
    public void testGetAllCourseRecordList() {
        List<ElectiveRecord> list = electiveRecordService.getAllElectiveRecordList();

        Assert.assertNotNull(list);
    }

    @Test
    public void testModStuElectiveGroup() {
        String actId = "999";
        List<ElectiveRecordGroupDto> groupList = new ArrayList<>();
//        物理	化学	生物	174
        ElectiveRecordGroupDto whs = new ElectiveRecordGroupDto(174,
                SubjectCodeEnum.Physics, SubjectCodeEnum.Chemistry, SubjectCodeEnum.Biology);
        groupList.add(whs);
//        物理	化学	历史	28
        ElectiveRecordGroupDto whl = new ElectiveRecordGroupDto(28,
                SubjectCodeEnum.Physics, SubjectCodeEnum.Chemistry, SubjectCodeEnum.History);
        groupList.add(whl);
//        物理	化学	政治	10
        ElectiveRecordGroupDto whz = new ElectiveRecordGroupDto(10,
                SubjectCodeEnum.Physics, SubjectCodeEnum.Chemistry, SubjectCodeEnum.Politics);
        groupList.add(whz);
//        物理	化学	地理	40
        ElectiveRecordGroupDto whd = new ElectiveRecordGroupDto(40,
                SubjectCodeEnum.Physics, SubjectCodeEnum.Chemistry, SubjectCodeEnum.Geography);
        groupList.add(whd);
//        物理	生物	历史	46
        ElectiveRecordGroupDto wsl = new ElectiveRecordGroupDto(46,
                SubjectCodeEnum.Physics, SubjectCodeEnum.Biology, SubjectCodeEnum.History);
        groupList.add(wsl);
//        物理	生物	政治	7
        ElectiveRecordGroupDto wsz = new ElectiveRecordGroupDto(7,
                SubjectCodeEnum.Physics, SubjectCodeEnum.Biology, SubjectCodeEnum.Politics);
        groupList.add(wsz);
//        物理	生物	地理	62
        ElectiveRecordGroupDto wsd = new ElectiveRecordGroupDto(62,
                SubjectCodeEnum.Physics, SubjectCodeEnum.Biology, SubjectCodeEnum.Geography);
        groupList.add(wsd);
//        物理	历史	政治	21
        ElectiveRecordGroupDto wlz = new ElectiveRecordGroupDto(21,
                SubjectCodeEnum.Physics, SubjectCodeEnum.History, SubjectCodeEnum.Geography);
        groupList.add(wlz);
//        物理	历史	地理	31
        ElectiveRecordGroupDto wld = new ElectiveRecordGroupDto(31,
                SubjectCodeEnum.Physics, SubjectCodeEnum.History, SubjectCodeEnum.Biology);
        groupList.add(wld);
//        物理	政治	地理	8
        ElectiveRecordGroupDto wzd = new ElectiveRecordGroupDto(8,
                SubjectCodeEnum.Physics, SubjectCodeEnum.Politics, SubjectCodeEnum.Geography);
        groupList.add(wzd);
//        化学	生物	历史	30
        ElectiveRecordGroupDto hsl = new ElectiveRecordGroupDto(30,
                SubjectCodeEnum.Chemistry, SubjectCodeEnum.Biology, SubjectCodeEnum.History);
        groupList.add(hsl);
//        化学	生物	政治	5
        ElectiveRecordGroupDto hsz = new ElectiveRecordGroupDto(5,
                SubjectCodeEnum.Chemistry, SubjectCodeEnum.Biology, SubjectCodeEnum.Politics);
        groupList.add(hsz);
//        化学	生物	地理	36
        ElectiveRecordGroupDto hsd = new ElectiveRecordGroupDto(36,
                SubjectCodeEnum.Chemistry, SubjectCodeEnum.Biology, SubjectCodeEnum.Geography);
        groupList.add(hsd);
//        化学	历史	政治	7
        ElectiveRecordGroupDto hlz = new ElectiveRecordGroupDto(7,
                SubjectCodeEnum.Chemistry, SubjectCodeEnum.History, SubjectCodeEnum.Politics);
        groupList.add(hlz);
//        化学	历史	地理	18
        ElectiveRecordGroupDto hld = new ElectiveRecordGroupDto(18,
                SubjectCodeEnum.Chemistry, SubjectCodeEnum.History, SubjectCodeEnum.Geography);
        groupList.add(hld);
//        化学	政治	地理	3
        ElectiveRecordGroupDto hzd = new ElectiveRecordGroupDto(3,
                SubjectCodeEnum.Chemistry, SubjectCodeEnum.Politics, SubjectCodeEnum.Geography);
        groupList.add(hzd);
//        生物	历史	政治	15
        ElectiveRecordGroupDto slz = new ElectiveRecordGroupDto(15,
                SubjectCodeEnum.Biology, SubjectCodeEnum.History, SubjectCodeEnum.Politics);
        groupList.add(slz);
//        生物	历史	地理	33
        ElectiveRecordGroupDto sld = new ElectiveRecordGroupDto(33,
                SubjectCodeEnum.Biology, SubjectCodeEnum.History, SubjectCodeEnum.Geography);
        groupList.add(sld);
//        生物	政治	地理	9
        ElectiveRecordGroupDto szd = new ElectiveRecordGroupDto(9,
                SubjectCodeEnum.Biology, SubjectCodeEnum.Politics, SubjectCodeEnum.Geography);
        groupList.add(szd);
//        历史	政治	地理	90
        ElectiveRecordGroupDto lzd = new ElectiveRecordGroupDto(90,
                SubjectCodeEnum.History, SubjectCodeEnum.Politics, SubjectCodeEnum.Geography);
        groupList.add(lzd);

        electiveRecordService.modStuElectiveGroup(actId, groupList);
    }

    @Test
    public void testCalElectiveGroup() {
        String actId = "999";
        int minute = 1;
        electiveRecordService.calElectiveGroup(actId, minute);
    }
}
