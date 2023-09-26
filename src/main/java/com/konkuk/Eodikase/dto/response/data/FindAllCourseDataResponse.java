package com.konkuk.Eodikase.dto.response.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FindAllCourseDataResponse {
    private Long id;
    private double lat;
    private double lng;
}
