package com.konkuk.Eodikase.dto.response.data;

import lombok.Getter;

import java.util.List;

@Getter
public class FilteredCourseDataResponse {
    private String datasType;
    private List<FilteredCourseDataByRadiusResponse> datas;
    private Boolean isEnd;

    public FilteredCourseDataResponse(String datasType, List<FilteredCourseDataByRadiusResponse> datas, Boolean isEnd) {
        this.datasType = datasType;
        this.datas = datas;
        this.isEnd = isEnd;
    }
}
