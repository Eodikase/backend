package com.konkuk.Eodikase.dto.request.course;

import com.konkuk.Eodikase.domain.data.entity.CourseDataCategory;
import lombok.Data;

@Data
public class CourseDataPostRequest {

    private Long courseDataId;
    private String region;

}
