package com.konkuk.Eodikase.dto.response.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class FilteredCourseDataResponse {
    private String datasType;
    private List<FilteredCourseDataByRadiusResponse> contents;
    private Boolean last;
}
