package com.konkuk.Eodikase.domain.data.controller;

import com.konkuk.Eodikase.domain.data.dto.response.GetFilteredCourseDataResponse;
import com.konkuk.Eodikase.domain.data.service.CourseDataService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/courseDatas")
public class CourseDataController {

    private final CourseDataService courseDataService;

    @Operation(summary = "타입별 코스 아이템 조회")
    @GetMapping
    public ResponseEntity<GetFilteredCourseDataResponse> getFilteredCourseData(
            @RequestParam String region, @RequestParam String types
    ) {
        GetFilteredCourseDataResponse response = courseDataService.filtersCourseDataByType(region, types);
        return ResponseEntity.ok(response);
    }
}
