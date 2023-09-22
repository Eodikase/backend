package com.konkuk.Eodikase.domain.data.service;

import com.konkuk.Eodikase.domain.data.dto.response.GetFilteredCourseDataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseDataService {

    public GetFilteredCourseDataResponse filtersCourseDataByType(String region, String category, String order) {

        // 순서 필터링 (첫 번째 순서인 경우에만 별점 + x, y위치 고려해서 거리순)
        // 지역 필터링
        // 매장 타입 필터링
    }
}
