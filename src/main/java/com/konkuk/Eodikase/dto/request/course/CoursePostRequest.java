package com.konkuk.Eodikase.dto.request.course;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
public class CoursePostRequest {

    private String courseName;

    private String courseDescription;

}
