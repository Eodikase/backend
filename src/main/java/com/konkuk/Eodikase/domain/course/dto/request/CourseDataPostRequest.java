package com.konkuk.Eodikase.domain.course.dto.request;

import com.konkuk.Eodikase.domain.data.entity.CourseDataCategory;
import lombok.Data;

@Data
public class CourseDataPostRequest {

    private Long courseDataId;
    private String region;
    private int sequence;


}
