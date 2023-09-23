package com.konkuk.Eodikase.domain.course.controller;

import com.konkuk.Eodikase.domain.course.dto.request.CourseDataPostRequest;
import com.konkuk.Eodikase.domain.course.dto.request.CoursePostRequest;
import com.konkuk.Eodikase.domain.course.entity.Course;
import com.konkuk.Eodikase.domain.course.service.CourseService;
import com.konkuk.Eodikase.domain.data.dto.request.FilteredCourseDataRequest;
import com.konkuk.Eodikase.domain.data.dto.response.FilteredCourseDataResponse;
import com.konkuk.Eodikase.domain.member.entity.Member;
import com.konkuk.Eodikase.domain.member.repository.MemberRepository;
import com.konkuk.Eodikase.security.auth.LoginUserId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/courses")
public class CourseController {

    private final CourseService courseService;
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "코스 데이터 삽입")
    @PostMapping("/{course-id}")
    public ResponseEntity postCourseData(
            @LoginUserId Long memberId,
            @PathVariable("course-id") Long courseId,
            @RequestBody CourseDataPostRequest request){
        courseService.saveData(memberId, courseId, request);
        return new ResponseEntity(HttpStatus.OK);
    }
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "코스 생성")
    @PostMapping
    public ResponseEntity saveCourse(
           @LoginUserId Long memberId,
           @RequestBody CoursePostRequest request){

        courseService.saveCourse(memberId,request);
        return new ResponseEntity(HttpStatus.OK);
    }

}



