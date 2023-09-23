package com.konkuk.Eodikase.domain.data.controller;

import com.konkuk.Eodikase.domain.data.dto.request.FilteredCourseDataRequest;
import com.konkuk.Eodikase.domain.data.dto.response.FilteredCourseDataResponse;
import com.konkuk.Eodikase.domain.data.service.CourseDataService;
import com.konkuk.Eodikase.security.auth.LoginUserId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/courseDatas")
public class CourseDataController {

    private final CourseDataService courseDataService;

    @Operation(summary = "타입별 코스 아이템 조회")
    @SecurityRequirement(name = "JWT")
    @GetMapping("/first")
    public ResponseEntity<FilteredCourseDataResponse> getFilteredCourseData(
            @LoginUserId Long memberId,
            @RequestParam String region,
            @RequestParam String type,
            @RequestParam int stage,
            @RequestParam("page") final Integer page,
            @RequestParam("count") final int count,
            @RequestBody FilteredCourseDataRequest request
    ) {
        FilteredCourseDataResponse response = courseDataService.filtersFirstCourseData(
                memberId, region, type, stage, request, page, count);
        return ResponseEntity.ok(response);
    }
}
