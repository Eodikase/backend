package com.konkuk.Eodikase.domain.data.service;

import com.konkuk.Eodikase.domain.data.dto.response.GetFilteredCourseDataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseDataService {

    public GetFilteredCourseDataResponse filtersCourseDataByType(String region, String type) {

    }
}
