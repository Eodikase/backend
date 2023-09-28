package com.konkuk.Eodikase.domain.course.controller;

import com.konkuk.Eodikase.domain.course.entity.CourseRegion;
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

}



