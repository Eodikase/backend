package com.konkuk.Eodikase.domain.data.controller;

import com.konkuk.Eodikase.domain.data.service.CourseDataService;
import com.konkuk.Eodikase.dto.request.data.FilteredCourseDataCountRequest;
import com.konkuk.Eodikase.dto.request.data.FilteredCourseDataRequest;
import com.konkuk.Eodikase.dto.response.data.*;
import com.konkuk.Eodikase.dto.response.Response;
import com.konkuk.Eodikase.security.auth.LoginUserId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/coursedatas")
public class CourseDataController {

    private final CourseDataService courseDataService;

    @Operation(summary = "타입별 코스 아이템 조회")
    @SecurityRequirement(name = "JWT")
    @PostMapping("/{region}/items")
    public Response<?> getFilteredCourseData(
            @LoginUserId Long memberId,
            @PathVariable String region,
            @RequestParam String type,
            @RequestParam int stage,
            @RequestParam int order,
            @RequestParam("page") final Integer page,
            @RequestBody FilteredCourseDataRequest request
    ) {
        FilteredCourseDataResponse response = courseDataService.filtersCourseData(
                memberId, region, type, stage, order, request, page, 7);
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
    @PostMapping("/{region}/items/count")
    public Response<?> getCourseDataCountByRadius(
            @LoginUserId Long memberId,
            @PathVariable String region,
            @RequestBody FilteredCourseDataCountRequest request
    ) {
        FilteredCourseDataCountResponse response = courseDataService.filterCourseDataCountByRadius(
                memberId, region, request);
        return Response.ofSuccess("OK", response);
    }

    @Operation(summary = "코스 아이템 키워드 검색")
    @SecurityRequirement(name = "JWT")
    @GetMapping("/{region}/search")
    public Response<?> getCourseDataCountByRadius(
            @LoginUserId Long memberId,
            @PathVariable String region,
            @RequestParam("category") String category,
            @RequestParam("keyword") String keyword,
            @RequestParam("page") final Integer page
    ) {
        SearchCourseDatasResponse response = courseDataService.searchCourseDataByKeyword(
                memberId, region, category, keyword, page, 10);
        return Response.ofSuccess("OK", response);
    }

    @Operation(summary = "지역별 코스 아이템 개수 조회")
    @SecurityRequirement(name = "JWT")
    @GetMapping("/{region}/count")
    public Response<?> getCourseDataCountByRegion(
            @LoginUserId Long memberId,
            @PathVariable String region
    ) {
        CourseDataCountByRegionResponse response = courseDataService.findCourseDataCountByRegion(
                memberId, region);
        return Response.ofSuccess("OK", response);
    }
}
