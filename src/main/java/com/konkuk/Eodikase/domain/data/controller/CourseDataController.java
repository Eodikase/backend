package com.konkuk.Eodikase.domain.data.controller;

import com.konkuk.Eodikase.domain.data.entity.Activity;
import com.konkuk.Eodikase.domain.data.service.CourseDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CourseDataController {

    private final CourseDataService courseDataService;

    @PostMapping("/course_data")
    public String save(){
        Activity activity = new Activity();
        activity.setDescription("testDescription");
        activity.setCategory("testCategory");
        activity.setTest("test");
        courseDataService.save(activity);

        return "test";
    }
}
