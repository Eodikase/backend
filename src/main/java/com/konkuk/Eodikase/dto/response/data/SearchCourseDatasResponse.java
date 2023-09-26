package com.konkuk.Eodikase.dto.response.data;

import lombok.Getter;

import java.util.List;

@Getter
public class SearchCourseDatasResponse {

    private List<SearchCourseDataResponse> contents;

    private Boolean isEnd;

    public SearchCourseDatasResponse(List<SearchCourseDataResponse> contents, Boolean isEnd) {
        this.contents = contents;
        this.isEnd = isEnd;
    }
}
