package com.konkuk.Eodikase.dto.response.data;

import lombok.Getter;

import java.util.List;

@Getter
public class FilteredCourseDataResponse {
    private String datasType;
    private List<FilteredCourseDataByRadiusResponse> datas;

    public FilteredCourseDataResponse(String datasType, List<FilteredCourseDataByRadiusResponse> datas) {
        this.datasType = datasType;
        this.datas = datas;
    }
}