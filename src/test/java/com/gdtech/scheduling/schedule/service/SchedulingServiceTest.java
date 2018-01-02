package com.gdtech.scheduling.schedule.service;

/**
 * @author zhucy
 */

import com.gdtech.scheduling.schedule.SchedulingApplicationTests;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import(SchedulingApplicationTests.class)
public class SchedulingServiceTest {

    @Autowired
    private SchedulingService schedulingService;

    @Test
    public void testScheduleTeachingClass() {
        String id = "999";
        schedulingService.scheduleTeachingClass(id);
    }


}
