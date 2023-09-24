package com.konkuk.Eodikase.domain.course.controller;

import com.konkuk.Eodikase.domain.course.entity.Course;
import com.konkuk.Eodikase.domain.course.entity.CourseRegion;
import com.konkuk.Eodikase.domain.course.repository.CourseRepository;
import com.konkuk.Eodikase.dto.request.course.CourseDataPostRequest;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/courses")
public class CourseController {

    private final CourseService courseService;

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "코스 데이터 삽입")
    @PostMapping("/{course-id}")
    public Response postCourseData(
            @LoginUserId Long memberId,
            @PathVariable("course-id") Long courseId,
            @RequestBody CourseDataPostRequest request){
        courseService.saveData(memberId, courseId, request);
        return Response.ofSuccess("OK",null);
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "코스 생성")
    @PostMapping
    public Response saveCourse(
           @LoginUserId Long memberId,
           @RequestBody CoursePostRequest request){

        courseService.saveCourse(memberId,request);
        return Response.ofSuccess("OK",null);
    }

    @Operation(summary = "코스 전체 조회")
    @GetMapping
    public Response list(@PageableDefault(size = 20, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable){
        Page<CourseResponse> courses = courseService.getCourses(pageable);
        return Response.ofSuccess("OK",courses);
    }
    @Operation(summary = "코스 지역별 조회")
    @GetMapping("/{region}")
    public Response listByRegion(@PageableDefault(size = 20, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable,
                                 @PathVariable("region") CourseRegion region){
        Page<CourseResponse> coursesByRegion = courseService.getCoursesByRegion(region,pageable);
        return Response.ofSuccess("OK",coursesByRegion);
    }
}



