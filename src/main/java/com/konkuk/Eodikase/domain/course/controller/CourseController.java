package com.konkuk.Eodikase.domain.course.controller;

import com.konkuk.Eodikase.domain.course.entity.CourseRegion;
import com.konkuk.Eodikase.domain.hashtag.entity.HashTagName;
import com.konkuk.Eodikase.dto.request.course.CoursePostRequest;
import com.konkuk.Eodikase.domain.course.service.CourseService;
import com.konkuk.Eodikase.dto.response.course.CourseResponse;
import com.konkuk.Eodikase.dto.response.Response;
import com.konkuk.Eodikase.security.auth.LoginUserId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/courses")
public class CourseController {

    private final CourseService courseService;

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "코스 생성")
    @PostMapping
    public Response saveCourse(
            @LoginUserId Long memberId,
            @RequestBody CoursePostRequest request
    ) {

        Long id = courseService.saveCourse(memberId, request);
        return Response.ofSuccess("OK", id);
    }

    @Operation(summary = "특정 코스 조회")
    @GetMapping("/course/{course-id}")
    public Response getCourse(@PathVariable("course-id") Long courseId) {
        CourseResponse findCourse = courseService.getCourse(courseId);
        return Response.ofSuccess("OK", findCourse);
    }
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "특정 코스 삭제")
    @DeleteMapping("/{course-id}")
    public Response deleteCourse(@LoginUserId Long memberId,
                                 @PathVariable("course-id") Long courseId) {
        courseService.deleteCourse(courseId, memberId);
        return Response.ofSuccess("OK", null);
    }

    @Operation(summary = "코스 전체 조회")
    @GetMapping
    public Response getCourses(@PageableDefault(size = 20, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<CourseResponse> courses = courseService.getCourses(pageable);
        return Response.ofSuccess("OK", courses);
    }

    @Operation(summary = "코스 지역별 조회")
    @GetMapping("/region/{region}")
    public Response getCoursesByRegion(@PageableDefault(size = 20, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable,
                                 @PathVariable("region") CourseRegion region) {
        Page<CourseResponse> coursesByRegion = courseService.getCoursesByRegion(region, pageable);
        return Response.ofSuccess("OK", coursesByRegion);
    }

    @Operation(summary = "코스 사용자별 조회")
    @GetMapping("/member/{member-id}")
    public Response getCoursesByMember(@PageableDefault(size = 20, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable,
                                 @PathVariable("member-id") Long memberId) {
        Page<CourseResponse> coursesByMember = courseService.getCoursesByMember(memberId, pageable);
        return Response.ofSuccess("OK", coursesByMember);
    }
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "코스 별점 등록")
    @PostMapping("/score/{course-id}")
    public Response listByRegion(@LoginUserId Long memberId,
                                 @PathVariable("course-id") Long courseId,
                                 @RequestParam double score ) {
        courseService.insertScore(courseId,score, memberId);
        return Response.ofSuccess("OK", null);
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "마이페이지 - 스크랩한 코스 목록 조회")
    @GetMapping(value = "/myscraps")
    public Response<?> findScrapedCourses(
            @LoginUserId Long memberId,
            @PageableDefault(size = 20, sort = "createdDate",
                    direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<CourseResponse> courses = courseService.findScrapedCourses(memberId, pageable);
        return Response.ofSuccess("OK", courses);
    }

    @Operation(summary = "코스검색by코스데이터 ")
    @GetMapping("/data/search")
    public Response listByCouseData(
            @PageableDefault(size = 20, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam String keyword) {
        Page<CourseResponse> courseByData = courseService.searchByData(keyword, pageable);
        return Response.ofSuccess("OK", courseByData);
    }
    @Operation(summary = "코스검색by코스제목 ")
    @GetMapping("/title/search")
    public Response listByTitle(
            @PageableDefault(size = 20, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam String keyword) {
        Page<CourseResponse> courseByTitle = courseService.searchByTitle(keyword, pageable);
        return Response.ofSuccess("OK", courseByTitle);
    }

    @Operation(summary = "코스검색by해시태그 ")
    @GetMapping("/tag/search")
    public Response listByTag(
            @PageableDefault(size = 20, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam HashTagName tag) {
        Page<CourseResponse> courseBytag = courseService.searchByTag(tag, pageable);
        return Response.ofSuccess("OK", courseBytag);
    }
}



