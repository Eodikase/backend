package com.konkuk.Eodikase.domain.course.dto.request;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
public class CoursePostRequest {

    private String courseName;

    private String courseDescription;

}
