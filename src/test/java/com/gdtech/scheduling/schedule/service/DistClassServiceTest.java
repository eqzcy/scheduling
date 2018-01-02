package com.gdtech.scheduling.schedule.service;

import com.gdtech.scheduling.schedule.SchedulingApplication;
import com.gdtech.scheduling.schedule.SchedulingApplicationTests;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zhucy
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Import(SchedulingApplicationTests.class)
public class DistClassServiceTest {

    @Autowired
    private DistClassService distClassService;

    @Test
    public void testDistTeachClass() {
        String settingId = "1";
        String actId = "999";
        Integer times = 691;
        distClassService.distTeachClass(settingId, actId, times);
    }
}
