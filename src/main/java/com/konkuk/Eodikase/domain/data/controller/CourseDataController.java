package com.konkuk.Eodikase.domain.data.controller;

import com.konkuk.Eodikase.domain.data.service.CourseDataService;
import com.konkuk.Eodikase.dto.request.data.FilteredCourseDataCountRequest;
import com.konkuk.Eodikase.dto.request.data.FilteredCourseDataRequest;
import com.konkuk.Eodikase.dto.response.data.CourseDataDetailInfoResponse;
import com.konkuk.Eodikase.dto.response.data.FilteredCourseDataCountResponse;
import com.konkuk.Eodikase.dto.response.data.FilteredCourseDataResponse;
import com.konkuk.Eodikase.dto.response.Response;
import com.konkuk.Eodikase.security.auth.LoginUserId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/courseDatas")
public class CourseDataController {

    private final CourseDataService courseDataService;

    @Operation(summary = "타입별 코스 아이템 조회")
    @SecurityRequirement(name = "JWT")
    @GetMapping("/{region}/items")
    public Response<?> getFilteredCourseData(
            @LoginUserId Long memberId,
            @PathVariable String region,
            @RequestParam String type,
            @RequestParam int stage,
            @RequestParam int order,
            @RequestParam("page") final Integer page,
            @RequestParam("count") final int count,
            @RequestBody FilteredCourseDataRequest request
    ) {
        FilteredCourseDataResponse response = courseDataService.filtersCourseData(
                memberId, region, type, stage, order, request, page, count);
        return Response.ofSuccess("OK", response);
    }

    @Operation(summary = "특정 코스 아이템 상세정보 조회")
    @SecurityRequirement(name = "JWT")
    @GetMapping("/{region}/items/{dataId}")
    public Response<?> getCourseDataDetailInfo(
            @LoginUserId Long memberId,
            @PathVariable String region,
            @PathVariable Long dataId
    ) {
        CourseDataDetailInfoResponse response = courseDataService.searchCourseDataDetailInfo(memberId, region, dataId);
        return Response.ofSuccess("OK", response);
    }

    @Operation(summary = "특정 반경 내의 코스 아이템 개수 조회")
    @SecurityRequirement(name = "JWT")
    @GetMapping("/{region}/items/count")
    public Response<?> getCourseDataCountByRadius(
            @LoginUserId Long memberId,
            @PathVariable String region,
            @RequestBody FilteredCourseDataCountRequest request
    ) {
        FilteredCourseDataCountResponse response = courseDataService.filterCourseDataCountByRadius(
                memberId, region, request);
        return Response.ofSuccess("OK", response);
    }
}
