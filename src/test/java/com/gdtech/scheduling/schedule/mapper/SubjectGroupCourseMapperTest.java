package com.gdtech.scheduling.schedule.mapper;

import com.gdtech.scheduling.schedule.SchedulingApplication;
import com.gdtech.scheduling.schedule.dto.ElectiveRecordLessonGroupDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

/**
 * @author zhucy
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Import(SchedulingApplication.class)
public class SubjectGroupCourseMapperTest {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SubjectGroupCourseMapper subjectGroupCourseMapper;

    @Test
    public void testGetFirstSubjectGroupMap() {
        String actId = "999";
        List<ElectiveRecordLessonGroupDto> list = subjectGroupCourseMapper.getSubjectGroupList(actId, -1);
        log.info(String.valueOf(list.get(0).getStuCount()));
    }
}
