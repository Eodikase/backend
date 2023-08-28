package com.konkuk.Eodikase.domain.data.controller;

import com.konkuk.Eodikase.domain.data.service.CourseDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CourseDataController {

    private final CourseDataService courseDataService;

}
