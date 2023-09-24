package com.konkuk.Eodikase.dto.request.course;

import com.konkuk.Eodikase.domain.course.entity.CourseRegion;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
public class CoursePostRequest {

    private String courseName;

    private String courseDescription;

    private CourseRegion courseRegion;

}
